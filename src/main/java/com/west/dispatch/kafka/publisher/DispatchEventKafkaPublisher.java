package com.west.dispatch.kafka.publisher;

import com.west.dispatch.kafka.message.DispatchOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispatchEventKafkaPublisher {

    private static final String DISPATCH_PROCESSED_TOPIC = "dispatch.processed";

    private final KafkaTemplate<String, Object> kafkaProducer;

    public void process(String key, DispatchOrder dispatchOrder) throws Exception {
        log.info("Sending dispatch event kafka message for order ID: {}", dispatchOrder.getOrderId());
        kafkaProducer.send(DISPATCH_PROCESSED_TOPIC, key, dispatchOrder).get();
    }
}
