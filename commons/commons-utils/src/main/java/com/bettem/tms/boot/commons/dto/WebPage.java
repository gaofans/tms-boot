package com.bettem.tms.boot.commons.dto;

import java.util.List;

/**
 * 分页对象
 * @author gaofans
 */
public class WebPage<T> {

    private long count;

    private int offset;

    private int limit;

    private List<T> records;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
