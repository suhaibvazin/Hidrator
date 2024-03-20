package com.example.Hidrator.controller;

import com.example.Hidrator.dto.ApiResponse;
import com.example.Hidrator.dto.UserInfoDTO;
import com.example.Hidrator.exception.AuthException;
import com.example.Hidrator.exception.HidratorException;
import com.example.Hidrator.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserInfoController {
    private final UserInfoService userInfoService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> saveUserInfo(@RequestBody UserInfoDTO userInfoDTO) throws AuthException {
        return ResponseEntity.ok(userInfoService.saveInfo(userInfoDTO));
    }

    @PutMapping("/updateInfo")
    public ResponseEntity<ApiResponse> updateInfo(@RequestBody UserInfoDTO userInfoDTO) throws HidratorException {
        if(userInfoDTO.getUsername()==null)
            throw new HidratorException("user name cannot be null");
        return ResponseEntity.ok(userInfoService.updateUserInfo(userInfoDTO));
    }

    @GetMapping("/getUser/{username}")
    public ResponseEntity<ApiResponse> getUserInfo(@PathVariable String username){
        return ResponseEntity.ok(userInfoService.getUserInfo(username));
    }

}
