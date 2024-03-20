package com.example.Hidrator.service.impl;

import com.example.Hidrator.dto.ApiResponse;
import com.example.Hidrator.dto.UserInfoDTO;
import com.example.Hidrator.entity.User;
import com.example.Hidrator.entity.UserInfo;
import com.example.Hidrator.exception.AuthException;
import com.example.Hidrator.exception.HidratorException;
import com.example.Hidrator.repository.AuthRepository;
import com.example.Hidrator.repository.UserInfoRepository;
import com.example.Hidrator.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.example.Hidrator.util.MapperClass.mapDtoToEntity;


@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService{
    private final UserInfoRepository userInfoRepository;

    private final AuthRepository authRepository;

    public ApiResponse getUserInfo(String username){
        UserInfo userInfo= userInfoRepository.findByUsername(username).orElseThrow();
        return new ApiResponse("userdeatils",userInfo);
    }

    public ApiResponse saveInfo(UserInfoDTO userInfoDTO) throws AuthException {
        //check user in auth before saving
        User user=authRepository.findByUsername(userInfoDTO.getUsername()).orElseThrow();

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

    public ApiResponse updateUserInfo(UserInfoDTO userInfoDTO) throws HidratorException {
        //retrieve user_info from db
        UserInfo userInfo = userInfoRepository.findByUsername(userInfoDTO.getUsername()).orElseThrow();
        //update values in info
//        mapEntityIgnoreNull(userInfoDTO,userInfo);
        mapDtoToEntity(userInfoDTO,userInfo,true);
        userInfo.setModifiedAt(Instant.now());
        User user = authRepository.findByUsername(userInfoDTO.getUsername()).orElseThrow();
        boolean updateFirstName = userInfoDTO.getFirstName() != null && !userInfoDTO.getFirstName().equals(user.getFirstName());
        boolean updateLastName = userInfoDTO.getLastName() != null && !userInfoDTO.getLastName().equals(user.getLastName());

// Update values if necessary
        if (updateFirstName) {
            user.setFirstName(userInfoDTO.getFirstName());
        }
        if (updateLastName) {
            user.setLastName(userInfoDTO.getLastName());
        }
// Update the user if there are changes
        if (updateFirstName || updateLastName) {
            authRepository.save(user);
        }
        UserInfo save = userInfoRepository.save(userInfo);
        return (new ApiResponse("user info updated",save));
    }
}
