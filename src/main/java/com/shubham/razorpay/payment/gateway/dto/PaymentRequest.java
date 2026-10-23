package com.shubham.razorpay.payment.gateway.dto;

import com.shubham.razorpay.common.entity.Money;
import com.shubham.razorpay.common.enums.PaymentMethod;

import java.util.Map;
import java.util.UUID;

public record PaymentRequest(
        UUID paymentId,
        UUID orderId,
        UUID merchantId,
        Money amount,
        PaymentMethod method,
        Map<String,Object> methodDetails
) {
}
