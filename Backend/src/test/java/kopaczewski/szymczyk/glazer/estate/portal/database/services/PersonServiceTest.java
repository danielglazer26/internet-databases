package kopaczewski.szymczyk.glazer.estate.portal.database.services;


import kopaczewski.szymczyk.glazer.estate.portal.database.model.Person;
import kopaczewski.szymczyk.glazer.estate.portal.database.model.Roles;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class PersonServiceTest {
    @Autowired
    PersonService service;
    Person person0, person1, person2, person3;

    @Before
    public void setUp() {
        System.out.println("cos");
        person0 = new Person(0L, "LoginPer0", "PasswordPer0", "mailPer0@niew4art0.com", null, null, null, null, Roles.USER);
        person1 = new Person(0L, "LoginPer1", "PasswordPer2", "mailPer1@niew4art0.com", null, null, null, null, Roles.USER);
        person2 = new Person(0L, "LoginPer2", "PasswordPer2", "mailPer2@niew4art0.com",
                "NamePer2", "SurnamePer2", null, "123456789", Roles.USER);
        person3 = new Person(0L, "LoginPer3", "PasswordPer3", "mailPer3@niew4art0.com",
                "NamePer3", "SurnamePer3", "nip", "901112131", Roles.USER);
    }

    @AfterEach
    public void tearDown() {
        service.removePerson(person0);
        service.removePerson(person1);
        service.removePerson(person2);
        service.removePerson(person3);
    }

    private Optional<Person> getOptionalPerson(Person person0) {
        //noinspection UnnecessaryLocalVariable
        var personOptional = service.createNewPerson(person0.getLogin(), person0.getPassword(), person0.getEmail());
        return personOptional;
    }


    @Test
    public void createNewPerson_WhenSomeIsNullAndDataIsCorrect_ShouldReturnPerson() {
        Optional<Person> optionalPerson = getOptionalPerson(person1);
        var filterRes = service.getAllPeople().stream()
                .filter(person -> person.getLogin().equals(optionalPerson.get().getLogin())).findFirst();
        if (filterRes.isPresent()) {
            Assertions.assertEquals(optionalPerson.get(), filterRes.get());
        } else {
            Assertions.fail();
        }
    }

    @Test
    public void createNewPerson_WhenMostDataIsNotNullAndDataIsCorrect_ShouldReturnPerson() {
        var optionalPerson = service.createNewPerson(person2.getLogin(), person2.getPassword(), person2.getEmail(),
                person2.getName(), person2.getSurname(), person2.getPhoneNumber());
        var filterRes = service.getAllPeople().stream()
                .filter(person -> person.getLogin().equals(optionalPerson.get().getLogin())).findFirst();
        if (filterRes.isPresent()) {
            Assertions.assertEquals(optionalPerson.get(), filterRes.get());
        } else {
            Assertions.fail();
        }
    }

    @Test
    public void createNewPerson_WhenAllDataIsNotNullAndDataIsCorrect_ShouldReturnPerson() {
        var optionalPerson = service.createNewPerson(person3.getLogin(), person3.getPassword(), person3.getEmail(),
                person3.getName(), person3.getSurname(), person3.getNip(), person3.getPhoneNumber());
        var filterRes = service.getAllPeople().stream()
                .filter(person -> person.getLogin().equals(optionalPerson.get().getLogin())).findFirst();
        if (filterRes.isPresent()) {
            Assertions.assertEquals(optionalPerson.get(), filterRes.get());
        } else {
            Assertions.fail();
        }
    }

    @Test
    public void getPersonByLogin_WhenUserIsInDatabase_ShouldReturnPerson() {
        Optional<Person> personOptional = getOptionalPerson(person0);
        Assertions.assertEquals(personOptional.get(), service.getPersonByLogin(personOptional.get().getLogin()).get());
    }

    @Test
    public void getPersonById_WhenUserIsInDatabase_ShouldReturnPerson() {
        Optional<Person> personOptional = getOptionalPerson(person0);
        Assertions.assertEquals(personOptional.get(), service.getPersonById(personOptional.get().getPersonId()).get());
    }
}