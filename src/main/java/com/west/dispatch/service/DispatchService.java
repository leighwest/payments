package com.west.dispatch.service;

import com.west.dispatch.entity.Dispatch;
import com.west.dispatch.entity.Dispatch.DispatchStatus;
import com.west.dispatch.kafka.message.DispatchOrder;
import com.west.dispatch.kafka.publisher.DispatchEventKafkaPublisher;
import com.west.dispatch.repository.DispatchRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DispatchService {

    private final DispatchRepository dispatchRepository;

    private final DispatchEventKafkaPublisher dispatchEventKafkaPublisher;
    private final int SLEEP_DURATION = 5000; // 5000 milliseconds (1 second)

    public void completeDispatch(DispatchOrder dispatchOrder) {
        Dispatch orderDispatch;
        boolean dispatchResult = simulateDispatch();

        if (dispatchResult) {
            log.info("Dispatch successfully completed for order id: {}", dispatchOrder.getOrderId());
            orderDispatch = dispatchRepository.save(Dispatch.builder()
                            .OrderId(dispatchOrder.getOrderId())
                            .dispatchStatus(DispatchStatus.COMPLETED)
                            .build());
        } else {
            log.error("Dispatch processing failed for order id: {}", dispatchOrder.getOrderId());
            orderDispatch = dispatchRepository.save(Dispatch.builder()
                    .OrderId(dispatchOrder.getOrderId())
                    .dispatchStatus(DispatchStatus.FAILED)
                    .build());
        }

        try {
            dispatchEventKafkaPublisher.process(convertToDispatchOrder(orderDispatch));
        } catch (Exception e) {
            log.error("Error while publishing dispatch order message with order ID {}, error: {}",
                    orderDispatch.getId(), e.getMessage());
        }
    }

    private boolean simulateDispatch() {
        try {
            Thread.sleep(SLEEP_DURATION);
            return true;
        } catch (InterruptedException e) {
            log.error("Thread sleep interrupted: " + e.getMessage());
            return false;
        }
    }

    private DispatchOrder convertToDispatchOrder(Dispatch dispatch) {
        return DispatchOrder.builder()
                .OrderId(dispatch.getOrderId())
                .dispatchStatus(dispatch.getDispatchStatus())
                .build();
    }
}
