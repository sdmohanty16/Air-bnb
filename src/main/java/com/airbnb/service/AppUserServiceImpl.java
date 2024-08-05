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

    private AppUserRepository appUserRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository) {
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
    public boolean verifyLogin(LoginDto loginDto) {
        Optional<AppUser> opUsername = appUserRepository.findByUsername(loginDto.getUsername());
        if(opUsername.isPresent()){
            AppUser appUser = opUsername.get();
            return BCrypt.checkpw(loginDto.getPassword(), appUser.getPassword());
        }
        return false;
    }

    AppUser mapToEntity(AppUserDto dto){
        AppUser entity = new AppUser();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        return entity;
    }

    AppUserDto mapToDto(AppUser user){
        AppUserDto dto = new AppUserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        return dto;
    }
}
