package com.bettem.tms.boot.commons.constant;

/**
 * 是否的枚举
 * @author GaoFans
 */

public enum YesOrNot {

    /**
     * 是
     */
    Y(true, "是", 1),
    /**
     * 否
     */
    N(false, "否", 0);

    private Boolean flag;
    private String desc;
    private Integer code;

    YesOrNot(Boolean flag, String desc, Integer code) {
        this.flag = flag;
        this.desc = desc;
        this.code = code;
    }

    public Boolean getFlag() {
        return flag;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }

    public static String valueOf(Integer status) {
        if (status != null) {
            for (YesOrNot s : YesOrNot.values()) {
                if (s.getCode().equals(status)) {
                    return s.getDesc();
                }
            }
        }
        return "";
    }

}
