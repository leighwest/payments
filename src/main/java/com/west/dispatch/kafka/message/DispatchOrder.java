package com.west.dispatch.kafka.message;

import com.west.dispatch.entity.Dispatch.DispatchStatus;
import lombok.*;


@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class DispatchOrder {

    private Long OrderId;

    private DispatchStatus dispatchStatus;
}
