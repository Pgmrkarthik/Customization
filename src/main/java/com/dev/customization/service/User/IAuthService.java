package com.dev.customization.service.User;

import com.dev.customization.payload.User.Request.UserLoginDTO;
import com.dev.customization.payload.User.Response.UserResponseDTO;

public interface IAuthService {

    UserResponseDTO login(UserLoginDTO loginDTO) throws Exception;

}
