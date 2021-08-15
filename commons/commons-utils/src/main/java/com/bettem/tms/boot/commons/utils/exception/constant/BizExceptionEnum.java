package com.bettem.tms.boot.commons.utils.exception.constant;

/**
 * 所有业务异常的枚举
 * @author GaoFans
 */
public enum BizExceptionEnum implements ServiceExceptionEnum {

    /**
     * 账户问题
     */
    USER_ALREADY_REG(401, "该用户已存在"),
    USER_NOT_EXISTED(400, "没有此用户"),
    ACCOUNT_FREEZE(401, "账号被冻结"),
    PWD_NOT_RIGHT(400,"密码不正确"),
    OLD_PWD_NOT_RIGHT(402, "原密码不正确"),
    TWO_PWD_NOT_MATCH(405, "两次输入密码不一致"),

    /**
     * 错误的请求
     */
    MENU_PCODE_COINCIDENCE(400, "菜单编号和副编号不能一致"),
    EXISTED_THE_MENU(400, "菜单编号重复，不能添加"),
    DICT_MUST_BE_NUMBER(400, "字典的值必须为数字"),
    REQUEST_NULL(400, "请求有错误"),
    SESSION_TIMEOUT(400, "会话超时"),
    SERVER_ERROR(500, "服务器异常"),
    /**
     * token异常
     */
    TOKEN_EXPIRED(700, "token过期"),
    TOKEN_ERROR(700, "token验证失败"),

    /**
     * 签名异常
     */
    SIGN_ERROR(700, "签名验证失败"),

    /**
     * 其他
     */
    AUTH_REQUEST_ERROR(400, "账号密码错误"),
    VERIFY_CODE_ERROR(400, "验证码错误"),

    /**
     * 权限异常
     */
    AUTHORITY_ERROR(403,"权限异常"),
    NOT_LOGIN_ERROR(401,"尚未登录"),
    LOGIN_TIME_OUT(401,"登录超时");;
    BizExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    @Override
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
