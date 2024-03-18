package com.example.Hidrator.service;

import com.example.Hidrator.dto.ApiResponse;
import com.example.Hidrator.dto.UserInfoDTO;
import com.example.Hidrator.exception.AuthException;

public interface UserInfoService {
     ApiResponse saveInfo(UserInfoDTO userInfoDTO) throws AuthException;

    ApiResponse updateUserInfo(UserInfoDTO userInfoDTO);
}
