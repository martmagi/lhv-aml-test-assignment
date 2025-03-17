package com.assignment.matcher;

import com.google.common.collect.Collections2;
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
    private static final SimpleTokenizer TOKENIZER = SimpleTokenizer.INSTANCE;

    private final BlacklistNameH2Repository repository;

    public BlacklistNameService(BlacklistNameH2Repository repository) {
        this.repository = repository;
    }

    public boolean check(String name) {
        String cleanedName = cleanName(name);
        for (BlacklistName blacklistName : this.repository.findAll()) {
            for (String blacklistNameVariant : blacklistName.getCleanedNamePermutations()) {
                if (DISTANCE.apply(blacklistNameVariant, cleanedName) < SIMILARITY_BOUND) {
                    return true;
                }
            }
        }
        return false;
    }

    public UUID add(String name) {
        BlacklistName entity = new BlacklistName();
        entity.setName(name);
        String cleanedName = cleanName(name);
        entity.setCleanedName(cleanedName);
        entity.setCleanedNamePermutations(generateNamePermutations(cleanedName));
        return this.repository.save(entity).getId();
    }

    public void update(UUID id, String name) {
        Optional<BlacklistName> optionalEntity = this.repository.findById(id);
        if (optionalEntity.isPresent()) {
            BlacklistName entity = optionalEntity.get();
            entity.setName(name);
            String cleanedName = cleanName(name);
            entity.setCleanedName(cleanedName);
            entity.setCleanedNamePermutations(generateNamePermutations(cleanedName));
            this.repository.save(entity);
        }
    }

    public void delete(UUID id) {
        this.repository.deleteById(id);
    }

    /**
     * Tokenizes the input name, removes stop words, lowercases the tokens and sorts alphabetically.
     * @param name String representation of name.
     * @return Standardized name for storage or further processing.
     */
    private String cleanName(String name) {
        String[] tokenizedName = TOKENIZER.tokenize(name);
        return Stream.of(tokenizedName)
                .filter(token -> !STOP_WORDS.contains(token))
                .map(String::toLowerCase)
                .sorted()
                .collect(Collectors.joining(" "));
    }

    /**
     * Tokenizes the input name and creates permutations of the name.
     * @param name String representation of name. NB: Expects input to be already clean.
     * @return List of permutations of given name for storage or further processing.
     */
    private List<String> generateNamePermutations(String name) {
        String[] tokenizedName = TOKENIZER.tokenize(name);
        return Collections2.permutations(List.of(tokenizedName)).stream()
                .map(e -> String.join(" ", e))
                .toList();
    }
}
