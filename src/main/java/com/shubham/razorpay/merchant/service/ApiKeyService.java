package com.shubham.razorpay.merchant.service;


import com.shubham.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.shubham.razorpay.merchant.dto.response.ApiKeyResponse;
import com.shubham.razorpay.merchant.dto.response.CreateApiKeyResponse;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.UUID;

public interface ApiKeyService {
    CreateApiKeyResponse create(UUID merchantId, CreateApiKeyRequest request);

    List<ApiKeyResponse> listByMerchant(UUID merchantId);

    void revoke(UUID merchantId, UUID keyId);

    @Nullable CreateApiKeyResponse rotateKey(UUID merchantId, UUID keyId);
}
