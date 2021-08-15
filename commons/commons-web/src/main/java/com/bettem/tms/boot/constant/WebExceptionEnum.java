package com.bettem.tms.boot.constant;

import com.bettem.tms.boot.commons.utils.exception.constant.ServiceExceptionEnum;

/**
 * 异常
 * @author GaoFans
 */
public enum  WebExceptionEnum implements ServiceExceptionEnum {

    REPEAT_FORM(101,"请勿重复提交"),
    ILLEGAL_ARGUMENT(102,"参数不正确");
    private Integer code;
    private String message;

    WebExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
