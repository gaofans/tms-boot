package com.bettem.tms.boot.commons.dto;

import java.io.Serializable;

/**
 * 通用数据传输对象
 * @author GaoFans
 */
public class RR<T> implements Serializable {

    private static final long serialVersionUID = 3468352004150968551L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 返回对象
     */
    private T result;

    public RR() {
        super();
    }

    public RR(Integer code) {
        super();
        this.code = code;
    }

    public RR(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public RR(CodeStatus codeStatus) {
        super();
        this.code = codeStatus.getCode();
        this.message = codeStatus.getMessage();
    }
    public RR(CodeStatus codeStatus, T result) {
        super();
        this.code = codeStatus.getCode();
        this.message = codeStatus.getMessage();
        this.result = result;
    }
    public RR(T result){
        super();
        this.code = CodeStatus.OK.getCode();
        this.message = CodeStatus.OK.getMessage();
        this.result = result;
    }
    public RR(Integer code, Throwable throwable) {
        super();
        this.code = code;
        this.message = throwable.getMessage();
    }

    public RR(Integer code, T result) {
        super();
        this.code = code;
        this.result = result;
    }

    public RR(Integer code, String message, T result) {
        super();
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RR<?> other = (RR<?>) obj;
        if (result == null) {
            if (other.result != null) {
                return false;
            }
        } else if (!result.equals(other.result)) {
            return false;
        }
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        if (code == null) {
            if (other.code != null) {
                return false;
            }
        } else if (!code.equals(other.code)) {
            return false;
        }
        return true;
    }

    /**
     * 通用状态码
     */
    public enum CodeStatus {
        OK(0,"请求成功"),
        FAIL(400,"请求失败");
        private Integer code;
        private String message;

        CodeStatus(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
