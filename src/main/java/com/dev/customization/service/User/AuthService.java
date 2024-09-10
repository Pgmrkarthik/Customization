package com.dev.customization.service.User;

import com.dev.customization.payload.User.Request.UserLoginDTO;
import com.dev.customization.payload.User.Response.UserResponseDTO;
import com.dev.customization.repository.user.UserRepository;
import com.dev.customization.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService UserService;

    @Autowired
    private JWTUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;



    @Override
    public UserResponseDTO login(UserLoginDTO loginDTO) throws Exception{

        System.out.println(loginDTO);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid credentials", e);
        }

        return null;
    }


}
