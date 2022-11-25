package kopaczewski.szymczyk.glazer.estate.portal.controller.unauthenticated;

import kopaczewski.szymczyk.glazer.estate.portal.database.services.AnnouncementService;
import kopaczewski.szymczyk.glazer.estate.portal.database.services.PhotoService;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/public/")
public class AnnouncementController {
    private final AnnouncementService service;
    private final PhotoService photoService;

    @Autowired
    public AnnouncementController(AnnouncementService service, PhotoService photoService) {
        this.service = service;
        this.photoService = photoService;
    }

    @GetMapping("/announcements/")
    public ResponseEntity<?> filerAnnouncements(
            @RequestParam(required = false) Integer minCost, @RequestParam(required = false) Integer maxCost,
            @RequestParam(required = false) Double minArea, @RequestParam(required = false) Double maxArea,
            @RequestParam(required = false) Integer roomNumber, @RequestParam(required = false) String city,
            @RequestParam(required = false) String street, @RequestParam(required = false) Integer apartmentNumber,
            @RequestParam Integer limit, @RequestParam Integer offset) {
        var list = service.getFiltered(minCost, maxCost, minArea, maxArea, roomNumber, city, street, apartmentNumber, limit, offset);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/upload/")
    public ResponseEntity<?> fileUpload(@RequestParam("image") MultipartFile image, @RequestParam("announcementId") Long announcementId) {
        try {
            var id = photoService.createPhoto(image.getBytes(), announcementId);
            return id.map(photo -> ResponseEntity.ok("Image " + photo.getPhotoId() + "loaded"))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error occurs when add new picture to database"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/photo/")
    public HttpEntity<?> fileDownload(HttpServletResponse response, @RequestParam("photoId") Long photoId) throws IOException {

        StreamUtils.copy(photoService.getPhoto(photoId), response.getOutputStream());
        response.setContentType(MediaType.IMAGE_PNG_VALUE);

        return ResponseEntity.ok().build();
    }
}
