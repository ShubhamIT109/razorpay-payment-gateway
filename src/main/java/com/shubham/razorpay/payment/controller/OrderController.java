package com.shubham.razorpay.payment.controller;

import com.shubham.razorpay.payment.dto.request.CreateOrderRequest;
import com.shubham.razorpay.payment.dto.response.OrderResponse;
import com.shubham.razorpay.payment.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    UUID merchantId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");//TODO: replace it with MerchantContext
    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.create(merchantId,request));
    }
}
