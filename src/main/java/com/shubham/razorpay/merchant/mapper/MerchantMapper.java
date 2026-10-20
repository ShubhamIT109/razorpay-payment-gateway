package com.shubham.razorpay.merchant.mapper;


import com.shubham.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.shubham.razorpay.merchant.dto.response.MerchantResponse;
import com.shubham.razorpay.merchant.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MerchantMapper {
  Merchant toEntityFromSignUpRequest(MerchantSignupRequest request);
  MerchantResponse toMerchantResponse(Merchant merchant);
}
