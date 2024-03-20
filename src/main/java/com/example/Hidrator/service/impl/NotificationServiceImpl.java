package com.example.Hidrator.service.impl;

import com.example.Hidrator.entity.UserInfo;
import com.example.Hidrator.repository.UserInfoRepository;
import com.example.Hidrator.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    private final UserInfoRepository userInfoRepository;
    public String generateNotification(String username){

      UserInfo userInfo = userInfoRepository.findByUsername(username).orElseThrow();
        Integer waterQuantity= userInfo.getWaterTarget();
      //interval will be time in minutes
        if(waterQuantity>0)
        {
            userInfoRepository.save(userInfo);
            return generateMessage(userInfo);
        }else {
            return null;
        }

    }

    private String generateMessage(UserInfo userInfo) {
        return "lets Hidrate drink "+userInfo.getServing()+"ml water!! be healthy";
    }
}
