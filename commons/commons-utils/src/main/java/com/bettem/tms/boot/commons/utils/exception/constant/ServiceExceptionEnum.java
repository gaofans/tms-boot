package com.bettem.tms.boot.commons.utils.exception.constant;

/**
 * 抽象接口
 * @author GaoFans
 */
public interface ServiceExceptionEnum {

	/**
	 * 获取异常编码
	 */
	Integer getCode();

	/**
	 * 获取异常信息
	 */
	String getMessage();
}
