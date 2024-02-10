package com.west.dispatch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
(name = "dispatch")
public class Dispatch {

    public enum DispatchStatus {
        PENDING,
        COMPLETED,
        CANCELLED,
        FAILED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long OrderId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DispatchStatus dispatchStatus;
}
