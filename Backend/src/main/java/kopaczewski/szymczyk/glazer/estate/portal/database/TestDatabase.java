package kopaczewski.szymczyk.glazer.estate.portal.database;

import kopaczewski.szymczyk.glazer.estate.portal.database.model.Person;
import kopaczewski.szymczyk.glazer.estate.portal.database.model.Roles;
import kopaczewski.szymczyk.glazer.estate.portal.database.repositories.AnnouncementRepository;
import kopaczewski.szymczyk.glazer.estate.portal.database.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TestDatabase {

    private final AnnouncementRepository announcementRepository;

    private final PersonRepository personRepository;

    @Autowired
    public TestDatabase(AnnouncementRepository announcementRepository, PersonRepository personRepository) {
        this.announcementRepository = announcementRepository;
        this.personRepository = personRepository;
    }

    @PostConstruct
    public void start() {
        addUsers();
    }

    public void addUsers() {
        personRepository.save(new Person(0L, "robejcik", BCrypt.hashpw("12345678", BCrypt.gensalt()), "robercik@wp" +
                ".pl", "Robert", "Lewandowski", "1234563218", "680543260", Roles.USER));
        personRepository.save(new Person(
                0L,
                "admin",
                BCrypt.hashpw("$2a$10$T37ajL7I4YSOrs.2h6Vvi.fLQefjuAHmOxbf2NKlc1afev/g/AYi2", BCrypt.gensalt()),
                "admin@wp.pl",
                null,
                null,
                null,
                null,
                Roles.ADMIN
        ));

        personRepository.save(new Person(
                0L,
                "wojteczek",
                BCrypt.hashpw("$2a$10$T37ajL7I4YSOrs.2h6Vvi.fLQefjuAHmOxbf2NKlc1afev/g/AYi2", BCrypt.gensalt()),
                "wojteczek@gmail.com",
                "Wojtek",
                "Glazer",
                "5234564218",
                "280543260",
                Roles.USER
        ));
        personRepository.save(new Person(
                0L,
                "kamilBezUcha",
                BCrypt.hashpw("$2a$10$T37ajL7I4YSOrs.2h6Vvi.fLQefjuAHmOxbf2NKlc1afev/g/AYi2", BCrypt.gensalt()),
                "kamilBezUcha@gmail.com",
                "Kamil",
                "Kowalski",
                "5234523218",
                "7805432260",
                Roles.USER
        ));
        personRepository.save(new Person(
                0L,
                "wojak",
                BCrypt.hashpw("$2a$10$T37ajL7I4YSOrs.2h6Vvi.fLQefjuAHmOxbf2NKlc1afev/g/AYi2", BCrypt.gensalt()),
                "wojak@gmail.com",
                "Weronika",
                "Tytka",
                "1524234218",
                "232805430",
                Roles.USER
        ));
        personRepository.save(new Person(
                0L,
                "nieMamPomysluNaLogin",
                BCrypt.hashpw("$2a$10$T37ajL7I4YSOrs.2h6Vvi.fLQefjuAHmOxbf2NKlc1afev/g/AYi2", BCrypt.gensalt()),
                "nieMamPomysluNaLogin@gmail.com",
                "Marta",
                "Niezapominalska",
                "5234532618",
                "2180543260",
                Roles.USER
        ));
    }
}
