package com.shubham.razorpay.payment.processor.strategy;

import com.shubham.razorpay.payment.processor.PaymentProcessor;
import com.shubham.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.shubham.razorpay.payment.processor.dto.PaymentProcessorResponse;

public class CardPaymentProcessor implements PaymentProcessor {
    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
        return null;
    }
}
