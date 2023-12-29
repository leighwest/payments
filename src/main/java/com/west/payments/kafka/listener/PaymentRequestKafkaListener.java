package com.west.payments.kafka.listener;

import com.west.payments.kafka.message.PaymentOrder;
import com.west.payments.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentRequestKafkaListener {

    private final PaymentService paymentService;

    @KafkaListener(
            id = "orderConsumerClient",
            topics = "order.created",
            groupId = "dispatch.order.created.consumer",
            containerFactory = "kafkaListenerContainerFactory"
    )

    public void receive(@Payload PaymentOrder payload) {
        log.info("Kafka broker received message with payload: {}", payload);

        if (payload.getPaymentStatus() == PaymentOrder.PaymentStatus.PENDING) {
            log.info("Processing payment for order id: {}", payload.getOrderId());
            paymentService.completePayment(payload);
        } else {
            // TODO: handle alternative payment status scenarios
        }
    }

}
