/*******************************************************************************
 * Class        :className
 * Created date :2024/08/19
 * Lasted date  :2024/08/19
 * Author       :TamTH1
 * Change log   :2024/08/19 01-00 TamTH1 create a new
******************************************************************************/
package com.example.service_data.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service_data.api.external.ssi.req.PriceRequest;
import com.example.service_data.api.external.ssi.res.SsiPriceResponse;
import com.example.service_data.api.external.ssi.res.SsiPriceResponse.PriceData;
import com.example.service_data.api.external.ssi.rest.SSI;
import com.example.service_data.entity.Stock;
import com.example.service_data.res.StockResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@RestController
@RequestMapping("/api/data")
@Slf4j
public class DataController {
    @Autowired
    private Environment env;

    @Autowired
    private SSI ssi;

    @GetMapping("/stocks/{symbol}")
    public StockResponse getStocks(@PathVariable String symbol) {
        List<Stock> stocks = new ArrayList<>();
        PriceRequest priceRequest = PriceRequest.builder().resolution("12H").symbol(symbol)
                .from(LocalDateTime.now().minus(30, ChronoUnit.DAYS)).to(LocalDateTime.now()).build();
        SsiPriceResponse ssiResponse = ssi.getPrice(priceRequest);
        PriceData data = ssiResponse.getData();
        for (int i = 0; i < data.getL().size(); i++) {
            BigDecimal closePrice = data.getL().get(i);
            Integer time = data.getT().get(i);
            Instant instant = Instant.ofEpochSecond(time);
            ZonedDateTime z = ZonedDateTime.ofInstant(instant, ZoneOffset.systemDefault());
            stocks.add(new Stock(z, closePrice,symbol));
        }
        StockResponse response = new StockResponse(env.getProperty("local.server.port"), stocks);
        return response;
    }

}
