package com.shubham.razorpay.payment.entity;

import com.shubham.razorpay.common.entity.BaseEntity;
import com.shubham.razorpay.common.entity.Money;
import com.shubham.razorpay.common.enums.RefundStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "refund")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Refund extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;
    @Column(name = "merchant_id", nullable = false)
    private UUID merchantId;
    @Embedded
    private Money amount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RefundStatus status=RefundStatus.PENDING;
    @Column(length = 100)
    private String bankReference;
    @Column(length = 100)
    private String errorCode;
    @Column(length = 500)
    private String errorDescription;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "notes", columnDefinition = "jsonb")
    private Map<String, Object> notes;

    private LocalDateTime processedAt;

}
