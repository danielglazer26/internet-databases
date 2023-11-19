package bednarz.glazer.iwaniak.estate.portal.controller.authenticated;


import bednarz.glazer.iwaniak.estate.portal.controller.ResponseJsonBody;
import bednarz.glazer.iwaniak.estate.portal.database.model.Person;
import bednarz.glazer.iwaniak.estate.portal.database.model.Roles;
import bednarz.glazer.iwaniak.estate.portal.database.model.announcement.Announcement;
import bednarz.glazer.iwaniak.estate.portal.database.services.AllModelsService;
import bednarz.glazer.iwaniak.estate.portal.database.services.AnnouncementService;
import bednarz.glazer.iwaniak.estate.portal.database.services.PersonService;
import bednarz.glazer.iwaniak.estate.portal.database.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static bednarz.glazer.iwaniak.estate.portal.ConstStorage.AUTHENTICATED_ENDPOINT;

@RestController
@CrossOrigin
@RequestMapping(AUTHENTICATED_ENDPOINT)
public class AuthenticatedControllers {

    private final AnnouncementService announcementService;
    private final PhotoService photoService;
    private final PersonService personService;

    private final AllModelsService allModelsService;

    @Autowired
    public AuthenticatedControllers(AnnouncementService announcementService, PhotoService photoService, PersonService personService, AllModelsService allModelsService) {
        this.announcementService = announcementService;
        this.photoService = photoService;
        this.personService = personService;
        this.allModelsService = allModelsService;
    }

    @PostMapping("/addAnnouncement")
    public ResponseEntity<?> createAnnouncement(@RequestBody Announcement announcementRequest) {
        try {
            Optional<Announcement> announcement = announcementService.createNewAnnouncement(announcementRequest);
            return announcement
                    .<ResponseEntity<?>>map(value -> ResponseEntity.ok(
                            new ResponseJsonBody("Create announcement number: " + value.getAnnouncementId())))
                    .orElseGet(() -> ResponseEntity.badRequest().body(
                            new ResponseJsonBody("No announcement created")));
        } catch (Exception e) {
            return ResponseEntity.accepted().body(new ResponseJsonBody(e.getMessage()));
        }
    }

    @GetMapping("/getPersonalAnnouncement")
    public ResponseEntity<List<Announcement>> getPersonalAnnouncements(@RequestParam String ownerLogin) {
        var personalAnnouncements = announcementService.getAnnouncementsByOwnerLogin(ownerLogin);
        return ResponseEntity.ok(personalAnnouncements);
    }

    @PutMapping("/updateAnnouncement")
    public ResponseEntity<?> updateAnnouncement(@RequestBody Announcement announcement) {
        try {
            announcementService.updateAnnouncement(announcement);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseJsonBody(e.getMessage()));
        }
    }

    @DeleteMapping("/destroyAnnouncement")
    public ResponseEntity<?> deleteAnnouncement(@RequestParam Long announcementId) {
        try {
            announcementService.removeAnnouncement(announcementId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> fileUpload(@RequestParam("image") MultipartFile image, @RequestParam("announcementId") Long announcementId) {
        try {
            var id = photoService.createPhoto(image.getBytes(), announcementId);
            return id.map(photo -> ResponseEntity.ok(new ResponseJsonBody(photo.getPhotoId().toString())))
                    .orElseGet(() -> ResponseEntity.badRequest().body(new ResponseJsonBody("Error occurs when add new picture to database")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/listUsers")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(personService.getAllUsers());
    }

    @DeleteMapping("/removeUser")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> deleteUser(@RequestParam("personToDelete") Long personToDelete) {
        Optional<Person> personById = personService.getPersonById(personToDelete);
        if (personById.isPresent()) {
            if(personById.get().getRole().equals(Roles.ADMIN)){
                return ResponseEntity.badRequest().body("You cant delete admin account!");
            }
            allModelsService.cascadeDelete(personById.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("There is no such user with given Id");
        }
    }
}
