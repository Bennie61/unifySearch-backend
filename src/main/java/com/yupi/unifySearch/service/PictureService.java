package com.yupi.unifySearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.unifySearch.model.entity.Picture;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description 图片服务
 * @Author benny
 **/
@Service
public interface PictureService{
    Page<Picture> searchPicture(String searchText, long pageNum, long pageSize);
}
