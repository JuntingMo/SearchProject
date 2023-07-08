package com.yupi.springbootinit.model.dto.search;

import com.yupi.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 全部的查询请求
 *
 *@author Junting
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequest extends PageRequest implements Serializable {

    /**
     * 统一搜索词
     */
    private String searchText;

    /**
     *类型
     */
    private String type;

    private static final long serialVersionUID = 1L;
}