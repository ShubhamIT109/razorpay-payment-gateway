package com.shubham.razorpay.merchant.service.impl;

import com.shubham.razorpay.common.enums.MerchantStatus;
import com.shubham.razorpay.common.enums.UserRole;
import com.shubham.razorpay.common.exception.DuplicateResourceException;
import com.shubham.razorpay.merchant.dto.request.MerchantSignupRequest;
import com.shubham.razorpay.merchant.dto.response.MerchantResponse;
import com.shubham.razorpay.merchant.entity.AppUser;
import com.shubham.razorpay.merchant.entity.Merchant;
import com.shubham.razorpay.merchant.mapper.MerchantMapper;
import com.shubham.razorpay.merchant.repository.AppUserRepository;
import com.shubham.razorpay.merchant.repository.MerchantRepository;
import com.shubham.razorpay.merchant.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService  {
    private final MerchantRepository merchantRepository;
    private final AppUserRepository appUserRepository;
    private final MerchantMapper merchantMapper;

    @Override
    @Transactional
    public MerchantResponse signup(MerchantSignupRequest request) {
        if(merchantRepository.existsByEmail(request.email())){
            throw new DuplicateResourceException("DUPLICATE_MERCHANT_EMAIL","Merchant with email already exists"+request.email());
        }
        Merchant merchant = merchantMapper.toEntityFromSignUpRequest(request);
        merchant.setStatus(MerchantStatus.PENDING_KYC);
//                Merchant.builder()
//                .businessName(request.businessName())
//                .businessType(request.businessType())
//                .name(request.name())
//                .email(request.email())
//                .status(MerchantStatus.PENDING_KYC)
//                .build();

        merchant = merchantRepository.save(merchant);

        AppUser appUser = AppUser.builder()
                .email(request.email())
                .merchant(merchant)
                .passwordHash(request.password())// TODO: hash the password before saving
                .role(UserRole.OWNER)
                .build();
        appUserRepository.save(appUser);

        return merchantMapper.toMerchantResponse(merchant);
//        return new MerchantResponse(merchant.getId(), merchant.getName(),
//                merchant.getEmail(), merchant.getBusinessName(),
//                merchant.getBusinessType().name(), merchant.getStatus());
    }
}
