package com.airbnb.service;

import com.airbnb.entity.AppUser;
import com.airbnb.payload.AppUserDto;
import com.airbnb.payload.LoginDto;

public interface AppUserService {

    AppUserDto createUser(AppUserDto appUserDto);


    boolean verifyLogin(LoginDto loginDto);
}
