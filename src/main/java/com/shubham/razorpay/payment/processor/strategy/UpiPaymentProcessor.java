package com.shubham.razorpay.payment.processor.strategy;

import com.shubham.razorpay.common.util.RandomizerUtil;
import com.shubham.razorpay.payment.processor.PaymentProcessor;
import com.shubham.razorpay.payment.processor.dto.PaymentProcessorRequest;
import com.shubham.razorpay.payment.processor.dto.PaymentProcessorResponse;

public class UpiPaymentProcessor implements PaymentProcessor {
    @Override
    public PaymentProcessorResponse charge(PaymentProcessorRequest request) {
        final String VPA_CODE_FAIL = "fail@okaxis";

        String bankCode = request.methodDetails() != null ?
                request.methodDetails().get("vpa").toString():null;

        //simulation
        if(VPA_CODE_FAIL.equals(bankCode)){
            return new PaymentProcessorResponse.Failure("UPI_REJECTED",
                    "Bank rejected the transaction registration"
            );
        }

        String processorRef = "UPI_PROCESSOR_"+ RandomizerUtil.randomBase64(16);

        String bankRef =  "BANK_REF"+processorRef;

        return new PaymentProcessorResponse.Success(processorRef, bankRef);
    }
}
