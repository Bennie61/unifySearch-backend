package com.yupi.unifySearch.controller;

import com.yupi.unifySearch.common.BaseResponse;
import com.yupi.unifySearch.common.ResultUtils;
import com.yupi.unifySearch.manager.SearchFacade;
import com.yupi.unifySearch.model.dto.search.SearchRequest;
import com.yupi.unifySearch.model.vo.SearchVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 聚合搜索 Controller，
 * 门面模式整体逻辑见README
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {
    @Resource
    private SearchFacade searchFacade; // 引入搜索门面类

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request){
        return ResultUtils.success(searchFacade.searchAll(searchRequest,request));
    }
}