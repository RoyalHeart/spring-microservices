/*******************************************************************************
 * Class        :className
 * Created date :2024/12/03
 * Lasted date  :2024/12/03
 * Author       :TamTH1
 * Change log   :2024/12/03 01-00 TamTH1 create a new
******************************************************************************/
package com.example.message.controller.req;

import com.example.message.dto.MessageDto;

import lombok.Data;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@Data
public class SendReq {
    private String queue;
    private MessageDto message;
}
