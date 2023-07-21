package com.uberClone.uberClone.socket;

import lombok.Data;

@Data
public class DriverResponse {
    private Long driverId;
    private boolean status;
    private Long orderId;
}
