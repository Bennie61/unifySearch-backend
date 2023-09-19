package com.yupi.unifySearch.model.dto.search;

import com.yupi.unifySearch.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * @Description 聚合查询请求
 * @Author benny
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequest extends PageRequest implements Serializable {
    /**
     * 搜索词
     */
    private String searchText;

    private String type;
    private static final long serialVersionUID = 1L;
}
