package com.etoos.bcp.sample.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class AuthSampleService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    Map<String, String> map = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        map.put("incheol", passwordEncoder.encode("pass"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);
        String password = map.get(username);
        System.out.println("===========password===========" + password);


        return User.builder().authorities("ROLE_USER")
                .username("incheol")
                .password("password")
                .build();
    }



}
