package com.yupi.unifySearch.model.dto.search;

import com.yupi.unifySearch.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description ToDo
 * @Author benny
 **/

@Data
public class SearchRequest extends PageRequest implements Serializable {
    /**
     * 搜索词
     */
    private String searchText;
    private static final long serialVersionUID = 1L;
}
