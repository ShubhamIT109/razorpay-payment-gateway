package com.shubham.razorpay.payment.service.impl;

import com.shubham.razorpay.common.enums.OrderStatus;
import com.shubham.razorpay.common.enums.PaymentEvent;
import com.shubham.razorpay.common.enums.PaymentStatus;
import com.shubham.razorpay.common.exception.BussinessRuleViolationException;
import com.shubham.razorpay.common.exception.ResourceNotFoundException;
import com.shubham.razorpay.payment.dto.request.PaymentInitRequest;
import com.shubham.razorpay.payment.dto.response.PaymentResponse;
import com.shubham.razorpay.payment.entity.OrderRecord;
import com.shubham.razorpay.payment.entity.Payment;
import com.shubham.razorpay.payment.gateway.PaymentGatewayRouter;
import com.shubham.razorpay.payment.gateway.dto.PaymentRequest;
import com.shubham.razorpay.payment.gateway.dto.PaymentResult;
import com.shubham.razorpay.payment.mapper.PaymentMapper;
import com.shubham.razorpay.payment.repository.OrderRepository;
import com.shubham.razorpay.payment.repository.PaymentRepository;
import com.shubham.razorpay.payment.service.PaymentService;
import com.shubham.razorpay.payment.statemachine.PaymentTransitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
   private final OrderRepository orderRepository;
   private final PaymentRepository paymentRepository;
   private final PaymentGatewayRouter paymentGatewayRouter;
   private final PaymentMapper paymentMapper;
   private final PaymentTransitionService paymentTransitionService;

    @Override
    @Transactional
    public PaymentResponse intiate(UUID merchantId, PaymentInitRequest request) {
        OrderRecord order = orderRepository.findByIdAndMerchantId(request.orderId(),merchantId)
                .orElseThrow(()->new ResourceNotFoundException("Order", request.orderId()));

        if(order.getOrderStatus() != OrderStatus.CREATED && order.getOrderStatus() != OrderStatus.ATTEMPTED){
            throw new BussinessRuleViolationException("ORDER_NOT_PAYABLE",
                    "Order cannot accept payment in status: "+order.getOrderStatus());
        }

        order.setOrderStatus(OrderStatus.ATTEMPTED);
        order.setAttempts(order.getAttempts()+1);

        Payment payment = Payment.builder()
                .order(order)
                .merchantId(merchantId)
                .amount(order.getAmount())
                .status(PaymentStatus.CREATED)
                .method(request.method())
                .methodDetails(request.methodDetails())
                .build();

        payment = paymentRepository.save(payment);

        PaymentRequest paymentRequest = new PaymentRequest(payment.getId(),
                request.orderId(),merchantId,
                order.getAmount(),request.method(),
                request.methodDetails());

        PaymentResult paymentResult = paymentGatewayRouter.initiate(paymentRequest);

        switch (paymentResult) {
            case PaymentResult.Pending pending -> payment.setProcessorReference(pending.registrationRef());
            case PaymentResult.Failure failure -> {
             //   payment.setStatus(PaymentStatus.FAILED);
                paymentTransitionService.apply(payment, PaymentEvent.AUTHORIZE_FAIL);
                payment.setErrorCode(failure.errorCode());
                payment.setErrorDescription(failure.errorDescription());
            }
            case PaymentResult.Success success -> {

            }
        }

        payment = paymentRepository.save(payment);
        orderRepository.save(order);

        //TODO: send an outbox(kafka_event)

        return paymentMapper.toResponse(payment);


    }

    @Override
    public PaymentResponse capture(UUID merchantId, UUID paymentId) {
        Payment payment = paymentRepository.findByIdAndMerchantId(paymentId,merchantId)
                .orElseThrow(()-> new ResourceNotFoundException("Payment", paymentId));

//        payment.setStatus(PaymentStatus.CAPTURING);//TODO state machine
        paymentTransitionService.apply(payment,PaymentEvent.CAPTURE_REQUEST);

        PaymentResult paymentResult = paymentGatewayRouter.capture(payment.getMethod(),paymentId);

        if(paymentResult instanceof PaymentResult.Success success){
//            payment.setStatus(PaymentStatus.CAPTURED);
            paymentTransitionService.apply(payment,PaymentEvent.CAPTURE_SUCCESS);
            payment.setCaprturedAt(LocalDateTime.now());
            log.info("Payment captured, paymentId :{}",paymentId);
        } else if (paymentResult instanceof PaymentResult.Failure failure) {
//            payment.setStatus(PaymentStatus.AUTHORIZED);
            paymentTransitionService.apply(payment,PaymentEvent.CAPTURE_FAIL);
            payment.setErrorCode(failure.errorCode());
            payment.setErrorDescription(failure.errorDescription());
            log.info("Payment captured failed, paymentId :{}",paymentId);
        }

        payment = paymentRepository.save(payment);

        //TODO: send an outbox(kafka_event)

        return paymentMapper.toResponse(payment);
    }
}
