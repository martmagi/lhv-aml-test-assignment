package com.assignment.matcher;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;
import java.util.UUID;

@Entity
public class BlacklistName {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String cleanedName;
    @ElementCollection
    private List<String> cleanedNamePermutations;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCleanedName() {
        return cleanedName;
    }

    public void setCleanedName(String cleanedName) {
        this.cleanedName = cleanedName;
    }

    public List<String> getCleanedNamePermutations() {
        return cleanedNamePermutations;
    }

    public void setCleanedNamePermutations(List<String> cleanedNamePermutations) {
        this.cleanedNamePermutations = cleanedNamePermutations;
    }
}
