package com.shubham.razorpay.payment.config;

import com.shubham.razorpay.common.enums.PaymentMethod;
import com.shubham.razorpay.payment.processor.PaymentProcessor;
import com.shubham.razorpay.payment.processor.strategy.CardPaymentProcessor;
import com.shubham.razorpay.payment.processor.strategy.NetBankingPaymentProcessor;
import com.shubham.razorpay.payment.processor.strategy.UpiPaymentProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class PaymentProcessorConfig {

    @Bean
    public Map<PaymentMethod, PaymentProcessor> paymentProcessorMap(){
        return Map.of(
                PaymentMethod.CARD, new CardPaymentProcessor(),
                PaymentMethod.NETBANKING, new NetBankingPaymentProcessor(),
                PaymentMethod.UPI, new UpiPaymentProcessor()
        );
    }
}
