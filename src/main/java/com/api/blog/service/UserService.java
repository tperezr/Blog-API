package com.api.blog.service;

import com.api.blog.dto.UserDto;
import com.api.blog.model.RequestRegister;
import com.api.blog.model.User;
import com.api.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(s);

        if(user.isEmpty()){
            throw new UsernameNotFoundException(s);
        }
        org.springframework.security.core.userdetails.User userDetails =
                new org.springframework.security.core.userdetails.User(
                        user.get().getEmail(),
                        user.get().getPassword(),
                        new ArrayList<GrantedAuthority>()
                );
        return userDetails;
    }

    @Transactional
    public Boolean signUpUser(UserDto userRegister){
        boolean existsUser = userRepository.findByEmail(userRegister.getEmail()).isPresent();
        User user;
        if(!existsUser){
            user = new User(
                    null,
                    userRegister.getEmail(),
                    passwordEncoder.encode(userRegister.getPassword())
            );
            userRepository.save(user);
        } else{
            return false;
        }

        return true;
    }
}
