package com.west.payments.kafka.message;

import lombok.*;

import java.math.BigDecimal;


@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class PaymentOrder {

    public enum PaymentStatus {
        PENDING,
        COMPLETED,
        CANCELED,
        FAILED
    }

    private Long OrderId;

    private BigDecimal totalPrice;

    private PaymentStatus paymentStatus;
}
