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
                    "title"+i, "description"+i, "login"+i, (int)i,
                    "street"+i, "city"+i, (int)i, (int)i, (int)i, (int)i, (double)i));
        }
    }
}
