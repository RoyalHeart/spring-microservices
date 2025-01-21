package com.example.gateway_service.prop;

import lombok.Data;

@Data
public class RateLimit {
    private int capacity = 0;
    private int refillAmount = 0;
    private int refillMinutes = 0;
}
