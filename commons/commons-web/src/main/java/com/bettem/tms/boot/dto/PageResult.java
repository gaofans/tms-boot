package com.bettem.tms.boot.dto;

import java.math.BigDecimal;

public class PageResult {

    private long totalCount;
    private long pageSize;
    private long pageNo;
    private long totalPage;
    private Object data;

    public PageResult() {
    }

    public PageResult(Object data) {
        this.data = data;
    }

    public PageResult(long totalCount, Object data) {
        this.totalCount = totalCount;
        this.data = data;
    }

    public PageResult(long totalCount, long pageSize, long pageNo, Object data) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.totalPage = totalCount == 0 ? 0 : new BigDecimal(totalCount).divide(new BigDecimal(pageSize),BigDecimal.ROUND_UP).longValue();

        this.data = data;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageNo() {
        return pageNo;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
