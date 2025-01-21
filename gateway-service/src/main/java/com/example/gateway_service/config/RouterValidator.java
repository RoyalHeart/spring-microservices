/*******************************************************************************
 * Class        :className
 * Created date :2024/08/22
 * Lasted date  :2024/08/22
 * Author       :TamTH1
 * Change log   :2024/08/22 01-00 TamTH1 create a new
******************************************************************************/
package com.example.gateway_service.config;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@Component
public class RouterValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/api/auth/");

    public Predicate<ServerHttpRequest> securedRoute = request -> openApiEndpoints
            .stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
