package com.shubham.razorpay.payment.dto.response;

import com.shubham.razorpay.common.entity.Money;
import com.shubham.razorpay.common.enums.OrderStatus;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID merchantId,
        String receipt,
        Money amount,
        OrderStatus status,
        Integer attempts,
        Map<String,Object> notes,
        LocalDateTime expiresAt,
        LocalDateTime createdAt
) {
}
