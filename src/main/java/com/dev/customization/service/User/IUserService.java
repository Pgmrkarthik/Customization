package com.dev.customization.service.User;

import com.dev.customization.entity.User;
import com.dev.customization.payload.User.Request.UserRegistrationDTO;

public interface IUserService {

    User registerUser(UserRegistrationDTO userRegistrationDTO) throws Exception;


}
