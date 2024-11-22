/*******************************************************************************
 * Class        :className
 * Created date :2024/10/03
 * Lasted date  :2024/10/03
 * Author       :TamTH1
 * Change log   :2024/10/03 01-00 TamTH1 create a new
******************************************************************************/
package com.example.service_data.api.external.ssi.req;

import java.time.LocalDateTime;

import jakarta.annotation.Nonnull;
import lombok.Builder;
import lombok.Data;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@Data
@Builder
public class PriceRequest {
    @Builder.Default
    private String resolution = "1D";
    @Nonnull
    private String symbol;
    private LocalDateTime from;
    private LocalDateTime to;
}
