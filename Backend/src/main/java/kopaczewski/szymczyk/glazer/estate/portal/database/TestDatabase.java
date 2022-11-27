package kopaczewski.szymczyk.glazer.estate.portal.database;

import kopaczewski.szymczyk.glazer.estate.portal.database.model.Announcement;
import kopaczewski.szymczyk.glazer.estate.portal.database.repositories.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TestDatabase {

    private final AnnouncementRepository announcementRepository;

    @Autowired
    public TestDatabase(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @PostConstruct
    public void start() {
        for( long i = 1L; i < 4000; i++){
            announcementRepository.save(new Announcement(i,
                    "Apartament B z tarasem w budynku z 2022 r", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas in erat nibh. Curabitur vel tincidunt libero. Curabitur scelerisque fermentum justo nec auctor. Proin lacus ipsum, imperdiet nec purus scelerisque, fermentum auctor lacus. ", "login", (int)i,
                    "ul. Węgliniecka", "Wrocław", (int)i * 100, (int)i, (int)i, (int)i, (double)i));
        }
    }
}
