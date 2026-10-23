package com.shubham.razorpay.payment.processor;

import com.shubham.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.shubham.razorpay.payment.processor.dto.PaymentProcessorResponse;

public interface PaymentProcessor {

    PaymentProcessorResponse charge(PaymentProcessorRequest request);
}
