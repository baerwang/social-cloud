package com.baerwang.common.entity;


import java.util.List;

/**
 * 分页结果类
 *
 * @author baerwang
 * @version 1.0
 * @date 2020/2/14 9:24
 */
public class PageResult<T> {
    private Long total;
    private List<T> rows;

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
