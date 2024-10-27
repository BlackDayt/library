package ru.kirill.library.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    private int bookId;
    private Integer personId;

    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 200, message = "Name should be between 2 and 0 characters")
    private String bookName;

    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 0 characters")
    private String author;

    @Max(value = 2024, message = "age of book should be greater than 0")
    private int yearOfRelease;



    public Book() {
    }

    public Book(int bookId, Integer personId, String bookName, String author, int yearOfRelease) {
        this.bookId = bookId;
        this.personId = personId;
        this.bookName = bookName;
        this.author = author;
        this.yearOfRelease = yearOfRelease;
    }



    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }


}
