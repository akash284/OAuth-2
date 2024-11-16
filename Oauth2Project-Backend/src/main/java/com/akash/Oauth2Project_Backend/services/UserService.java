package com.akash.Oauth2Project_Backend.services;

import com.akash.Oauth2Project_Backend.Entities.User;
import com.akash.Oauth2Project_Backend.enums.AuthProvider;
import com.akash.Oauth2Project_Backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;


    // registering user locally
    public User registerUserLocal(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthProvider(AuthProvider.LOCAL);
        return  userRepository.save(user);

    }

    // loging user locally not with google
    public User loginUserLocal(User user){
        User existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (existingUser != null){
            if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                throw new RuntimeException("User password does  not match");
            }
            return existingUser;
        }
        throw new RuntimeException("User not found");
    }


    // handles the process of logging in or registering a user using Google OAuth2
    public  User loginRegisterByGoogleOAuth2(OAuth2AuthenticationToken auth2AuthenticationToken){


        //uAuth2User contains info about authenticated user,which comes from google
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();   //retrieves the authenticated user's details from the OAuth2AuthenticationToken

        String email = oAuth2User.getAttribute("email"); //getting email from authenticated user
        String name = oAuth2User.getAttribute("name");

        log.info("User Email From GOOGLE  IS {}",email );
        log.info("User Name from GOOGLE IS {}",name );

        User user = userRepository.findByEmail(email).orElse(null);

        // registering a new user if not found
        if (user == null) {

            user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setAuthProvider(AuthProvider.GOOGLE);
            return userRepository.save(user);
        }
        return user;
    }
}


