package com.shubham.razorpay.merchant.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "merchant_webhook_config")
public class MerchantWebhookConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;
    @Column(nullable = false, length = 500)
    private String targetUrl; //www.zara.com/webhook/success
    @Column(length = 255)
    private String webhookSecretHash;
    @Column(nullable = false)
    private Boolean enabled = true;
    @Column(length = 500)
    private String eventTypes; // Comma-separated list of event types to subscribe to

}
