package com.example.Hidrator.controller;

import com.example.Hidrator.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("notification")
public class NotificationController {
    private final NotificationService notificationService;
    @PutMapping("/generate/{username}")
    public ResponseEntity<String> generateNotification(@PathVariable String username){
        String message = notificationService.generateNotification(username);

        if (message != null) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not ready yet");
        }
    }
}