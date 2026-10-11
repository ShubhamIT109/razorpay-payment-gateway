package com.shubham.razorpay.merchant.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;
    @Column(length = 200)
    private String name;
    @Column(length = 200)
    private String email;
    @Column(length = 15)
    private String contactNumber;

    private LocalDateTime deletedAt;
}
