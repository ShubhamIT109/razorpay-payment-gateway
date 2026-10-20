package com.shubham.razorpay.merchant.dto.response;

import com.shubham.razorpay.common.enums.MerchantStatus;
import lombok.Data;

import java.util.UUID;
public record MerchantResponse(
        UUID id,
        String name,
        String email,
        String businessName,
        String businessType,
        MerchantStatus merchantStatus
) {
}
