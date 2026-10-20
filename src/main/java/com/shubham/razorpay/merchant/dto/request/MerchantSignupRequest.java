package com.shubham.razorpay.merchant.dto.request;

import com.shubham.razorpay.common.enums.BusinessType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MerchantSignupRequest(
        @NotNull(message = "Name should be provided")
        @Size(max = 50, message = "Name should not exceed 50 characters")
        String name,
        @Email
        @NotNull(message = "Email should be provided")
        String email,
        @NotNull(message = "Password should be provided")
        @Size(min=8, message = "Password should be at least 8 characters long")
        String password,
        @Size(max = 50, message = "Business name should not exceed 50 characters")
        String businessName,
        BusinessType businessType
) {
}
