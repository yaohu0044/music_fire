package com.musicfire.common.utiles;

import lombok.Data;

import java.util.List;

@Data
public class BasePage {
    private static final long serialVersionUID = -6710532081301192385L;

    /**
     * 基本属性分析：
     * 1.当前页 currentPage
     * 2.每页多少数据 pageSize
     * 3.数据总条数 totalCount
     * 4.总页数 pageCount
     * 5.数据集合 List<T> list
     */
    private int currentPage = 1;

    private int pageSize = 10;

    private int totalCount;

    private int pageCount;


    private List<?> list;

}
