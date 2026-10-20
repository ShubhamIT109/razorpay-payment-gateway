package com.shubham.razorpay.payment.service.impl;

import com.shubham.razorpay.common.enums.OrderStatus;
import com.shubham.razorpay.common.exception.BussinessRuleViolationException;
import com.shubham.razorpay.common.exception.DuplicateResourceException;
import com.shubham.razorpay.common.exception.ResourceNotFoundException;
import com.shubham.razorpay.payment.dto.request.CreateOrderRequest;
import com.shubham.razorpay.payment.dto.response.OrderResponse;
import com.shubham.razorpay.payment.dto.response.PaymentResponse;
import com.shubham.razorpay.payment.entity.OrderRecord;
import com.shubham.razorpay.payment.entity.Payment;
import com.shubham.razorpay.payment.mapper.OrderMapper;
import com.shubham.razorpay.payment.mapper.PaymentMapper;
import com.shubham.razorpay.payment.repository.OrderRepository;
import com.shubham.razorpay.payment.repository.PaymentRepository;
import com.shubham.razorpay.payment.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderMapper orderMapper;

    @Value("${payment.order.default-order-expiry-minutes:30}")
    private int defaultOrderExpiryMinutes;
    @Override
    @Transactional
    public OrderResponse create(UUID merchantId, CreateOrderRequest request) {
        if(request.receipt()!= null && orderRepository.existsByMerchantIdAndReceipt(merchantId,request.receipt())){
            throw new DuplicateResourceException("ORDER_RECEIPT_DUPLICATE","Order with receipt already exist: "+request.receipt());
        }
        OrderRecord order = OrderRecord.builder()
                .receipt(request.receipt())
                .amount(request.amount())
                .notes(request.notes())
                .merchantId(merchantId)
                .orderStatus(OrderStatus.CREATED)
                .expiredAt(request.expiresAt() != null ? request.expiresAt():
                        LocalDateTime.now().plusMinutes(defaultOrderExpiryMinutes))
                .build();

        order = orderRepository.save(order);

        //TODO: publish kafka event about order creation
//        return new OrderResponse(order.getId(),
//                order.getMerchantId(),
//                order.getReceipt(),
//                order.getAmount(),
//                order.getOrderStatus(),
//                order.getAttempts(),order.getNotes(),
//                order.getExpiredAt(),null); //TODO: we can use map struct here

        return orderMapper.toResponse(order); // map struct implementation

    }

    @Override
    public OrderResponse getById(UUID merchantId, UUID orderId) {
        OrderRecord orderRecord = orderRepository.findByIdAndMerchantId(orderId,merchantId)
                .orElseThrow(()->new ResourceNotFoundException("Order",orderId));
//        return new OrderResponse(orderRecord.getId(),orderRecord.getMerchantId(),
//                orderRecord.getReceipt(),orderRecord.getAmount(),orderRecord.getOrderStatus(),
//                orderRecord.getAttempts(),orderRecord.getNotes(), orderRecord.getExpiredAt(),
//                null);
        return  orderMapper.toResponse(orderRecord);
    }

    @Override
    @Transactional
    public OrderResponse cancel(UUID merchantId, UUID orderId) {
        OrderRecord orderRecord = orderRepository.findByIdAndMerchantId(orderId,merchantId)
                .orElseThrow(()->new ResourceNotFoundException("Order",orderId));

        if(orderRecord.getOrderStatus() == OrderStatus.CANCELLED||orderRecord.getOrderStatus() == OrderStatus.PAID){
            throw new BussinessRuleViolationException("ORDER_CANNOT_CANCEL","Cannot cancel order with status: "+ orderRecord.getOrderStatus().name());
        }
        orderRecord.setOrderStatus(OrderStatus.CANCELLED);
        orderRecord = orderRepository.save(orderRecord);

        return  orderMapper.toResponse(orderRecord);
    }

    @Override
    public List<PaymentResponse> listPayments(UUID merchantId, UUID orderId) {
        OrderRecord orderRecord = orderRepository.findByIdAndMerchantId(orderId,merchantId)
                .orElseThrow(()->new ResourceNotFoundException("Order",orderId));

        List<Payment> paymentList = paymentRepository.findByOrder_Id(orderId);

//        return paymentList.stream().map(
//                 payment -> paymentMapper.toResponse(payment)
//                 ).collect(Collectors.toList());

        return paymentMapper.toResponseList(paymentList);
    }
}
