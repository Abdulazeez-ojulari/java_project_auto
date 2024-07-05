package org.afripay.afripay.jwt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@CrossOrigin()
public class HelloWorldController {

    @GetMapping({"/hello"})
    public String hello(Principal principal) {
        String email = principal.getName();
        log.info("Logged in user => {}", email);
        return email;
    }

}