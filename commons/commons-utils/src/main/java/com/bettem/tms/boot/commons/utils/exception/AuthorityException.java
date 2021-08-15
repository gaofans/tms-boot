package com.bettem.tms.boot.commons.utils.exception;

import com.bettem.tms.boot.commons.utils.exception.constant.ServiceExceptionEnum;

/**
 * 权限异常
 * @author GaoFans
 */
public class AuthorityException extends TmsException {
    public AuthorityException(ServiceExceptionEnum serviceExceptionEnum) {
        super(serviceExceptionEnum);
    }

    public AuthorityException(Integer code, String message) {
        super(code, message);
    }
}
