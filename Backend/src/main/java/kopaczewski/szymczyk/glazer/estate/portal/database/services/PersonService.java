package kopaczewski.szymczyk.glazer.estate.portal.database.services;

import com.google.common.hash.Hashing;
import kopaczewski.szymczyk.glazer.estate.portal.database.model.Person;
import kopaczewski.szymczyk.glazer.estate.portal.database.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
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
            return Optional.of(personRepository.save(new Person(0L, login, makeHash(password),
                    email, null, null, null, null)));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Person> createNewPerson(String login, String password, String email, String name, String surname,
                                            String phoneNumber) {
        Optional<Person> optionalPerson = personRepository.findByLogin(login);
        if (optionalPerson.isEmpty()) {
            return Optional.of(personRepository.save(new Person(0L, login, makeHash(password),
                    email, name, surname, null, phoneNumber)));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Person> createNewPerson(String login, String password, String email, String name, String surname,
                                            String nip, String phoneNumber) {
        Optional<Person> optionalPerson = personRepository.findByLogin(login);
        if (optionalPerson.isEmpty()) {
            return Optional.of(personRepository.save(new Person(0L, login, makeHash(password),
                    email, name, surname, nip, phoneNumber)));
        } else {
            return Optional.empty();
        }
    }

    static String makeHash(String password) {
        return Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }

    public boolean makeAuthorization(String login, String password) {
        Optional<Person> person = getPersonByLogin(login);
        return person.filter(value -> Objects.equals(value.getPassword(), password)).isPresent();
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

    public void removePerson(Person person) {
        personRepository.delete(person);
    }

    public void removePeople(List<Person> person) {
        personRepository.deleteAll(person);
    }
}
