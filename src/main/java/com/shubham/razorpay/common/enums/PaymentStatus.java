package com.shubham.razorpay.common.enums;

public enum PaymentStatus {
    CREATED,
    AUTHORIZING,
    AUTHORIZED,
    CAPTURING,
    CAPTURED,
    CANCELLED,
    REFUNDED,
    PARTIALLY_REFUNDED,
    FAILED,
    SETTLED,
    AUTH_EXPIRED
}
