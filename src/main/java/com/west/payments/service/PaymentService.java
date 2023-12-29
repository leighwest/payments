package com.west.payments.service;

import com.west.payments.entity.Payment;
import com.west.payments.kafka.message.PaymentOrder;
import com.west.payments.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final int SLEEP_DURATION = 2000; // 1000 milliseconds (1 second)

    public void completePayment(PaymentOrder paymentOrder) {
        boolean paymentResult = simulatePayment();

        if (paymentResult) {
            paymentRepository.save(Payment.builder()
                            .OrderId(paymentOrder.getOrderId())
                            .totalPrice(paymentOrder.getTotalPrice())
                            .paymentStatus(PaymentOrder.PaymentStatus.COMPLETED)
                            .build());
        } else {
            log.error("Payment processing failed for order id: {}", paymentOrder.getOrderId());
            paymentRepository.save(Payment.builder()
                    .OrderId(paymentOrder.getOrderId())
                    .totalPrice(paymentOrder.getTotalPrice())
                    .paymentStatus(PaymentOrder.PaymentStatus.FAILED)
                    .build());
        }


        // TODO: send Kafka message back
    }

    private boolean simulatePayment() {
        try {
            Thread.sleep(SLEEP_DURATION);
            return true;
        } catch (InterruptedException e) {
            log.error("Thread sleep interrupted: " + e.getMessage());
            return false;
        }
    }
}
