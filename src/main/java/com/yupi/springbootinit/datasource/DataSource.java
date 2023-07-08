package com.yupi.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * TODO 数据源接口（新接入数据源的时候必须实现）
 *
 * @param <T>
 *
 * @author Junting
 */
public interface DataSource<T> {

    /**
     * 搜索接口
     *
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<T> doSearch(String searchText, long pageNum, long pageSize);
}
