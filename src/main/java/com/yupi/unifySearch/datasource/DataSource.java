package com.yupi.unifySearch.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 数据源接口(新接入的数据源必须实现)---适配器模式
 * 适配器模式的作⽤：通过转换，让两个系统能够完成对接。
 */
public interface DataSource<T> {
    /**
     * 搜索接口
     * 之后所有要接入我们系统的数据源，它必须要实现该接口，必须提供搜索的方法，要求可根据关键词搜索，并且支持分页搜索。
     * 通过适配器模式统一了参数
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<T> doSearch(String searchText, long pageNum, long pageSize);

}