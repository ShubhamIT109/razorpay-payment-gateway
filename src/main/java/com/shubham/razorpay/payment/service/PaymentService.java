package com.shubham.razorpay.payment.service;

import com.shubham.razorpay.payment.dto.request.PaymentInitRequest;
import com.shubham.razorpay.payment.dto.response.PaymentResponse;

import java.util.UUID;

public interface PaymentService {
    PaymentResponse intiate(UUID merchantId, PaymentInitRequest request);

    PaymentResponse capture(UUID merchantId, UUID paymentId);
}
