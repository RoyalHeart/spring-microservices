package com.example.gateway_service.prop;

import lombok.Data;

@Data
public class Jwt {
    private int validTime = 0;
    private String base64Secret;
}
