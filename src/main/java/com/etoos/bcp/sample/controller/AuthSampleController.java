package com.etoos.bcp.sample.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@Slf4j
public class AuthSampleController {

    @Qualifier("authSampleService")
    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping("/user")
    public UserDetails getUser(@RequestParam String userName, Principal principal) {
        log.info("{}", userName);
        log.info("{}", SecurityContextHolder.getContext().getAuthentication().getDetails());
        return userDetailsService.loadUserByUsername(userName);
    }

    @PostMapping("/user")
    public UserDetails getUserPost(@RequestBody UserDto userDto) {
        log.info("{}", userDto);
        return userDetailsService.loadUserByUsername(userDto.userName);
    }


    @PostMapping("/login")
    public void login(HttpServletResponse servletResponse) {
        System.out.println(servletResponse);
    }


    @Data
    public static class UserDto {
        String userName;

    }

}
