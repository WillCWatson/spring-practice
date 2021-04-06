package com.example.practice;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class RelationalDataAccessApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RelationalDataAccessApplication.class);

    public static void main(String args[]) {
        SpringApplication.run(RelationalDataAccessApplication.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;
    // Automatically loads JdbcTemplate and makes it available

    @Override
    public void run(String... strings) throws Exception {

        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

        // Split up the array of whole names into an array of first/last names
        List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        // Checking for my own curiosity what this looks like
        System.out.println("DEBUG: Printing out all of the names");
        System.out.println(splitUpNames.toString());

        // Use a Java 8 stream to print out each tuple of the list
        splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);
        // jdbcTemplate.batchUpdate() takes an array and then automatically populates the contents of that array in those ? bits, I think

        log.info("Querying for customer records where first_name = 'Josh':");
        // Apparently jdbcTemplate.query is depricated, they should be using something else here, but I don't know what.
        jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers WHERE first_name = ?", new Object[] { "Josh" },
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
        ).forEach(customer -> log.info(customer.toString()));
        // Okay, it looks like the jdbcTemplate.query should be replaced with jdbcTemplate.queryForObject, but I would
        // have to mess with this to get it to come out not depricated. Thus, for the purposes of this guide, I will not.
    }
}