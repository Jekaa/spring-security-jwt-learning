package ru.gee.learning.controller;


import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gee.learning.entity.AuthRequest;
import ru.gee.learning.entity.User;
import ru.gee.learning.service.UserService;
import ru.gee.learning.util.JwtUtil;

@RestController
@Log4j2
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String authorized() {
        return "Congrats! You are authorized!";
    }

    @PostMapping("/auth")
    public String generateToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (Exception ex) {
            log.catching(Level.ERROR, ex);
        }
        User user = (User) userService.loadUserByUsername(authRequest.getUsername());
        return jwtUtil.generateToken(user);
    }
}
