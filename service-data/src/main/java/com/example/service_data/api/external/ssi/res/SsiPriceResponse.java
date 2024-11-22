/*******************************************************************************
 * Class        :className
 * Created date :2024/10/03
 * Lasted date  :2024/10/03
 * Author       :TamTH1
 * Change log   :2024/10/03 01-00 TamTH1 create a new
******************************************************************************/
package com.example.service_data.api.external.ssi.res;

import java.math.BigDecimal;
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
public class SsiPriceResponse {
    private String code;
    private String message;
    private PriceData data;
    private String paging;
    private String status;

    @Data
    public class PriceData {
        private List<Integer> t;
        private List<BigDecimal> o;
        private List<BigDecimal> h;
        private List<BigDecimal> l;
        private List<Integer> v;
        private String s;
    }
}
