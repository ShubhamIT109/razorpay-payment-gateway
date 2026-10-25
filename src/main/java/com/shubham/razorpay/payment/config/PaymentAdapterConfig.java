package com.shubham.razorpay.payment.config;

import com.shubham.razorpay.common.enums.PaymentMethod;
import com.shubham.razorpay.payment.gateway.PaymentAdapter;
import com.shubham.razorpay.payment.gateway.adapter.CardPaymentAdapter;
import com.shubham.razorpay.payment.gateway.adapter.NetBankingAdapter;
import com.shubham.razorpay.payment.gateway.adapter.UpiPaymentAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class PaymentAdapterConfig {

    private final NetBankingAdapter netBankingAdapter;
//    private final CardPaymentAdapter cardPaymentAdapter; // TODO: While adding card vault will be added
    private final UpiPaymentAdapter upiPaymentAdapter;

    @Bean
    public Map<PaymentMethod, PaymentAdapter> paymentAdapterMap(){
        return Map.of(
//                PaymentMethod.CARD,cardPaymentAdapter,
                PaymentMethod.NETBANKING,netBankingAdapter,
                PaymentMethod.UPI,upiPaymentAdapter
        );
    }
}
