package com.lin.oos.vo;

import com.lin.oos.pojo.PmsProductItem;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {


    private List<PmsProductItem> list;
    //当前页
    private Long pageNum;
    //总页数
    private Long pages;
    //导航栏第一个数
    private Long firstPage;
    //导航栏最后一个数
    private Long lastPage;
    //每页显示个数
    private Long pageSize;
    //前一页
    private Long prePage;

    //后一页
    private Long nextPage;

    public List<PmsProductItem> getList() {
        return list;
    }

    public void setList(List<PmsProductItem> list) {
        this.list = list;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public Long getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Long firstPage) {
        this.firstPage = firstPage;
    }

    public Long getLastPage() {
        return lastPage;
    }

    public void setLastPage(Long lastPage) {
        this.lastPage = lastPage;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPrePage() {
        return prePage;
    }

    public void setPrePage(Long prePage) {
        this.prePage = prePage;
    }

    public Long getNextPage() {
        return nextPage;
    }

    public void setNextPage(Long nextPage) {
        this.nextPage = nextPage;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    //导航栏个数
    private Long count;

}