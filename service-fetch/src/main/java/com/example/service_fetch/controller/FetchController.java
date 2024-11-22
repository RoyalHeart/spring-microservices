package com.example.service_fetch.controller;

/*******************************************************************************
 * Class        :className
 * Created date :2024/08/19
 * Lasted date  :2024/08/19
 * Author       :TamTH1
 * Change log   :2024/08/19 01-00 TamTH1 create a new
******************************************************************************/

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import com.example.service_fetch.entity.StockGroup;
import com.example.service_fetch.util.CastUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@RestController
@RequestMapping("/api/fetch")
@Slf4j
public class FetchController {
    @Autowired
    private Environment env;

    @Autowired
    private RestClient restClient;

    @RequestMapping("/")
    public String home() {
        // This is useful for debugging
        // When having multiple instance of gallery service running at different ports.
        // We load balance among them, and display which instance received the request.
        return "Hello from Gallery Service running at port: " + env.getProperty("local.server.port");
    }

    @RequestMapping("/{stockGroup}")
    public StockGroup getGroup(@PathVariable final String stockGroup) {
        // create gallery object
        log.info(stockGroup);
        StockGroup group = new StockGroup();
        group.setGroup(stockGroup);

        // get list of available images
        List<?> list = restClient.get().uri("http://SERVICE-DATA/api/data/stocks").retrieve().body(List.class);
        List<Object> stocks = CastUtil.castList(Object.class, list);
        // List<Object> stocks = CastUtil.castList(Object.class,restClient.get().uri("http://SERVICE-DATA/api/data/stocks").retrieve().body(List.class));
        // List<Object> stocks = CastUtil.castList(Object.class,
        //         restTemplate.getForObject("lb://SERVICE-DATA/stocks", List.class));
        log.info(stocks.toString());
        group.setStocks(stocks);

        return group;
    }

    // -------- Admin Area --------
    // This method should only be accessed by users with role of 'admin'
    // We'll add the logic of role based auth later
    @RequestMapping("/admin")
    public String homeAdmin(HttpServletRequest request, HttpServletResponse response) {
        log.info(request.getHeader("Authorization"));
        return "This is the admin area of Gallery service running at port: " + env.getProperty("local.server.port");
    }
}