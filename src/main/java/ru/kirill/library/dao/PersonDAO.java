package ru.kirill.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kirill.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int personId) {
        return jdbcTemplate.query("SELECT * FROM person where personId=?", new Object[]{personId},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public Optional<Person> show(String personName) {
        return jdbcTemplate.query("SELECT * FROM person where personName=?", new Object[]{personName},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(personName, YearOfBirth) VALUES (?, ?)",
                person.getPersonName(), person.getYearOfBirth());
    }

    public void update(int personId, Person person) {
        jdbcTemplate.update("update person set personName=?, YearOfBirth=? where personId=?", person.getPersonName(), person.getYearOfBirth(), personId);
    }

    public void delete(int personId) {
        jdbcTemplate.update("DELETE FROM person where personId=?", personId);
    }

}
