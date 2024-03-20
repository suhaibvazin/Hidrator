package com.example.Hidrator.service;

import com.example.Hidrator.dto.ApiResponse;
import com.example.Hidrator.dto.UserInfoDTO;
import com.example.Hidrator.exception.AuthException;
import com.example.Hidrator.exception.HidratorException;

public interface UserInfoService {
     ApiResponse saveInfo(UserInfoDTO userInfoDTO) throws AuthException;

    ApiResponse updateUserInfo(UserInfoDTO userInfoDTO) throws HidratorException;

    ApiResponse getUserInfo(String username);
}
