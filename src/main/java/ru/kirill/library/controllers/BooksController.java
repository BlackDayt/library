package ru.kirill.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kirill.library.dao.BookDAO;
import ru.kirill.library.dao.PersonDAO;
import ru.kirill.library.models.Book;
import ru.kirill.library.models.Person;
import ru.kirill.library.util.BookValidator;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/indexBooks";
    }

    @GetMapping("/{bookId}")
    public String show(@PathVariable("bookId") int bookId, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(bookId));

        Optional<Person> bookOwner = bookDAO.getbookOwner(bookId);

        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personDAO.index());

        return "books/showBook";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {

        return "books/newBook";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors())
            return "books/newBook";
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{bookID}/edit")
    public String editBook(@PathVariable("bookID") int bookId, Model model) {
        model.addAttribute("book", bookDAO.show(bookId));
        return "books/editBook";
    }

    @PatchMapping("/{bookId}")
    public String updateBook(@ModelAttribute("book") Book book, BindingResult bindingResult,
                             @PathVariable("bookId") int bookId) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors())
            return "books/editBook";
        bookDAO.update(bookId, book);
        return "redirect:/books/{bookId}";
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable("bookId") int bookId) {
        bookDAO.delete(bookId);
        return "redirect:/books";
    }

    @PatchMapping("/{bookId}/assign")
    public String assignBook(@ModelAttribute("person") Person person, @PathVariable("bookId") int bookId) {
        bookDAO.assign(bookId, person);
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/{bookId}/release")
    public String releaseBook(@PathVariable("bookId") int bookId) {
        bookDAO.release(bookId);
        return "redirect:/books/{bookId}";
    }
}