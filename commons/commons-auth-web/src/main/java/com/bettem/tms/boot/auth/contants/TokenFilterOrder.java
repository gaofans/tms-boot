package com.bettem.tms.boot.auth.contants;

import com.bettem.tms.boot.constant.FilterOrder;

/**
 * 过滤器的顺序
 * @author GaoFans
 */
public interface TokenFilterOrder extends FilterOrder {

    int TOKEN_CREATE_FILTER_ORDER = MIN + 5;
    int TOKEN_PARSE_FILTER_ORDER = MIN + 10;
    int LOGIN_FILTER_ORDER = MIN + 15;
}
