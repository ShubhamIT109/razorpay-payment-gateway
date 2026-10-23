package com.shubham.razorpay.payment.processor.dto;

import com.shubham.razorpay.common.entity.Money;
import com.shubham.razorpay.common.enums.PaymentMethod;

import java.util.Map;

public record PaymentProcessorRequest(
        PaymentMethod method,
        Money amount,
        Map<String,Object> methodDetails

) {
}
