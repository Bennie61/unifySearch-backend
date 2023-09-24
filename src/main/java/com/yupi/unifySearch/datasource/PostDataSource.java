package com.yupi.unifySearch.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.unifySearch.model.dto.post.PostQueryRequest;
import com.yupi.unifySearch.model.entity.Post;
import com.yupi.unifySearch.model.vo.PostVO;
import com.yupi.unifySearch.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子搜索适配器
 *
 */
@Service
@Slf4j
public class PostDataSource implements DataSource<PostVO> {
    @Resource
    private PostService postService;

    @Override
    public Page<PostVO> doSearch(String searchText, long pageNum, long pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setCurrent(pageNum);
        postQueryRequest.setPageSize(pageSize);
        // HttpServletRequest request
        ServletRequestAttributes servletRequestAttributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        /**
         * 原始版本(未使用ES)
         * Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
         * return postVOPage;
         */

        /**
         * 使用ES查询
         */
        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
        return postService.getPostVOPage(postPage,request);
    }
}




