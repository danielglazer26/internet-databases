package kopaczewski.szymczyk.glazer.estate.portal.database;

import kopaczewski.szymczyk.glazer.estate.portal.database.model.announcement.Announcement;
import kopaczewski.szymczyk.glazer.estate.portal.database.model.announcement.AnnouncementType;
import kopaczewski.szymczyk.glazer.estate.portal.database.model.announcement.ProvinceNameEnum;
import kopaczewski.szymczyk.glazer.estate.portal.database.repositories.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        for (long i = 1L; i < 4000; i++) {
            announcementRepository.save(new Announcement(
                    i, "Apartament B z tarasem w budynku z 2022 r", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas in erat nibh. Curabitur vel tincidunt libero. Curabitur scelerisque fermentum justo nec auctor. Proin lacus ipsum, imperdiet nec purus scelerisque, fermentum auctor lacus. ", AnnouncementType.RENTAL, "login",
                    String.valueOf(i), "ul. Węgliniecka", "Wrocław", ProvinceNameEnum.KUYAVIAN_POMERANIAN, (int) i * 100, (int) i, (int) i, (int) i, (double) i));
        }
    }
}
