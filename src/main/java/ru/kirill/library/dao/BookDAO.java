package ru.kirill.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kirill.library.models.Book;
import ru.kirill.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int bookId) {
        return jdbcTemplate.query("SELECT * FROM book where bookId=?", new Object[]{bookId},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public Optional<Book> show(String bookName) {
        return jdbcTemplate.query("SELECT * FROM book where bookName=?", new Object[]{bookName},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(bookName, author, yearOfRelease) VALUES (?, ?, ?)",
                book.getBookName(), book.getAuthor(), book.getYearOfRelease());
    }

    public void update(int bookId, Book book) {
        jdbcTemplate.update("update book set bookName=?, author=?, yearOfRelease=? where bookId=?",
                book.getBookName(), book.getAuthor(), book.getYearOfRelease(), bookId);
    }

    public void delete(int bookId) {
        jdbcTemplate.update("delete from book where bookId=?", bookId);
    }

    public void assign(int bookId, Person person) {
        jdbcTemplate.update("update book set personId=? where bookId=?", person.getPersonId(),bookId);
    }

    public void release(int bookId) {
        jdbcTemplate.update("update book set personId=NULL where bookId=?", bookId);
    }

    public Optional<Person> getbookOwner(int bookId) {
        return jdbcTemplate.query("Select Person.* from book join person on book.personId = person.personId where book.bookId=?",
                new Object[]{bookId}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
