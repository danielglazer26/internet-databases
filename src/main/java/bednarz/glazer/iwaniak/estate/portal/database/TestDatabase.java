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
import com.luciad.imageio.webp.WebPWriteParam;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TestDatabase {
    private static final int ANNOUNCEMENTS_NUMBER = 100;
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
    private static final int PHOTOS_PER_ANNOUNCEMENT = 2;
    private static final boolean IS_WEBP = false;
    private static final boolean SHOULD_GENERATE_WEBP = false;
    private static final boolean SHOULD_COMPRESS = false;
    private static final float COMPRESSION_FACTOR = 0.3f;

    private final AnnouncementRepository announcementRepository;

    private final PersonRepository personRepository;
    private final PhotoRepository photoRepository;
    private final Random random;

    @Value("classpath:photos/webp/*")
    private Resource[] webpResources;

    @Value("classpath:photos/default/*")
    private Resource[] defaultResources;

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
        if (SHOULD_GENERATE_WEBP) {
            generateWebps();
        }
        List<Resource> rawPhotos = Arrays.stream(IS_WEBP ? webpResources : defaultResources).collect(Collectors.toCollection(ArrayList::new));
        Iterator<Resource> iterator = rawPhotos.iterator();

        for (int i = 0; i < ANNOUNCEMENTS_NUMBER; i++) {
            Announcement announcement = announcementRepository.save(Announcement.builder()
                    .title("Ogłoszenie testowe " + i)
                    .additionalDescription("Testowe ogłoszenie testowe " + i)
                    .announcementType(i < ANNOUNCEMENTS_NUMBER / 2 ? AnnouncementType.RENTAL : AnnouncementType.SALE)
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

            for (int j = 0; j < PHOTOS_PER_ANNOUNCEMENT; j++) {
                if (!iterator.hasNext()) {
                    iterator = rawPhotos.iterator();
                }
                Photo photo = photoRepository.save(Photo.builder()
                        .announcementId(announcement.getAnnouncementId())
                        .pictureBytes(getPhotoResource(iterator.next()))
                        .build());
                announcement.setCoverPhotoId(photo.getPhotoId());
            }

            announcementRepository.save(announcement);
        }
    }

    private byte[] getPhotoResource(Resource rawPhoto) {
        try {
            return rawPhoto.getInputStream().readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateWebps() {
        for (Resource resource: defaultResources) {
            generateWebp(resource);
        }
    }

    private void generateWebp(Resource rawPhoto) {
        if (SHOULD_COMPRESS) {
            generateCompressedWebp(rawPhoto);
        } else {
            generateUncompressedWebp(rawPhoto);
        }
    }

    private void generateCompressedWebp(Resource rawPhoto) {
        ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
        WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSY_COMPRESSION]);
        writeParam.setCompressionQuality(COMPRESSION_FACTOR);
        try {
            BufferedImage image = ImageIO.read(rawPhoto.getFile());
            String path = rawPhoto.getFile().getParentFile().getParent() + "\\webp\\"
                    + Objects.requireNonNull(rawPhoto.getFilename()).split("\\.")[0] + ".webp";
            writer.setOutput(new FileImageOutputStream(new File(path)));
            writer.write(null, new IIOImage(image, null, null), writeParam);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateUncompressedWebp(Resource rawPhoto) {
        try {
            BufferedImage image = ImageIO.read(rawPhoto.getFile());
            String path = rawPhoto.getFile().getParentFile().getParent() + "\\webp\\"
                    + Objects.requireNonNull(rawPhoto.getFilename()).split("\\.")[0] + ".webp";
            ImageIO.write(image, "webp", new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
