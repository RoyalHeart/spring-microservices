/*******************************************************************************
 * Class        :className
 * Created date :2024/08/19
 * Lasted date  :2024/08/19
 * Author       :TamTH1
 * Change log   :2024/08/19 01-00 TamTH1 create a new
******************************************************************************/
package com.example.gateway_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@RestController
public class FallbackController {

    @GetMapping("/fallback1")
    public String userFallback() {
        return "User service is not available";
    }

    @GetMapping("/auth-fallback")
    public String authFallback() {
        return "Auth service is not available";
    }
}

