/*******************************************************************************
 * Class        :className
 * Created date :2024/12/03
 * Lasted date  :2024/12/03
 * Author       :TamTH1
 * Change log   :2024/12/03 01-00 TamTH1 create a new
******************************************************************************/
package com.example.message.enuminit;

import com.example.message.dto.MessageDto;
import com.example.message.dto.TempDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@Getter
@RequiredArgsConstructor
public enum ClassEnum {
    TempDto(TempDto.class),
    MessageDto(MessageDto.class);

    ClassEnum(Class<?> class1) {
    }

    Class<?> className;

}
