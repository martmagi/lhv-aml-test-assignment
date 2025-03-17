package com.assignment.matcher;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BlacklistNameMatcherTest {

    @Autowired
    private BlacklistNameH2Repository repository;

    private BlacklistNameService service;

    @BeforeAll
    public void setUp() {
        service = new BlacklistNameService(repository);
        service.add("Osama Bin Laden");
    }

    @ParameterizedTest(name = "Name ''{0}'' should return ''{1}''")
    @CsvFileSource(resources = "/names.csv", delimiter = ';')
    void testNames(String name, boolean expected) {
        assertEquals(expected, service.check(name));
    }
}
