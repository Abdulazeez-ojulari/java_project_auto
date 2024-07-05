package org.afripay.afripay.users.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.afripay.afripay.general.dto.Response;
import org.afripay.afripay.general.service.GeneralService;
import org.afripay.afripay.users.service.UsersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "api/profile")
@RequiredArgsConstructor
public class UsersController {
    
    private final UsersService usersService;
    private final GeneralService generalService;
    
    @GetMapping(value = "single-user")
    public Response getUserProfile(@Parameter(hidden = true) Principal principal){
        
        var response = usersService.getUserProfile(principal.getName());
        
        return generalService.prepareSuccessResponse(response);
    }
}