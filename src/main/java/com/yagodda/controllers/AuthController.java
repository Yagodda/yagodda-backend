package com.yagodda.controllers;


import com.yagodda.models.User;
import com.yagodda.payload.request.SingInRequest;
import com.yagodda.payload.request.SingUpRequest;
import com.yagodda.payload.response.JWTTokenSuccessResponse;
import com.yagodda.payload.response.MessageResponse;
import com.yagodda.security.JWTTokenProvider;
import com.yagodda.security.SecurityConstants;
import com.yagodda.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@PreAuthorize("permitAll()")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody SingInRequest singInRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        singInRequest.getPhoneNumber(),
                        singInRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateWebToken(authentication);
        return ResponseEntity.ok(new JWTTokenSuccessResponse(jwt, true));
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> registration(@RequestBody SingUpRequest singUpRequest) {
        userService.createUser(singUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }
}



