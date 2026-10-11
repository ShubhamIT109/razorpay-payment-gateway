package com.shubham.razorpay.common.enums;

public enum PaymentEvent {
    AUTHORIZE_ATTEMPT,
    AUTHORIZE_SUCCESS,
    AUTHORIZE_FAIL,
    CAPTURE_REQUEST,
    CAPTURE_SUCCESS,
    CAPTURE_FAIL,
    REFUND_INIT,
    REFUND_COMPLETE,
    SETTLE,
    CANCEL,
    CAPRTURE_TIMEOUT
}
