package com.shubham.razorpay.merchant.entity;

import com.shubham.razorpay.common.entity.BaseEntity;
import com.shubham.razorpay.common.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name="app_user",
    indexes = {
        @Index(name="idx_app_user_merchant_id",columnList = "merchant_id")
    }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_id", nullable = false)
    private Merchant merchant;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String passwordHash;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

}
