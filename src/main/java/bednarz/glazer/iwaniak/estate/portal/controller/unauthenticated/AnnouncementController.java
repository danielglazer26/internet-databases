package bednarz.glazer.iwaniak.estate.portal.controller.unauthenticated;

import bednarz.glazer.iwaniak.estate.portal.controller.ResponseJsonBody;
import bednarz.glazer.iwaniak.estate.portal.database.model.Photo;
import bednarz.glazer.iwaniak.estate.portal.database.services.AnnouncementService;
import bednarz.glazer.iwaniak.estate.portal.database.services.PhotoService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/public")
@Slf4j
public class AnnouncementController {
    private final AnnouncementService announcementService;
    private final PhotoService photoService;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService, PhotoService photoService) {
        this.announcementService = announcementService;
        this.photoService = photoService;
    }

    @GetMapping("/announcements")
    public ResponseEntity<?> filerAnnouncements(
            @RequestParam(required = false) Integer minCost, @RequestParam(required = false) Integer maxCost,
            @RequestParam(required = false) Double minArea, @RequestParam(required = false) Double maxArea,
            @RequestParam(required = false) Integer roomNumber, @RequestParam(required = false) String city,
            @RequestParam(required = false) String street, @RequestParam(required = false) String apartmentNumber,
            @RequestParam(required = false) Integer announcementType,
            @RequestParam Integer limit, @RequestParam Integer offset) {
        var list = announcementService.getFiltered(
                        minCost, maxCost,
                        minArea, maxArea,
                        roomNumber, city, street,
                        apartmentNumber, announcementType, limit, offset).stream()
                .map(AnnouncementRespond::new).toList();

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .body(list);
    }

    @GetMapping("/announcementCoverPhotoId")
    public ResponseEntity<?> getAnnouncementCoverPhotoId(@RequestParam("announcementId") Long announcementId) {
        Optional<Long> announcementCoverPhotoId = announcementService.getAnnouncementCoverPhotoId(announcementId);
        if (announcementCoverPhotoId.isPresent()) {
            return ResponseEntity.ok(announcementCoverPhotoId.get());
        } else {
            return ResponseEntity.badRequest().body(new ResponseJsonBody("Announcement don't have cover photo"));
        }
    }

    @GetMapping("/announcementPhotos")
    public ResponseEntity<?> getAnnouncementPhotos(@RequestParam("announcementId") Long announcementId) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .body(photoService.getPhotosByAnnouncementId(announcementId).stream().map(Photo::getPhotoId));
    }

    @GetMapping("/photo")
    public ResponseEntity<?> fileDownload(HttpServletResponse response, @RequestParam("photoId") Long photoId) {
        try {
            response.setHeader("Cache-Control", "max-age=600");
            StreamUtils.copy(photoService.getPhoto(photoId), response.getOutputStream());
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseJsonBody(e.getMessage()));
        }
    }

    @GetMapping(value = "/getPrivacyPolicy")
    public ResponseEntity<?> getPDF() throws IOException {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(Files.newInputStream(Path.of("polityka_prywatnosci.pdf"))));
    }
}
