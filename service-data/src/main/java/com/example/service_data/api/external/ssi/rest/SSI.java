/*******************************************************************************
 * Class        :className
 * Created date :2024/10/03
 * Lasted date  :2024/10/03
 * Author       :TamTH1
 * Change log   :2024/10/03 01-00 TamTH1 create a new
******************************************************************************/
package com.example.service_data.api.external.ssi.rest;

import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestHeadersSpec;

import com.example.service_data.api.external.ssi.req.PriceRequest;
import com.example.service_data.api.external.ssi.res.SsiPriceResponse;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@Component
public class SSI {
    @Autowired
    RestClient restClient;

    private final String GET_HIST_URL = "/statistics/charts/history";
    private final String SSI_HOST = "https://iboard-api.ssi.com.vn";

    public SsiPriceResponse getPrice(PriceRequest priceRequest) {
        RestClient restClient = RestClient.builder().baseUrl(SSI_HOST).build();
        RequestHeadersSpec<?> request = restClient.get()
                .uri(GET_HIST_URL + "?resolution={resolution}&symbol={symbol}&from={from}&to={to}",
                        priceRequest.getResolution(), priceRequest.getSymbol(), priceRequest.getFrom().toEpochSecond(ZoneOffset.UTC),
                        priceRequest.getTo().toEpochSecond(ZoneOffset.UTC))
                .headers(headers -> {
                    headers.add("Accept", "application/json");
                    headers.add("User-Agent",
                            "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Mobile Safari/537.36");
                }).accept(MediaType.APPLICATION_JSON);
        SsiPriceResponse response = request.retrieve().toEntity(SsiPriceResponse.class).getBody();
        return response;
    }
}
