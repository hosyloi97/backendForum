package com.bksoftware.entities.json_payload;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterForm {

    private String username;

    private String password;

    private String name;

    private int gender;

    private LocalDate birthday;

    private int phoneNumber;

    private String address;

    private String job;

}
