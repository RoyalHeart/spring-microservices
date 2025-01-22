package com.example.message.bean;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/*******************************************************************************
* Class :className
* Created date :2024/12/02
* Lasted date :2024/12/02
* Author :TamTH1
* Change log :2024/12/02 01-00 TamTH1 create a new
******************************************************************************/
/**
 * className
 *
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@Component
public class Sender {
    private final JmsTemplate jmsTemplate;

    public Sender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Object sendStringMessage(String queueName, String data) {
        jmsTemplate.send(queueName, s -> s.createTextMessage(data));
        return data;
    }

}
