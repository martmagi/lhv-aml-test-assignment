package com.assignment.matcher;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BlacklistNameH2Repository extends CrudRepository<BlacklistName, UUID> {
}
