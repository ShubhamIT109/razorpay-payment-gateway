package com.shubham.razorpay.payment.gateway.adapter;

import com.shubham.razorpay.payment.gateway.PaymentAdapter;
import com.shubham.razorpay.payment.gateway.dto.PaymentRequest;
import com.shubham.razorpay.payment.gateway.dto.PaymentResult;

public class CardPaymentAdapter implements PaymentAdapter {
    @Override
    public PaymentResult initiate(PaymentRequest request){
       return null;
    }
}
