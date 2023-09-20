package com.yupi.unifySearch.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.unifySearch.model.entity.Picture;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 聚合搜索结果视图
 * 该种方式对应于 https://tophub.today/
 * 相当于用一个对象封装不同类型的数据列表
 */
@Data
public class SearchVO implements Serializable {
    private List<UserVO> userList;
    private List<PostVO> postList;
    private List<Picture> pictureList;
    private List<?> dataList;
    private static final long serialVersionUID = 1L;
}
