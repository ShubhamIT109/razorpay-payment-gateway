package com.shubham.razorpay.operations.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
@NoArgsConstructor
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class SettlementPaymentId {
    private UUID settlementId;
    private UUID paymentId;

}
