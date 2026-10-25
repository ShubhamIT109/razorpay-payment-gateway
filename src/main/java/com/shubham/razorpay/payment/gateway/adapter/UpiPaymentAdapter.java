package com.shubham.razorpay.payment.gateway.adapter;

import com.shubham.razorpay.common.enums.PaymentMethod;
import com.shubham.razorpay.payment.gateway.PaymentAdapter;
import com.shubham.razorpay.payment.gateway.dto.PaymentRequest;
import com.shubham.razorpay.payment.gateway.dto.PaymentResult;
import com.shubham.razorpay.payment.processor.PaymentProcessorRouter;
import com.shubham.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.shubham.razorpay.payment.processor.dto.PaymentProcessorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class UpiPaymentAdapter implements PaymentAdapter {

    private final PaymentProcessorRouter paymentProcessorRouter;

    @Override
    public PaymentResult initiate(PaymentRequest request){
        log.info("Initiate Payment with UPI, paymentId:{}", request.paymentId());

        try {
            PaymentProcessorRequest paymentProcessorRequest = PaymentProcessorRequest.nonCard(
                    request.paymentId(),
                    PaymentMethod.UPI,
                    request.amount(),
                    request.methodDetails()
            );

            PaymentProcessorResponse paymentProcessorResponse =
                    paymentProcessorRouter.charge(paymentProcessorRequest);

            return switch (paymentProcessorResponse) {
                case PaymentProcessorResponse.Failure failure ->
                        new PaymentResult.Failure(failure.errorCode(), failure.errorDescription());

                case PaymentProcessorResponse.Pending pending ->
                        new PaymentResult.Pending(pending.processorReference());

                case PaymentProcessorResponse.Success success -> new PaymentResult.Success(success.bankReference());
            };
        }
        catch (Exception e){
            log.warn("UPI failed, paymentId: {}",request.paymentId());
            return new PaymentResult.Failure("UPI_FAILED",e.getMessage());
        }
    }

    @Override
    public PaymentResult capture(UUID paymentId) {
        return new PaymentResult.Success("UPI_REF");
    }
}
