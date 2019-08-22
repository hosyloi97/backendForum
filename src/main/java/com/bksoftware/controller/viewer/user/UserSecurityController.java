package com.bksoftware.controller.viewer.user;

import com.bksoftware.entities.Record;
import com.bksoftware.entities.json_payload.LoginForm;
import com.bksoftware.entities.json_payload.RegisterForm;
import com.bksoftware.entities.user.AppRole;
import com.bksoftware.entities.user.AppUser;
import com.bksoftware.service.user.AppRoleService;
import com.bksoftware.service_impl.RecordService_Impl;
import com.bksoftware.service_impl.user.AppUserService_Impl;
import com.bksoftware.service_impl.JWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/public")
public class UserSecurityController {

    private final AppUserService_Impl appUserService;
    private final JWTService jwtService;
    private final RecordService_Impl recordService;


    public UserSecurityController(AppUserService_Impl appUserService, JWTService jwtService, RecordService_Impl recordService) {
        this.appUserService = appUserService;
        this.jwtService = jwtService;
        this.recordService = recordService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginForm loginForm,
                                        HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        AppUser appUser = appUserService.findByEmailAndPassword(loginForm.getUsername(), loginForm.getPassword());
        if (appUser == null) return new ResponseEntity<>("username or password is not correct", HttpStatus.OK);
        else {
            String token = jwtService.generateToken(appUser.getEmail());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterForm registerForm,
                                           HttpServletResponse response
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");

        if (appUserService.findByEmail(registerForm.getUsername()) != null) {

            return new ResponseEntity<>("Email has been used", HttpStatus.BAD_REQUEST);
        }

        if (appUserService.saveAppUser(registerForm)) {
            Record record = recordService.findByName("user");
            record.setSize(record.getSize() + 1);
            AppUser appUser = appUserService.findByEmail(registerForm.getUsername());
            recordService.saveRecord(record);
            return new ResponseEntity<>(appUser, HttpStatus.OK);
        }
        return new ResponseEntity<>("register fail", HttpStatus.BAD_REQUEST);
    }
}
