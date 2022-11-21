package kopaczewski.szymczyk.glazer.estate.portal.controller.unauthenticated;

import kopaczewski.szymczyk.glazer.estate.portal.database.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/public/")
public class AnnouncementController {
    private final AnnouncementService service;

    @Autowired
    public AnnouncementController(AnnouncementService service) {
        this.service = service;
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
}
