package com.shubham.razorpay.merchant.mapper;

import com.shubham.razorpay.merchant.dto.response.ApiKeyResponse;
import com.shubham.razorpay.merchant.dto.response.CreateApiKeyResponse;
import com.shubham.razorpay.merchant.entity.ApiKey;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiKeyMapper {
    CreateApiKeyResponse toCreateResponse(ApiKey apiKey);

    List<ApiKeyResponse> toResponseList(List<ApiKey> apiKeyList);
}
