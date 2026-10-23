package com.shubham.razorpay.payment.entity;

import com.shubham.razorpay.common.entity.BaseEntity;
import com.shubham.razorpay.common.entity.Money;
import com.shubham.razorpay.common.enums.PaymentMethod;
import com.shubham.razorpay.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "payment", indexes = {
        @Index(name = "idx_payment_order_id",columnList = "order_id"),
        @Index(name = "idx_payment_merchant_id", columnList = "merchant_id")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderRecord order;
    @Column(nullable = false)
    private UUID merchantId;
    @Embedded
    private Money amount;
    @Column(nullable = false, length = 100, unique = true)
    private String idempotencyKey;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    private PaymentStatus status;
    @Column(nullable = false)
    private PaymentMethod method;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name="method_details", columnDefinition = "jsonb")
    private Map<String, Object> methodDetails;
    @Column(length = 100)
    private String BankReference;
    @Column(length = 100)
    private String processorReference;
    @Column(length = 100)
    private String errorCode;
    @Column(length = 500)
    private String errorDescription;
    private LocalDateTime caprturedAt;
    private LocalDateTime failedAt;
    private LocalDateTime refundedAt;
    private LocalDateTime settledAt;

}
