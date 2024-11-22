package com.example.core.api.handler.impl;

import org.springframework.stereotype.Component;

import com.example.core.api.error.ErrorDetail;
import com.example.core.api.handler.IResponseHandler;
import com.example.core.api.res.BaseResponse;
import com.example.core.exception.CustomException;

@Component
public class ResponseHandler implements IResponseHandler {
    public BaseResponse handleSuccessRequest(Object data, long start) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(data);
        baseResponse.setMessage("success");
        long took = System.currentTimeMillis() - start;
        baseResponse.setTook(took);
        return baseResponse;
    }

    public BaseResponse handleErrorRequest(Exception e, Object data, long start) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(data);
        baseResponse.setMessage("error");
        long took = System.currentTimeMillis() - start;
        baseResponse.setTook(took);
        if (e instanceof CustomException) {
            ErrorDetail errorDetail = new ErrorDetail();
            errorDetail.setMessage(e.getMessage());
            baseResponse.addError(errorDetail);
        }
        if (e instanceof Exception) {
            ErrorDetail errorDetail = new ErrorDetail();
            errorDetail.setMessage(e.getMessage());
            baseResponse.addError(errorDetail);
        }
        return baseResponse;

    }
}
