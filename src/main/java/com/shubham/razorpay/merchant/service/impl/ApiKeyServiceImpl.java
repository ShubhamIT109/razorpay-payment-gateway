package com.shubham.razorpay.merchant.service.impl;

import com.shubham.razorpay.common.exception.ResourceNotFoundException;
import com.shubham.razorpay.common.util.RandomizerUtil;
import com.shubham.razorpay.merchant.dto.request.CreateApiKeyRequest;
import com.shubham.razorpay.merchant.dto.response.ApiKeyResponse;
import com.shubham.razorpay.merchant.dto.response.CreateApiKeyResponse;
import com.shubham.razorpay.merchant.entity.ApiKey;
import com.shubham.razorpay.merchant.entity.Merchant;
import com.shubham.razorpay.merchant.mapper.ApiKeyMapper;
import com.shubham.razorpay.merchant.repository.ApiKeyRepository;
import com.shubham.razorpay.merchant.repository.MerchantRepository;
import com.shubham.razorpay.merchant.service.ApiKeyService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiKeyServiceImpl implements ApiKeyService {

    private final MerchantRepository merchantRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyMapper apiKeyMapper;
    @Override
    @Transactional
    public CreateApiKeyResponse create(UUID merchantId, CreateApiKeyRequest request) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(()->new ResourceNotFoundException("merchant",merchantId));
        String keyId = "rzp_"+request.environment().name().toLowerCase()+"_"+RandomizerUtil.randomBase64(24);
        String rawSecret = RandomizerUtil.randomBase64(40);

        ApiKey apiKey = ApiKey.builder()
                .merchant(merchant)
                .keyId(keyId)
                .keySecretHash(rawSecret) //TODO:encode with BcryptPasswordEncoder
                .environment(request.environment())
                .build();

        apiKey = apiKeyRepository.save(apiKey);

        return new CreateApiKeyResponse(apiKey.getId(),keyId,rawSecret,request.environment());
    }

    @Override
    public List<ApiKeyResponse> listByMerchant(UUID merchantId) {

             return apiKeyMapper.toResponseList(apiKeyRepository.findByMerchant_Id(merchantId));


//        .stream().map(apiKey ->
//                        new ApiKeyResponse(apiKey.getId(),
//                                apiKey.getKeyId(),
//                                apiKey.getEnvironment(),
//                                apiKey.isEnabled(),
//                                apiKey.getLastUsedAt(), null))
//                .toList();
    }

    @Override
    @Transactional
    public void revoke(UUID merchantId, UUID keyId) {
        ApiKey key = apiKeyRepository.findById(keyId)
                .filter(k->k.getMerchant().getId().equals(merchantId))
                .orElseThrow(()->new ResourceNotFoundException("ApiKey",keyId));

        key.setEnabled(false);
        apiKeyRepository.save(key);
    }


    @Override
    @Transactional
    public @Nullable CreateApiKeyResponse rotateKey(UUID merchantId, UUID keyId) {
        ApiKey apiKey = apiKeyRepository.findById(keyId)
                .filter(k->k.getMerchant().getId().equals(merchantId))
                .orElseThrow(()->new ResourceNotFoundException("ApiKey",keyId));

        if(!apiKey.isEnabled()) throw new RuntimeException("Cannot rotate a disabled key");

        String newRawSecret =  RandomizerUtil.randomBase64(40);
        apiKey.setPreviousKeySecretHash(apiKey.getKeySecretHash());
        apiKey.setKeySecretHash(newRawSecret); //TODO: encode with BcryptPasswordEncoder
        apiKey.setRotatedAt(LocalDateTime.now());
        apiKey.setGracePeriodExpiredAt(LocalDateTime.now().plusHours(24));
        apiKey = apiKeyRepository.save(apiKey);

        return new CreateApiKeyResponse(apiKey.getId(),apiKey.getKeyId(),newRawSecret,apiKey.getEnvironment());

    }
}
