package com.shubham.razorpay.vault.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "card_token")
public class CardToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, length = 50,unique = true)
    private String token;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vault_card_id", nullable = false)
    private VaultCard vaultCard;
    @Column(nullable = false)
    private UUID customerId;
    @Column(nullable = false)
    private UUID merchantId;
    private LocalDateTime revokeAt;
}
