package com.urlshortener.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_details")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

}
