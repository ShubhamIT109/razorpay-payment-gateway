package com.shubham.razorpay.payment.processor;

import com.shubham.razorpay.common.enums.PaymentMethod;
import com.shubham.razorpay.payment.config.PaymentProcessorConfig;
import com.shubham.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.shubham.razorpay.payment.processor.dto.PaymentProcessorResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaymentProcessorRouter {
    private Map<PaymentMethod, PaymentProcessor> paymentProcessor;
    public PaymentProcessorResponse charge(PaymentProcessorRequest request){
        PaymentProcessor processor = paymentProcessor.get(request.method());
        if(processor == null ){
            throw new IllegalArgumentException("No payment processor registered for method: "+request.method());
        }
        return processor.charge(request);
    }
}
