package com.spring.springecommerceapi.controller;

import com.spring.springecommerceapi.dto.request.ApiRequest;
import com.spring.springecommerceapi.dto.request.AuthRequest;
import com.spring.springecommerceapi.exception.ErrorCode;
import com.spring.springecommerceapi.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController extends BaseController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        super();
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiRequest<Boolean>> login(@RequestBody AuthRequest authRequest) {
        boolean auth =  authService.authenticate(authRequest);
        if (auth){
            return createApiResponse(ErrorCode.SUCCESS,true);
        }else{
            return createApiResponse(ErrorCode.FAIL,false);
        }
    }
}
