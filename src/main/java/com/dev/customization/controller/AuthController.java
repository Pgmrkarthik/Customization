package com.dev.customization.controller;


import com.dev.customization.entity.User;
import com.dev.customization.entity.UserCredentials;
import com.dev.customization.payload.FolderCopyDTO;
import com.dev.customization.payload.JWTResponse;
import com.dev.customization.payload.User.Request.UserLoginDTO;
import com.dev.customization.payload.User.Request.UserRegistrationDTO;

import com.dev.customization.security.JWTUtil;
import com.dev.customization.service.AWS.S3Service;
import com.dev.customization.service.User.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtils;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody UserLoginDTO loginRequest) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        System.out.println("What happens!");

        System.out.println(authentication);

        // Generate JWT Token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        System.out.println(userDetails.getUsername());
        String jwt = jwtUtils.generateToken(userDetails.getUsername());

        JWTResponse jwtResponse = new JWTResponse();
        jwtResponse.setJwtToken(jwt);
        jwtResponse.setMessage("User Login Success");

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO signupRequest) throws Exception {
        // Save user with encoded password

        User user = userService.registerUser(signupRequest);

        if(user == null) {
            throw new Exception("User could not be created");
        }

        return ResponseEntity.ok("User registered successfully");
    }


    // Copy Folder test
    @PostMapping("/copy")
    public ResponseEntity<String> copyPasteS3Bucket(@RequestBody FolderCopyDTO folderCopyDTO) throws Exception {

        System.out.println(folderCopyDTO);
        // Save user with encoded password

//        User user = userService.registerUser(signupRequest);

        long number = 5L;

      String responseStr =   s3Service.copyFolder(number);



        return ResponseEntity.ok(responseStr);
    }


}
