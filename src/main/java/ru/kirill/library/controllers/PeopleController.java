package ru.kirill.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kirill.library.dao.PersonDAO;
import ru.kirill.library.models.Person;
import ru.kirill.library.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/indexPeople";
    }

    @GetMapping("/{personId}")
    public String show(@PathVariable("personId") int personId, Model model) {
        model.addAttribute("person", personDAO.show(personId));
        return "people/showPerson";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {

        return "people/newPerson";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/newPerson";
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{personId}/edit")
    public String editPerson(@PathVariable("personId") int personId, Model model) {
        model.addAttribute("person", personDAO.show(personId));
        return "people/editPerson";
    }

    @PatchMapping("/{personId}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("personId") int personId) {
        personValidator.validate(person, bindingResult, Thread.currentThread().getStackTrace()[2].getMethodName());

        if (bindingResult.hasErrors())
            return "people/editPerson";
        personDAO.update(personId, person);
        return "redirect:/people/{personId}";
    }

    @DeleteMapping("/{personId}")
    public String deletePerson(@PathVariable("personId") int personId) {
        personDAO.delete(personId);
        return "redirect:/people";
    }
}