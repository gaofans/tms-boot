package com.bettem.tms.boot.constant;

/**
 * 过滤器的顺序
 * @author GaoFans
 */
public interface FilterOrder {

    int MIN = Integer.MIN_VALUE + 100;
    int REQUEST_HOLDER_ORDER = MIN;
    int XSS_ORDER = MIN + 1;
}
