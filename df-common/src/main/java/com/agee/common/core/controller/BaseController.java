package com.agee.common.core.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.agee.common.core.domain.R;
import com.agee.common.core.page.PageDomain;
import com.agee.common.core.page.PageModel;
import com.agee.common.core.page.TableDataInfo;
import com.agee.common.core.page.TableSupport;
import com.agee.common.utils.PageUtils;
import com.agee.common.utils.ServletUtils;
import com.agee.common.utils.SqlUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * @author qimingjin
 * @date 2022-09-16 10:43
 * @Description:
 */
@Slf4j
public class BaseController {
    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtil.parseDate(text));
            }
        });
    }
    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageUtils.startPage();
    }
    /**
     * 设置请求排序数据
     */
    protected void startOrderBy() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (ObjectUtil.isNotEmpty(pageDomain.getOrderBy())) {
            String orderBy = SqlUtils.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.orderBy(orderBy);
        }
    }

    /**
     * 清理分页的线程变量
     */
    protected void clearPage()
    {
        PageUtils.clearPage();
    }

    /**
     * 获取request
     */
    public HttpServletRequest getRequest()
    {
        return ServletUtils.getRequest();
    }

    /**
     * 获取response
     */
    public HttpServletResponse getResponse()
    {
        return ServletUtils.getResponse();
    }


    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected R<TableDataInfo> getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        rspData.setPageIndex(new PageInfo(list).getPageNum());
        rspData.setPageSize(new PageInfo(list).getPageSize());
        return R.ok(rspData);
    }

    /**
     * flowable的分页
     * @param list
     * @return
     */
    protected R<TableDataInfo> getFlowDataTable(PageModel<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setRows(list.getPagedRecords());
        rspData.setTotal(list.getTotalCount());
        rspData.setPageIndex(list.getPageNo());
        rspData.setPageSize(list.getPageSize());
        return R.ok(rspData);
    }

    /**
     * 利用subList方法进行分页
     * @param list 分页数据
     */
    public R<TableDataInfo> pageBySubList(List list) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageIndex = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        TableDataInfo rspData = new TableDataInfo();
        int totalCount = list.size();
        int pageCount = 0;
        List<String> subList;
        int m = totalCount % pageSize;
        if (m > 0) {
            pageCount = totalCount / pageSize + 1;
        } else {
            pageCount = totalCount / pageSize;
        }
        if (m == 0) {
            subList = list.subList((pageIndex - 1) * pageSize, pageSize * (pageIndex));
        } else {
            if (pageIndex == pageCount) {
                subList = list.subList((pageIndex - 1) * pageSize, totalCount);
            } else {
                subList = list.subList((pageIndex - 1) * pageSize, pageSize * (pageIndex));
            }
        }
        rspData.setRows(subList);
        rspData.setTotal(totalCount);
        rspData.setPageIndex(pageIndex);
        rspData.setPageSize(pageSize);
        return R.ok(rspData);
    }
}
