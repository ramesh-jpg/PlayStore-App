package org.src.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.src.model.App;
import org.src.service.AppReportService;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private AppReportService reportService;

    @GetMapping("/installed/{userId}")
    public ResponseEntity<Collection<App>> showInstalledApps(@PathVariable final int userId) {
        final Collection<App> apps = reportService.showInstalledApps(userId);

        if (apps.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(apps);
    }

    @GetMapping("/author")
    public ResponseEntity<Map<String, Object>> countInstallByAuthor(@RequestParam final String authorName) {
        final int count = reportService.countInstallByAuthor(authorName);
        final Map<String, Object> response = new HashMap<>();
        response.put("author", authorName);
        response.put("totalInstalls", count);

        return ResponseEntity.ok(response);
    }
}