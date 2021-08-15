package com.bettem.tms.boot.exception;

import com.bettem.tms.boot.commons.utils.exception.TmsException;
import com.bettem.tms.boot.commons.utils.exception.constant.ServiceExceptionEnum;

/**
 * 重复提交异常
 * @author GaoFans
 */
public class RepeatFormException extends TmsException {
    public RepeatFormException(ServiceExceptionEnum serviceExceptionEnum) {
        super(serviceExceptionEnum);
    }

    public RepeatFormException(Integer code, String message) {
        super(code, message);
    }
}
