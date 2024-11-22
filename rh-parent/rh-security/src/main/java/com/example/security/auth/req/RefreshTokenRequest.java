/*******************************************************************************
 * Class        :className
 * Created date :2024/10/03
 * Lasted date  :2024/10/03
 * Author       :TamTH1
 * Change log   :2024/10/03 01-00 TamTH1 create a new
******************************************************************************/
package com.example.security.auth.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@Data
public class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;
}
