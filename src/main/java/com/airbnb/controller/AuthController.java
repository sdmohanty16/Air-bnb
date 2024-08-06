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

    //http://localhost:8080/api/v1/auth/addUser
    @PostMapping("/addUser")
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
        String token = appUserService.verifyLogin(loginDto);
        if(token!=null){
            return new ResponseEntity<>(token ,HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Invalid credentials" , HttpStatus.UNAUTHORIZED);
        }
    }


}
