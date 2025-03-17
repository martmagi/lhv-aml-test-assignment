package com.assignment.matcher;

import opennlp.tools.tokenize.SimpleTokenizer;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BlacklistNameService {
    private static final JaroWinklerDistance DISTANCE = new JaroWinklerDistance();
    private static final float SIMILARITY_BOUND = 0.2f;
    private static final List<String> STOP_WORDS = List.of("the", "to", "an", "mrs", "mr", "and", ",", ".");

    private final BlacklistNameH2Repository repository;

    public BlacklistNameService(BlacklistNameH2Repository repository) {
        this.repository = repository;
    }

    public boolean check(String name) {
        String cleanedName = tokenizeAndCleanName(name);
        for (BlacklistName blacklistName : this.repository.findAll()) {
            if (DISTANCE.apply(blacklistName.getCleanedName(), cleanedName) < SIMILARITY_BOUND) {
                return true;
            }
        }
        return false;
    }

    public UUID add(String name) {
        BlacklistName entity = new BlacklistName();
        entity.setName(name);
        entity.setCleanedName(tokenizeAndCleanName(name));
        return this.repository.save(entity).getId();
    }

    public void update(UUID id, String name) {
        Optional<BlacklistName> optionalEntity = this.repository.findById(id);
        if (optionalEntity.isPresent()) {
            BlacklistName entity = optionalEntity.get();
            entity.setName(name);
            entity.setCleanedName(tokenizeAndCleanName(name));
            this.repository.save(entity);
        }
    }

    public void delete(UUID id) {
        this.repository.deleteById(id);
    }

    private String tokenizeAndCleanName(String name) {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] tokenizedName = tokenizer.tokenize(name);
        return Stream.of(tokenizedName)
                .filter(token -> !STOP_WORDS.contains(token))
                .sorted()
                .map(String::toLowerCase)
                .collect(Collectors.joining(" "));
    }
}
