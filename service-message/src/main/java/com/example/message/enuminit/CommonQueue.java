/*******************************************************************************
 * Class        :className
 * Created date :2024/12/02
 * Lasted date  :2024/12/02
 * Author       :TamTH1
 * Change log   :2024/12/02 01-00 TamTH1 create a new
******************************************************************************/
package com.example.message.enuminit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@RequiredArgsConstructor
public enum CommonQueue {

    QUEUE("${queue.name}");

    @Getter
    private final String value;

}
