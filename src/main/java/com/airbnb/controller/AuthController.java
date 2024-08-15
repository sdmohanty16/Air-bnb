package com.airbnb.controller;

import com.airbnb.entity.AppUser;
import com.airbnb.payload.AppUserDto;
import com.airbnb.payload.JWTToken;
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

    //http://localhost:8080/api/v1/auth/createuser
    @PostMapping("/createuser")
    public ResponseEntity<AppUserDto> createUser(
            @RequestBody AppUserDto appUserDto
    ){
        appUserDto.setRole("ROLE_USER");
        AppUserDto userDto = appUserService.createUser(appUserDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/v1/auth/createpropertyowner
    @PostMapping("/createpropertyowner")
    public ResponseEntity<AppUserDto> createPropertyOwner(
            @RequestBody AppUserDto appUserDto
    ){
        appUserDto.setRole("ROLE_OWNER");
        AppUserDto userDto = appUserService.createUser(appUserDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/v1/auth/createpropertymanager
    @PostMapping("/createpropertymanager")
    public ResponseEntity<AppUserDto> createPropertyManager(
            @RequestBody AppUserDto appUserDto
    ){
        appUserDto.setRole("ROLE_MANAGER");
        AppUserDto userDto = appUserService.createUser(appUserDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/v1/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> signIn(
            @RequestBody LoginDto loginDto
    ){
        String token = appUserService.verifyLogin(loginDto);
        JWTToken jwtToken = new JWTToken();
        if(token!=null){
            jwtToken.setTokenType("JWT");
            jwtToken.setToken(token);
            return new ResponseEntity<>(jwtToken, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Invalid username/password", HttpStatus.UNAUTHORIZED);
        }
    }


}
