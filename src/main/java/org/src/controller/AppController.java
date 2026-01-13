package org.src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.src.model.App;
import org.src.model.Review;
import org.src.service.AppService;

import java.util.Collection;

@RestController
@RequestMapping("/api/apps")
public class AppController {

    @Autowired
    private AppService appService;

    @PostMapping
    public ResponseEntity<String> createApp(@RequestBody final App app) {
        try {
            appService.createApp(app);
            return ResponseEntity.ok("App Created Successfully!");
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().body(" Error: " + exception.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Collection<App>> listApps() {
        return ResponseEntity.ok(appService.listApps());
    }

    @PutMapping
    public ResponseEntity<String> updateApp(@RequestBody final App app) {
        try {
            appService.updateApp(app);
            return ResponseEntity.ok("App Updated Successfully!");
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().body("Error: " + exception.getMessage());
        }
    }

    @DeleteMapping("/{appId}")
    public ResponseEntity<String> deleteApp(@PathVariable final int appId, @RequestParam final int authorId) {
        try {
            appService.deleteApp(appId, authorId);
            return ResponseEntity.ok("App Deleted Successfully!");
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().body(" Error: " + exception.getMessage());
        }
    }

    @PostMapping("/install")
    public ResponseEntity<String> installApp(@RequestParam final int userId, @RequestParam final int appId) {
        try {
            appService.installApp(userId, appId);
            return ResponseEntity.ok("App Installed Successfully!");
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().body("Error: " + exception.getMessage());
        }
    }

    @PostMapping("/uninstall")
    public ResponseEntity<String> unInstallApp(@RequestParam final int userId, @RequestParam final int appId) {
        try {
            appService.unInstallApp(userId, appId);
            return ResponseEntity.ok("App Uninstalled Successfully!");
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().body("Error: " + exception.getMessage());
        }
    }

    @PostMapping("/review")
    public ResponseEntity<String> writeReview(@RequestBody final Review review) {
        try {
            appService.writeReview(review);
            return ResponseEntity.ok("Review Added Successfully!");
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().body("Error: " + exception.getMessage());
        }
    }
}
