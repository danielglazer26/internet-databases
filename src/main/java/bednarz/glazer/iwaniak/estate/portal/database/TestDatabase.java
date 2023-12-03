package bednarz.glazer.iwaniak.estate.portal.database;

import bednarz.glazer.iwaniak.estate.portal.database.model.Person;
import bednarz.glazer.iwaniak.estate.portal.database.model.Photo;
import bednarz.glazer.iwaniak.estate.portal.database.model.Roles;
import bednarz.glazer.iwaniak.estate.portal.database.model.announcement.Announcement;
import bednarz.glazer.iwaniak.estate.portal.database.model.announcement.AnnouncementType;
import bednarz.glazer.iwaniak.estate.portal.database.model.announcement.ProvinceNameEnum;
import bednarz.glazer.iwaniak.estate.portal.database.repositories.AnnouncementRepository;
import bednarz.glazer.iwaniak.estate.portal.database.repositories.PersonRepository;
import bednarz.glazer.iwaniak.estate.portal.database.repositories.PhotoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestDatabase {
    private static final int ANNOUNCEMENTS_NUMBER = 1000;
    private static final int MIN_APARTMENT_NUMBER = 1;
    private static final int MAX_APARTMENT_NUMBER = 100;
    private static final int MIN_COST_PER_MONTH = 1000;
    private static final int MAX_COST_PER_MONTH = 10000;
    private static final int MIN_RENT = 500;
    private static final int MAX_RENT = 5000;
    private static final int MIN_DEPOSIT = 3000;
    private static final int MAX_DEPOSIT = 30000;
    private static final int MIN_ROOM_NUMBER = 1;
    private static final int MAX_ROOM_NUMBER = 7;
    private static final double MIN_AREA = 50.5;
    private static final double MAX_AREA = 600.0;
    private static final int MIN_PHOTOS_PER_ANNOUNCEMENT = 2;
    private static final int MAX_PHOTOS_PER_ANNOUNCEMENT = 5;

    private final AnnouncementRepository announcementRepository;

    private final PersonRepository personRepository;
    private final PhotoRepository photoRepository;
    private final Random random;
    @Value("classpath:photos/*")
    private Resource[] photoResources;

    @Autowired
    public TestDatabase(AnnouncementRepository announcementRepository, PersonRepository personRepository, PhotoRepository photoRepository) {
        this.announcementRepository = announcementRepository;
        this.personRepository = personRepository;
        this.photoRepository = photoRepository;
        random = new Random();
    }

    @PostConstruct
    public void start() {
        addUsers();
        addAnnouncements();
    }

    //Password -> Admin123!
    public void addUsers() {
        personRepository.save(new Person(0L, "robejcik", BCrypt.hashpw("robejcik", BCrypt.gensalt()), "robercik@wp" +
                ".pl", "Robert", "Lewandowski", "1234563218", "680543260", Roles.USER));
        personRepository.save(new Person(
                0L,
                "admin",
                BCrypt.hashpw("admin", BCrypt.gensalt()),
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
                BCrypt.hashpw("wojteczek", BCrypt.gensalt()),
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
                BCrypt.hashpw("kamilBezUcha", BCrypt.gensalt()),
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
                BCrypt.hashpw("wojak", BCrypt.gensalt()),
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
                BCrypt.hashpw("nieMamPomysluNaLogin", BCrypt.gensalt()),
                "nieMamPomysluNaLogin@gmail.com",
                "Marta",
                "Niezapominalska",
                "5234532618",
                "2180543260",
                Roles.USER
        ));
    }

    public void addAnnouncements() {
        List<byte[]> rawPhotos = Arrays.stream(photoResources).map(resource -> {
            try {
                return resource.getInputStream().readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toCollection(ArrayList::new));

        for (int i = 0; i < ANNOUNCEMENTS_NUMBER; i++) {
            Announcement announcement = announcementRepository.save(Announcement.builder()
                    .title("Ogłoszenie testowe " + i)
                    .additionalDescription("Testowe ogłoszenie testowe " + i)
                    .announcementType(random.nextBoolean() ? AnnouncementType.RENTAL : AnnouncementType.SALE)
                    .ownerLogin("robejcik")
                    .apartmentNumber(String.valueOf(random.nextInt(MIN_APARTMENT_NUMBER, MAX_APARTMENT_NUMBER)))
                    .street("Testowa ulica " + i)
                    .city("Testowe miasto " + i)
                    .province(ProvinceNameEnum.values()[random.nextInt(ProvinceNameEnum.values().length)])
                    .costPerMonth(random.nextInt(MIN_COST_PER_MONTH, MAX_COST_PER_MONTH))
                    .rent(random.nextInt(MIN_RENT, MAX_RENT))
                    .deposit(random.nextInt(MIN_DEPOSIT, MAX_DEPOSIT))
                    .roomNumber(random.nextInt(MIN_ROOM_NUMBER, MAX_ROOM_NUMBER))
                    .area(random.nextDouble(MIN_AREA, MAX_AREA))
                    .build());

            Collections.shuffle(rawPhotos);
            List<Photo> photos = rawPhotos.subList(0, random.nextInt(MIN_PHOTOS_PER_ANNOUNCEMENT, MAX_PHOTOS_PER_ANNOUNCEMENT)).stream()
                    .map(rawPhoto -> photoRepository.save(Photo.builder()
                            .announcementId(announcement.getAnnouncementId())
                            .pictureBytes(rawPhoto)
                            .build()))
                    .toList();

            announcement.setCoverPhotoId(photos.get(0).getPhotoId());
            announcementRepository.save(announcement);
        }
    }
}
