package com.shubham.razorpay.payment.mapper;

import com.shubham.razorpay.payment.dto.response.OrderResponse;
import com.shubham.razorpay.payment.entity.OrderRecord;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    OrderResponse toResponse(OrderRecord orderRecord);
}
