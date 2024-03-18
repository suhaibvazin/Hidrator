package com.example.Hidrator.service.impl;

import com.example.Hidrator.dto.ApiResponse;
import com.example.Hidrator.dto.UserInfoDTO;
import com.example.Hidrator.entity.User;
import com.example.Hidrator.entity.UserInfo;
import com.example.Hidrator.exception.AuthException;
import com.example.Hidrator.repository.AuthRepository;
import com.example.Hidrator.repository.UserInfoRepository;
import com.example.Hidrator.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService{
    private final UserInfoRepository userInfoRepository;

    private final AuthRepository authRepository;

    public ApiResponse saveInfo(UserInfoDTO userInfoDTO) throws AuthException {
        //check user in auth before saving
        User user=authRepository.findByUsername(userInfoDTO.getUsername()).orElseThrow();

        if(userInfoRepository.findByUsername(userInfoDTO.getUsername()).isPresent()){
            throw new AuthException("User info already saved");
        }
        UserInfo userInfo= UserInfo.builder()
                .interval(userInfoDTO.getInterval())
                .firstName(userInfoDTO.getFirstName())
                .lastName(userInfoDTO.getLastName())
                .waterTarget(userInfoDTO.getWaterTarget())
                .username(userInfoDTO.getUsername())
                .user(user)
                .build();
        UserInfo savedUser = userInfoRepository.save(userInfo);

        return (new ApiResponse("user info saved successfully",savedUser));
    }

    public ApiResponse updateUserInfo(UserInfoDTO userInfoDTO)
    {
        //retrieve user info from db
        UserInfo userInfo = userInfoRepository.findByUsername(userInfoDTO.getUsername()).orElseThrow();
        User user = authRepository.findByUsername(userInfoDTO.getUsername()).orElseThrow();
        //update values in info
        userInfo.setInterval(userInfoDTO.getInterval());
        userInfo.setWaterTarget(userInfoDTO.getWaterTarget());
        userInfo.setFirstName(userInfoDTO.getFirstName());
        userInfo.setLastName(userInfoDTO.getLastName());
        boolean updateFirstName =!user.getFirstName().equals(userInfoDTO.getFirstName());
        boolean updateLastName=!user.getLastName().equals(userInfoDTO.getLastName());
        //update value in auth_user
        if(updateFirstName)
            user.setFirstName(userInfoDTO.getFirstName());
        if(updateLastName)
            user.setLastName(userInfoDTO.getLastName());
        //update the userInfo and auth_user
        if(updateFirstName || updateLastName)
            authRepository.save(user);
        UserInfo save = userInfoRepository.save(userInfo);
        return (new ApiResponse("user info updated",save));
    }
}
