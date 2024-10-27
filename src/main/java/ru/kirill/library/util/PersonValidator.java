package ru.kirill.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kirill.library.dao.PersonDAO;
import ru.kirill.library.models.Person;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personDAO.show(person.getPersonName()).isPresent()) {
            errors.rejectValue("personName", "", "This name is already exist");
        }

    }

    public void validate(Object target, Errors errors, String method) {
        Person person = (Person) target;
        if (method.equals("create")){
            if (personDAO.show(person.getPersonName()).isPresent()) {
                errors.rejectValue("personName", "", "This name is already exist");
            }

        }
    }
}
