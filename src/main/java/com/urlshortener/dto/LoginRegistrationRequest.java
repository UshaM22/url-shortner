package com.urlshortener.dto;

import lombok.Data;

@Data
public class LoginRegistrationRequest {

    private String userName;

    private String password;
}
