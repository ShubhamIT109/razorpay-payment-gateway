package com.shubham.razorpay.merchant.dto.response;

import com.shubham.razorpay.common.enums.Environment;

import java.util.UUID;

public record CreateApiKeyResponse(
        UUID ID,
        String keyId,
        String keySecret,
        Environment environment
) {
}
