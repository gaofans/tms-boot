package com.bettem.tms.boot.commons.utils.exception;

import com.bettem.tms.boot.commons.utils.exception.constant.ServiceExceptionEnum;

/**
 * 系统异常基类
 * @author GaoFans
 */
public class TmsException extends RuntimeException {
    private Integer code;

    private String message;

    public TmsException(ServiceExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
    }

    public TmsException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
