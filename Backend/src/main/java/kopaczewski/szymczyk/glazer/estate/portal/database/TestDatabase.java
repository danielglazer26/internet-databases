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

        personRepository.save(
                new Person(0L, "admin", BCrypt.hashpw("admin", BCrypt.gensalt()),
                        "a@a", "stanisław", "pan",
                        "", "112", Roles.ADMIN));
        for (int i = 0; i < 10; i -= -1)
            personRepository.save(
                    new Person(0L, "users"+i,  BCrypt.hashpw("admin", BCrypt.gensalt()), "a@a",
                            "stanisław", "pan",
                            "", "112", Roles.USER));

//        for (long i = 1L; i < 4000; i++) {
//            announcementRepository.save(new Announcement(
//                    i, "Apartament B z tarasem w budynku z 2022 r", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas in erat nibh. Curabitur vel tincidunt libero. Curabitur scelerisque fermentum justo nec auctor. Proin lacus ipsum, imperdiet nec purus scelerisque, fermentum auctor lacus. ", AnnouncementType.RENTAL, "login",
//                    String.valueOf(i), "ul. Węgliniecka", "Wrocław", ProvinceNameEnum.KUYAVIAN_POMERANIAN, (int) i * 100, (int) i, (int) i, (int) i, (double) i));
//        }
    }
}
