package com.shubham.razorpay.merchant.controller;

import com.shubham.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.shubham.razorpay.merchant.dto.response.MerchantResponse;
import com.shubham.razorpay.merchant.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<MerchantResponse> signup(@RequestBody @Valid MerchantSignupRequest request){
    return ResponseEntity.status(HttpStatus.CREATED).body(
            authService.signup(request)
    );
    }
}
