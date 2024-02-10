package com.west.dispatch.kafka.listener;

import com.west.dispatch.entity.Dispatch.DispatchStatus;
import com.west.dispatch.kafka.message.DispatchOrder;
import com.west.dispatch.service.DispatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DispatchRequestKafkaListener {

    private final DispatchService dispatchService;

    @KafkaListener(
            id = "orderConsumerClient",
            topics = "order.created",
            groupId = "dispatch.order.created.consumer",
            containerFactory = "kafkaListenerContainerFactory"
    )

    public void receive(@Payload DispatchOrder payload) {
        log.info("Kafka broker received message with payload: {}", payload);

        if (payload.getDispatchStatus() == DispatchStatus.PENDING) {
            log.info("Dispatching order id: {}", payload.getOrderId());
            dispatchService.completeDispatch(payload);
        } else {
            // TODO: handle alternative dispatch status scenarios
        }
    }
}
