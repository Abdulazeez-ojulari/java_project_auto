package org.afripay.afripay.jwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.afripay.afripay.exceptions.GeneralException;
import org.afripay.afripay.general.dto.Response;
import org.afripay.afripay.general.dto.ResponseConstants;
import org.afripay.afripay.general.service.GeneralService;
import org.afripay.afripay.jwt.config.JwtTokenUtil;
import org.afripay.afripay.jwt.service.JwtUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/logout")
@Tag(name = "Logout", description = "Apis for managing logout")
public class LogoutController {

    private final JwtTokenUtil jwtTokenUtil;
    private final GeneralService generalService;
    private final JwtUserDetailsService jwtUserDetailsService;

    @PostMapping
    @Operation(summary = "Logout admin user", description = "Terminate and invalidate admin user authorization token")
    public Response logout(HttpServletRequest request) {
        String token = jwtTokenUtil.resolveToken(request);

        if (token.isBlank())
            throw new GeneralException("Only logged in users can perform this action");

        jwtUserDetailsService.addToBlacklist(token);

        return generalService.prepareSuccessResponse(ResponseConstants.ResponseCode.SUCCESS, ResponseConstants.ResponseMessage.SUCCESS, "logged out successfully");
    }


}