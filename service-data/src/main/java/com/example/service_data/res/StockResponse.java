/*******************************************************************************
 * Class        :className
 * Created date :2024/10/03
 * Lasted date  :2024/10/03
 * Author       :TamTH1
 * Change log   :2024/10/03 01-00 TamTH1 create a new
******************************************************************************/
package com.example.service_data.res;

import java.util.List;

import com.example.service_data.entity.Stock;

import lombok.Data;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@Data
public class StockResponse {
    private final String port;
    private final List<Stock> stocks;
}
