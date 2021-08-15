package com.bettem.tms.boot.commons.utils.exception;

import com.bettem.tms.boot.commons.utils.exception.constant.ServiceExceptionEnum;

/**
 * 业务异常
 * @author GaoFans
 */
public class BusinessException extends TmsException {

	public BusinessException(ServiceExceptionEnum serviceExceptionEnum) {
		super(serviceExceptionEnum);
	}

	public BusinessException(Integer code, String message) {
		super(code, message);
	}
}
