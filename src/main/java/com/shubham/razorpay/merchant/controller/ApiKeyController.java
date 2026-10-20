package com.shubham.razorpay.merchant.controller;

import com.shubham.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.shubham.razorpay.merchant.dto.response.ApiKeyResponse;
import com.shubham.razorpay.merchant.dto.response.CreateApiKeyResponse;
import com.shubham.razorpay.merchant.service.ApiKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/merchants/{merchantsId}/api-keys")
@RequiredArgsConstructor
public class ApiKeyController {
    private final ApiKeyService apiKeyService;

    @PostMapping
    public ResponseEntity<CreateApiKeyResponse> create(@PathVariable UUID merchantId,
                                                       @Valid @RequestBody CreateApiKeyRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(apiKeyService.create(merchantId,request));
    }

    @GetMapping
    public ResponseEntity<List<ApiKeyResponse>> listByMerchant(@PathVariable UUID merchantId){
        return ResponseEntity.ok(apiKeyService.listByMerchant(merchantId));
    }

    @DeleteMapping("/keyId")
    public ResponseEntity<Void> revoke(@PathVariable UUID merchantId, @PathVariable UUID keyId){
        apiKeyService.revoke(merchantId,keyId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{keyId}/rotate")
    public ResponseEntity<CreateApiKeyResponse> rotateKey(@PathVariable UUID merchantId,@PathVariable UUID keyId){
        return ResponseEntity.ok(apiKeyService.rotateKey(merchantId,keyId));
    }
}
