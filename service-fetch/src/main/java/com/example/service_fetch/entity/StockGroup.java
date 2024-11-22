/*******************************************************************************
 * Class        :className
 * Created date :2024/08/19
 * Lasted date  :2024/08/19
 * Author       :TamTH1
 * Change log   :2024/08/19 01-00 TamTH1 create a new
******************************************************************************/
package com.example.service_fetch.entity;

import java.util.List;

import lombok.Data;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@Data
public class StockGroup {
    String group;
    List<Object> stocks;
}
