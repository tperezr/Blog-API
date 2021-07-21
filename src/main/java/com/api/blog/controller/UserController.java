package com.api.blog.controller;

import com.api.blog.dto.UserDto;
import com.api.blog.service.UserService;
import com.api.blog.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userLogin){
        try {
            authenticate(userLogin.getEmail(),userLogin.getPassword());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        UserDetails userDetails = userService.loadUserByUsername(userLogin.getEmail());

        String token = jwtTokenUtil.getToken(userDetails);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/sign_up")
    public ResponseEntity<String> singUp(@RequestBody UserDto userSignUp){
        if(userService.signUpUser(userSignUp)) {
            return ResponseEntity.ok("Registro exitoso");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario ya existente");
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            throw new Exception("Credenciales invalidas", e);
        }
    }
}
