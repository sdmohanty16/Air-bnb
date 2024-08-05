package com.airbnb.controller;

import com.airbnb.entity.AppUser;
import com.airbnb.payload.AppUserDto;
import com.airbnb.payload.LoginDto;
import com.airbnb.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    //http://localhost:8080/api/v1/auth
    @PostMapping
    public ResponseEntity<AppUserDto> createUser(
            @RequestBody AppUserDto appUserDto
    ){
        AppUserDto userDto = appUserService.createUser(appUserDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/v1/auth/login
    @PostMapping("/login")
    public ResponseEntity<String> verifyLogin(
            @RequestBody LoginDto loginDto
            ){
        boolean status = appUserService.verifyLogin(loginDto);
        if(status){
            return new ResponseEntity<>("Successful..." ,HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Invalid credentials" , HttpStatus.UNAUTHORIZED);
        }
    }


}
