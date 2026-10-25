package com.shubham.razorpay.payment.controller;

import com.shubham.razorpay.payment.dto.request.PaymentInitRequest;
import com.shubham.razorpay.payment.dto.response.PaymentResponse;
import com.shubham.razorpay.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("v1/payments")
@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    UUID merchantId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");//ToDo: replace it with MerchantContext

    @PostMapping
    public ResponseEntity<PaymentResponse> initiate(@Valid @RequestBody PaymentInitRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.intiate(merchantId,request));
    }

    @PostMapping("/{paymentId}/capture")
    public ResponseEntity<PaymentResponse> capture(@PathVariable UUID paymentId){
        return ResponseEntity.ok(paymentService.capture(merchantId,paymentId));
    }
}
