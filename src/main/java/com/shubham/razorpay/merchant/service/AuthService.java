package com.shubham.razorpay.merchant.service;

import com.shubham.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.shubham.razorpay.merchant.dto.response.MerchantResponse;

public interface AuthService {
     MerchantResponse signup(MerchantSignupRequest request);
}
