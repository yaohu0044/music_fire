package com.musicfire.common.utiles;

import java.util.List;

public class BasePage {
    private static final long serialVersionUID = -6710532081301192385L;

    /**
     * 基本属性分析：
     * 1.当前页 currentPage
     * 2.每页多少数据 pageSize
     * 3.数据总条数 totalCount
     * 4.总页数 pageCount
     * 5.数据集合 List<T> list
     * 6.开始位置
     */
    private int currentPage = 1;

    private int pageSize = 10;

    private int totalCount;

    private int pageCount;


    private List<?> list;

    private int startPosition;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        setStartPosition(currentPage);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        startPosition = (currentPage-1)* pageSize;
        this.startPosition = startPosition;
    }
}
