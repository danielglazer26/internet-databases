package kopaczewski.szymczyk.glazer.estate.portal.controller.authenticated;


import kopaczewski.szymczyk.glazer.estate.portal.database.model.Announcement;
import kopaczewski.szymczyk.glazer.estate.portal.database.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static kopaczewski.szymczyk.glazer.estate.portal.ConstStorage.AUTHENTICATED_ENDPOINT;

@RestController
@RequestMapping(AUTHENTICATED_ENDPOINT)
public class AuthenticatedControllers {

    private final AnnouncementService announcementService;

    @Autowired
    public AuthenticatedControllers(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PostMapping("/addAnnouncement")
    /* @PreAuthorize("hasAnyAuthority('USER')")*/
    public ResponseEntity<?> createAnnouncement(@RequestBody AnnouncementRequest announcementRequest) {
        Optional<Announcement> announcement = announcementService.createNewAnnouncement(announcementRequest);
        return announcement
                .<ResponseEntity<?>>map(value -> ResponseEntity.ok("Create announcement number: " + value.getAnnouncementId()))
                .orElseGet(() -> ResponseEntity.badRequest().body("No announcement created"));
    }

}