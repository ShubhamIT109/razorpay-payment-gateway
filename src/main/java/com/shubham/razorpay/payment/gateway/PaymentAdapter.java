package com.shubham.razorpay.payment.gateway;

import com.shubham.razorpay.payment.gateway.dto.PaymentRequest;
import com.shubham.razorpay.payment.gateway.dto.PaymentResult;

public interface PaymentAdapter {
    PaymentResult initiate(PaymentRequest request);
}
