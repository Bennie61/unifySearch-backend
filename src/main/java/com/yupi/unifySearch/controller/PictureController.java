package com.yupi.unifySearch.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yupi.unifySearch.common.BaseResponse;
import com.yupi.unifySearch.common.ErrorCode;
import com.yupi.unifySearch.common.ResultUtils;
import com.yupi.unifySearch.exception.ThrowUtils;
import com.yupi.unifySearch.model.dto.picture.PictureQueryRequest;
import com.yupi.unifySearch.model.entity.Picture;
import com.yupi.unifySearch.model.entity.Post;
import com.yupi.unifySearch.model.entity.User;
import com.yupi.unifySearch.model.vo.PostVO;
import com.yupi.unifySearch.service.PictureService;
import com.yupi.unifySearch.service.PostService;
import com.yupi.unifySearch.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 图片接口
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {
    @Resource
    private PictureService pictureService;
    /**
     * 分页获取列表（封装类）
     * @param pictureQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
                                                        HttpServletRequest request) {
        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        String searchText = pictureQueryRequest.getSearchText();
        Page<Picture> picturePage = pictureService.searchPicture(searchText, current, size);
        return ResultUtils.success(picturePage);
    }
}
