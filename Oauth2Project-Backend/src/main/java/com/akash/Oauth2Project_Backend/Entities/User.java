package com.akash.Oauth2Project_Backend.Entities;


import com.akash.Oauth2Project_Backend.enums.AuthProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    // we will have the provider since we are either be logging in or registering us
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;
}
