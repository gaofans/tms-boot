package com.bettem.tms.boot.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bettem.tms.boot.base.warpper.BaseControllerWrapper;
import com.bettem.tms.boot.dto.PageResult;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 分页工具类
 * @author GaoFans
 */
public class PageFactory<T> {

    private long limitSize = 20;
    private long pageNo = 1;

    /**
     * 获取table的分页参数
     * @return
     */
    public IPage<T> defaultPage() {
        HttpServletRequest request = HttpKit.getRequest();

        //每页多少条数据
        String limitString = request.getParameter("limitSize");
        if (StringUtils.isNotBlank(limitString)) {
            limitSize = Long.parseLong(limitString);
        }

        //第几页
        String pageString = request.getParameter("pageNo");
        if (StringUtils.isNotBlank(pageString)) {
            pageNo = Long.parseLong(pageString);
        }

        return new Page(pageNo, limitSize);
    }

    /**
     * 分页结果
     * @param page
     * @return
     */
    public PageResult createPageInfo(IPage<T> page) {
        PageResult result = new PageResult(page.getTotal(),limitSize,pageNo,page.getRecords());
        return result;
    }

    /**
     * 包装后的分页结果
     * @param page
     * @param wrapper
     * @return
     */
    public PageResult createPageInfo(IPage<T> page, BaseControllerWrapper wrapper){
        PageResult result = new PageResult(page.getTotal(),limitSize,pageNo,wrapper.setObj(page.getRecords()).wrap());
        return result;
    }
}
