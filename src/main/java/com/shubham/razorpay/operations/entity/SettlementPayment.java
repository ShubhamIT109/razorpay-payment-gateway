package com.shubham.razorpay.operations.entity;

import com.shubham.razorpay.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "settlement_payment")
@Getter
@Setter
@NoArgsConstructor
public class SettlementPayment{
    @EmbeddedId
     private SettlementPaymentId id;
    @MapsId("settlementId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "settlement_id", nullable = false)
    private Settlement settlement;
}
