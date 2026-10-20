package com.shubham.razorpay.merchant.repository;

import com.shubham.razorpay.merchant.dto.response.ApiKeyResponse;
import com.shubham.razorpay.merchant.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApiKeyRepository extends JpaRepository<ApiKey,UUID> {

    List<ApiKey> findByMerchant_Id(UUID merchantId);
}
