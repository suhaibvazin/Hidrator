package com.example.Hidrator.controller;

import com.example.Hidrator.dto.ApiResponse;
import com.example.Hidrator.dto.UserInfoDTO;
import com.example.Hidrator.service.NotificationService;
import com.example.Hidrator.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("notification")
public class NotificationController {
    private final UserInfoService userInfoService;
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

    @PutMapping("/updateProgress")
    public ResponseEntity<ApiResponse> updateProgress(@RequestBody UserInfoDTO userInfoDTO){
        return ResponseEntity.ok(userInfoService.updateUserInfo(userInfoDTO));
    }
}