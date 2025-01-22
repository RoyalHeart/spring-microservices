/*******************************************************************************
* Class :className
* Created date :2024/12/02
* Lasted date :2024/12/02
* Author :TamTH1
* Change log :2024/12/02 01-00 TamTH1 create a new
******************************************************************************/
package com.example.message.bean;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.example.message.dto.MessageDto;
import com.example.message.dto.TempDto;
import com.example.message.enuminit.ClassEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * className
 *
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@Component
@Slf4j
public class Listener {

    @JmsListener(destination = "${queue.name}")
    public Object processMessage(String content)
            throws JsonMappingException, JsonProcessingException {
        log.info(">>> content {}", content);
        ObjectMapper mapper = new ObjectMapper();
        MessageDto message = mapper.readValue(content, MessageDto.class);
        ClassEnum classType = ClassEnum.valueOf(message.getClassName());
        Object data = null;
        switch (classType) {
            case MessageDto:
                break;
            case TempDto:
                TempDto temp = mapper.convertValue(message.getData(), TempDto.class);
                log.info(">>> message {}", temp.getTemp());
                break;
            default:
                break;
        }
        return data;
    }

}
