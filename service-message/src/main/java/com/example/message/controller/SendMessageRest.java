/*******************************************************************************
 * Class        :className
 * Created date :2024/12/02
 * Lasted date  :2024/12/02
 * Author       :TamTH1
 * Change log   :2024/12/02 01-00 TamTH1 create a new
******************************************************************************/
package com.example.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.core.api.controller.AbstractRestController;
import com.example.core.api.res.BaseResponse;
import com.example.message.bean.Sender;
import com.example.message.controller.req.SendReq;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * className
 * 
 * @version 01-00
 * @since 01-00
 * @author TamTH1
 */
@RestController
@RequestMapping("/api/message")
public class SendMessageRest extends AbstractRestController {
    @Autowired
    private Sender sender;

    @GetMapping("/hello")
    public BaseResponse getMessage(@RequestParam String message) {
        long start = System.currentTimeMillis();
        return responseHandler.handleSuccessRequest(message, start);
    }

    @PostMapping()
    public BaseResponse sendMessage(@RequestBody SendReq requestBody) {
        long start = System.currentTimeMillis();
        ObjectMapper mapper = new ObjectMapper();
        Object requestData = requestBody.getMessage();
        try {
            String data = mapper.writeValueAsString(requestBody.getMessage());
            sender.sendStringMessage(requestBody.getQueue(),
                    data);
            return responseHandler.handleSuccessRequest(requestData, start);
        } catch (Exception e) {
            return responseHandler.handleErrorRequest(e, requestBody.getMessage(), start);
        }
    }
}
