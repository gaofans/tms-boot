package com.bettem.tms.boot.config;

/**
 * 缓存的属性
 * @author GaoFans
 */
public class CacheProperties {

    private TimeOutPolicy timeOutPolicy = TimeOutPolicy.NONE;

    private long timeout = 3600;

    public TimeOutPolicy getTimeOutPolicy() {
        return timeOutPolicy;
    }

    public void setTimeOutPolicy(String timeOutPolicy) {
        this.timeOutPolicy = TimeOutPolicy.get(timeOutPolicy);
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * 过期策略
     */
    public enum TimeOutPolicy{
        /**
         * 读
         */
        READ("r"),
        /**
         * 写
         */
        WRITE("w"),
        /**
         * 读和写
         */
        READ_AND_WRITE("rw"),
        /**
         * 无
         */
        NONE("none")
        ;
        String value;

        TimeOutPolicy(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static TimeOutPolicy get(String value){
            if(value != null){
                for (TimeOutPolicy policy : TimeOutPolicy.values()) {
                    if(policy.getValue().equals(value.trim().toLowerCase())){
                        return policy;
                    }
                }
            }
            return NONE;
        }
    }
}
