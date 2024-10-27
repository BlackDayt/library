package ru.kirill.library.models;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;


public class Person {
    private int personId;

    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 0 characters")
    private String personName;

    @Max(value = 2023, message = "age should be greater than 0")
    private int yearOfBirth;
    private List<Book> takenBook = new ArrayList<>();


    public Person() {
    }

    public Person(String personName, int yearOfBirth) {
        this.personName = personName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getTakenBook() {
        return takenBook;
    }

    public void addBook(Book book) {
        takenBook.add(book);
    }
}
