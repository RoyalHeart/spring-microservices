package com.example.core.api.handler.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.example.core.api.error.ErrorDetail;
import com.example.core.api.handler.IResponseHandler;
import com.example.core.api.res.BaseResponse;
import com.example.core.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ResponseHandler implements IResponseHandler {
    @Autowired
    private MessageSource messageSource;

    public BaseResponse handleSuccessRequest(Object data, long start) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(data);
        baseResponse.setMessage("success");
        long took = System.currentTimeMillis() - start;
        baseResponse.setTook(took);
        return baseResponse;
    }

    public BaseResponse handleErrorRequest(Exception e, long start) {
        return handleErrorRequest(e, null, start);
    }

    public BaseResponse handleErrorRequest(Exception e, Object data, long start) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(data);
        baseResponse.setMessage("error");
        if (e instanceof CustomException customException) {
            ErrorDetail errorDetail = new ErrorDetail();
            String message = resolveMessage(customException);
            errorDetail.setMessage(message);
            errorDetail.setCode(customException.getCode());
            baseResponse.addError(errorDetail);
        } else if (e instanceof Exception) {
            ErrorDetail errorDetail = new ErrorDetail();
            errorDetail.setMessage(e.getMessage());
            baseResponse.addError(errorDetail);
        }
        long took = System.currentTimeMillis() - start;
        baseResponse.setTook(took);
        return baseResponse;
    }

    private String resolveMessage(CustomException exception) {
        String message = exception.getMessage();
        String code = exception.getCode();
        if (code == null) {
            return message;
        }
        try {
            message = messageSource.getMessage(code, null, Locale.of("vi"));
        } catch (Exception ex) {
            log.error(">>> No messageSource found for code {}", code);
            message = "Unexpected error";
        }
        return message;
    }
}
