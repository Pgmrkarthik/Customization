package com.dev.customization.service.User;


import com.dev.customization.configuration.AppConstants;
import com.dev.customization.entity.Role;
import com.dev.customization.entity.User;
import com.dev.customization.entity.UserCredentials;
import com.dev.customization.payload.User.Request.UserRegistrationDTO;
import com.dev.customization.repository.RoleRepo;
import com.dev.customization.repository.UserCredentialsRepo;
import com.dev.customization.repository.user.UserRepository;
import com.fasterxml.jackson.core.Base64Variant;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserCredentialsRepo userCredentialsRepo;

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Override
    public User registerUser(UserRegistrationDTO userRegistrationDTO) throws Exception {
        User user = new User();

        try{

            user.setUsername(userRegistrationDTO.getUsername());
            user.setEmail(userRegistrationDTO.getEmail());

            UserCredentials userCredentials = new UserCredentials();
            Role role = roleRepo.findById(AppConstants.USER_ID).get();
            userCredentials.getRoles().add(role);
            userCredentials.setEmail(userRegistrationDTO.getEmail());
            userCredentials.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
            user.setUserCredentials(userCredentials);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return user;


    }
}
