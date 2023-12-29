package com.west.payments.kafka.handler;

import com.west.payments.kafka.message.PaymentOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderCreatedHandler {

//    private final PaymentService paymentService;

    @KafkaListener(
            id = "orderConsumerClient",
            topics = "order.created",
            groupId = "dispatch.order.created.consumer",
            containerFactory = "kafkaListenerContainerFactory"
    )
//    public void listen(@Header(KafkaHeaders.RECEIVED_KEY) String key, @Payload PaymentOrder payload) {
//        log.info("Kafka broker received message with key: {} - payload: {}", key, payload);
//    }
    public void listen(@Payload PaymentOrder payload) {
        log.info("Kafka broker received message with payload: {}", payload);
    }

}
