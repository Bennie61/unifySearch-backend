package com.yupi.unifySearch.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.unifySearch.common.BaseResponse;
import com.yupi.unifySearch.common.ErrorCode;
import com.yupi.unifySearch.common.ResultUtils;
import com.yupi.unifySearch.exception.BusinessException;
import com.yupi.unifySearch.exception.ThrowUtils;
import com.yupi.unifySearch.manager.SearchFacade;
import com.yupi.unifySearch.model.dto.post.PostQueryRequest;
import com.yupi.unifySearch.model.dto.search.SearchRequest;
import com.yupi.unifySearch.model.dto.user.UserQueryRequest;
import com.yupi.unifySearch.model.entity.Picture;
import com.yupi.unifySearch.model.enums.SearchTypeEnum;
import com.yupi.unifySearch.model.vo.PostVO;
import com.yupi.unifySearch.model.vo.SearchVO;
import com.yupi.unifySearch.model.vo.UserVO;
import com.yupi.unifySearch.service.PictureService;
import com.yupi.unifySearch.service.PostService;
import com.yupi.unifySearch.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 接口
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {
    @Resource
    private UserService userService;
    @Resource
    private PostService postService;
    @Resource
    private PictureService pictureService;

    @Resource
    private SearchFacade searchFacade; // 引入搜索门面类

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request){
        return ResultUtils.success(searchFacade.searchAll(searchRequest,request));
    }
}