package com.airbnb.service;

import com.airbnb.entity.AppUser;
import com.airbnb.payload.AppUserDto;
import com.airbnb.payload.LoginDto;
import com.airbnb.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService{

    private JWTService jwtService;

    private AppUserRepository appUserRepository;

    public AppUserServiceImpl(JWTService jwtService, AppUserRepository appUserRepository) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }


    @Override
    public AppUserDto createUser(AppUserDto appUserDto) {
        AppUser user = mapToEntity(appUserDto);

        Optional<AppUser> opEmail = appUserRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()){
            throw new RuntimeException("Email id Exists...");
        }

        Optional<AppUser> opUsername = appUserRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            throw new RuntimeException("User name Exists...");
        }

        String hashpw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hashpw);

        AppUser savedData = appUserRepository.save(user);
        AppUserDto dto = mapToDto(savedData);
        return dto;
    }

    @Override
    public String verifyLogin(LoginDto loginDto) {
        Optional<AppUser> opUsername = appUserRepository.findByUsername(loginDto.getUsername());
        if(opUsername.isPresent()){
            AppUser user = opUsername.get();
            if(BCrypt.checkpw(loginDto.getPassword(), user.getPassword())){
                return jwtService.generateToken(user);
            }
        }
        return null;
    }

    AppUser mapToEntity(AppUserDto dto){
        AppUser entity = new AppUser();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        return entity;
    }

    AppUserDto mapToDto(AppUser user){
        AppUserDto dto = new AppUserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        return dto;
    }
}
