package com.shubham.razorpay.common.exception;

import lombok.Getter;

@Getter
public class BussinessRuleViolationException extends RuntimeException{
    private final String errorCode;
    public BussinessRuleViolationException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }
}
