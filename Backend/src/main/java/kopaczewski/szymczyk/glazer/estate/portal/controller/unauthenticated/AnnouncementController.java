package kopaczewski.szymczyk.glazer.estate.portal.controller.unauthenticated;

import kopaczewski.szymczyk.glazer.estate.portal.controller.ResponseJsonBody;
import kopaczewski.szymczyk.glazer.estate.portal.database.model.Photo;
import kopaczewski.szymczyk.glazer.estate.portal.database.services.AnnouncementService;
import kopaczewski.szymczyk.glazer.estate.portal.database.services.PhotoService;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
            @RequestParam(required = false) String street, @RequestParam(required = false) String apartmentNumber,
            @RequestParam(required = false) Integer announcementType,
            @RequestParam Integer limit, @RequestParam Integer offset) {
        var list = service.getFiltered(
                minCost, maxCost,
                minArea, maxArea,
                roomNumber, city, street,
                apartmentNumber,announcementType, limit, offset);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/announcementPhotos")
    public ResponseEntity<?> getAnnouncementPhotos(@RequestParam("announcementId") Long announcementId){
        return ResponseEntity.ok(photoService.getPhotosByAnnouncementId(announcementId).stream().map(Photo::getPhotoId));
    }

    @GetMapping("/photo/")
    public HttpEntity<?> fileDownload(HttpServletResponse response, @RequestParam("photoId") Long photoId){
        try {
            StreamUtils.copy(photoService.getPhoto(photoId), response.getOutputStream());
            response.setContentType(MediaType.IMAGE_PNG_VALUE);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseJsonBody(e.getMessage()));
        }
    }
}
