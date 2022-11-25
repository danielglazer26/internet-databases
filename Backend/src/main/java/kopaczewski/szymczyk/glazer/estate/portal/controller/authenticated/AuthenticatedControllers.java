package kopaczewski.szymczyk.glazer.estate.portal.controller.authenticated;


import kopaczewski.szymczyk.glazer.estate.portal.controller.ResponseJsonBody;
import kopaczewski.szymczyk.glazer.estate.portal.database.model.Announcement;
import kopaczewski.szymczyk.glazer.estate.portal.database.services.AnnouncementService;
import kopaczewski.szymczyk.glazer.estate.portal.database.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static kopaczewski.szymczyk.glazer.estate.portal.ConstStorage.AUTHENTICATED_ENDPOINT;

@RestController
@CrossOrigin
@RequestMapping(AUTHENTICATED_ENDPOINT)
public class AuthenticatedControllers {

    private final AnnouncementService announcementService;
    private final PhotoService photoService;

    @Autowired
    public AuthenticatedControllers(AnnouncementService announcementService, PhotoService photoService) {
        this.announcementService = announcementService;
        this.photoService = photoService;
    }

    @PostMapping("/addAnnouncement")
    /* @PreAuthorize("hasAnyAuthority('USER')")*/
    public ResponseEntity<?> createAnnouncement(@RequestBody Announcement announcementRequest) {
        Optional<Announcement> announcement = announcementService.createNewAnnouncement(announcementRequest);
        return announcement
                .<ResponseEntity<?>>map(value -> ResponseEntity.ok(new ResponseJsonBody("Create announcement number: " + value.getAnnouncementId())))
                .orElseGet(() -> ResponseEntity.badRequest().body(new ResponseJsonBody("No announcement created")));
    }

    @PostMapping("/upload/")
    public ResponseEntity<?> fileUpload(@RequestParam("image") MultipartFile image, @RequestParam("announcementId") Long announcementId) {
        try {
            var id = photoService.createPhoto(image.getBytes(), announcementId);
            return id.map(photo -> ResponseEntity.ok(new ResponseJsonBody("Image " + photo.getPhotoId() + "loaded")))
                    .orElseGet(() -> ResponseEntity.badRequest().body(new ResponseJsonBody("Error occurs when add new picture to database")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
