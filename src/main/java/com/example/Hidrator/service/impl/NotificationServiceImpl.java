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
        Instant modifiedTime = userInfo.getModifiedAt();
        Instant now = Instant.now();
      //interval will be time in minutes
        if(modifiedTime==null)
        {
            userInfo.setModifiedAt(now);
            userInfoRepository.save(userInfo);
            return generateMessage(userInfo);
        }
        int interval =userInfo.getInterval();
        Instant calculated = modifiedTime.plus(interval, ChronoUnit.MINUTES);

        long differenceInSeconds = Math.abs(Duration.between(now, calculated).getSeconds());
        if(differenceInSeconds<=30){
           return generateMessage(userInfo);
        }else {
            return null;
        }
    }

    private String generateMessage(UserInfo userInfo) {
        return "lets Hidrate drink "+userInfo.getWaterTarget()/ userInfo.getInterval()+"ml water!! be healthy";
    }
}
