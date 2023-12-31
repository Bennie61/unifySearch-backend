package com.yupi.unifySearch.controller;

/**
 * @Date 2023/9/22 13:39
 * @Description ToDo
 * @Difficulty Simple
 * @Tag
 * @Related Code_xxx;Code_xxx
 * @Website
 * @Author benny
 * @Version 1.0
 **/

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.unifySearch.common.BaseResponse;
import com.yupi.unifySearch.common.ErrorCode;
import com.yupi.unifySearch.common.ResultUtils;
import com.yupi.unifySearch.exception.BusinessException;
import com.yupi.unifySearch.exception.ThrowUtils;
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
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.apache.commons.lang3.StringUtils;


/**
 * 接口
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class test {
    @Resource
    private UserService userService;
    @Resource
    private PostService postService;
    @Resource
    private PictureService pictureService;
    @PostMapping("/all01")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request){
        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        String searchText = searchRequest.getSearchText();
        // 搜索出所有数据
        if (searchTypeEnum == null){
            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
                return userVOPage;
            });

            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                postQueryRequest.setSearchText(searchText);
                Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
                return postVOPage;
            });

            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
                Page<Picture> picturePage = pictureService.searchPicture(searchText, 1, 10);
                return picturePage;
            });

            CompletableFuture.allOf(userTask, postTask, pictureTask).join();

            try {
                Page<UserVO> userVOPage = userTask.get();
                Page<PostVO> postVOPage = postTask.get();
                Page<Picture> picturePage = pictureTask.get();

                SearchVO searchVO = new SearchVO();
                searchVO.setUserList(userVOPage.getRecords());
                searchVO.setPostList(postVOPage.getRecords());
                searchVO.setPictureList(picturePage.getRecords());
                return ResultUtils.success(searchVO);
            } catch (Exception e) {
                log.error("查询异常", e);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
            }
        }else{
            SearchVO searchVO = new SearchVO();
            switch (searchTypeEnum) {
                case POST:
                    PostQueryRequest postQueryRequest = new PostQueryRequest();
                    postQueryRequest.setSearchText(searchText);
                    Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
                    searchVO.setPostList(postVOPage.getRecords());
                    break;
                case USER:
                    UserQueryRequest userQueryRequest = new UserQueryRequest();
                    userQueryRequest.setUserName(searchText);
                    Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
                    searchVO.setUserList(userVOPage.getRecords());
                    break;
                case PICTURE:
                    Page<Picture> picturePage = pictureService.searchPicture(searchText, 1, 10);
                    searchVO.setPictureList(picturePage.getRecords());
                    break;
                default:
            }
            return ResultUtils.success(searchVO);
        }
    }
}
