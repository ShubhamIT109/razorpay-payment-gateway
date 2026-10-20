package com.shubham.razorpay.merchant.dto.request;

import com.shubham.razorpay.common.enums.Environment;

public record CreateApiKeyRequest(
        Environment environment
) {
}
