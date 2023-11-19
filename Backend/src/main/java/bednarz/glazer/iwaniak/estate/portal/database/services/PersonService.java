package bednarz.glazer.iwaniak.estate.portal.database.services;

import bednarz.glazer.iwaniak.estate.portal.database.model.Person;
import bednarz.glazer.iwaniak.estate.portal.database.model.Roles;
import bednarz.glazer.iwaniak.estate.portal.database.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> createNewPerson(String login, String password, String email) {
        Optional<Person> optionalPerson = personRepository.findByLogin(login);
        if (optionalPerson.isEmpty()) {
            return Optional.of(personRepository.save(new Person(0L, login, password,
                    email, null, null, null, null, Roles.USER)));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Person> createNewPerson(String login, String password, String email, String name, String surname,
                                            String phoneNumber) {
        Optional<Person> optionalPerson = personRepository.findByLogin(login);
        if (optionalPerson.isEmpty()) {
            return Optional.of(personRepository.save(new Person(0L, login, password,
                    email, name, surname, null, phoneNumber, Roles.USER)));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Person> createNewPerson(String login, String password, String email, String name, String surname,
                                            String nip, String phoneNumber) {
        Optional<Person> optionalPerson = personRepository.findByLogin(login);
        if (optionalPerson.isEmpty()) {
            return Optional.of(personRepository.save(new Person(0L, login, password,
                    email, name, surname, nip, phoneNumber, Roles.USER)));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Person> getPersonByLogin(String login) {
        return personRepository.findByLogin(login);
    }

    public Optional<Person> getPersonById(Long personId) {
        return personRepository.findById(personId);
    }

    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    public List<Person> getAllUsers(){
        return personRepository.getAllUsers();
    }

    public void removePerson(Person person) {
        personRepository.delete(person);
    }

    public void removePeople(List<Person> person) {
        personRepository.deleteAll(person);
    }
}
