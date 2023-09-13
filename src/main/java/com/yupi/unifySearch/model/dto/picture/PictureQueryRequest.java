package com.yupi.unifySearch.model.dto.picture;

import com.yupi.unifySearch.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Description 图片查询请求
 * @Author benny
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class PictureQueryRequest extends PageRequest implements Serializable {
    /**
     * 搜索词
     */
    private String searchText;
    private static final long serialVersionUID = 1L;
}
