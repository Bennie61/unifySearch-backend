package com.yupi.unifySearch.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.unifySearch.model.dto.user.UserQueryRequest;
import com.yupi.unifySearch.model.vo.UserVO;
import com.yupi.unifySearch.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 用户搜索适配器
 * 适配器模式
 */
@Service
@Slf4j
public class UserDataSource implements DataSource<UserVO> {
    @Resource
    private UserService userService;

    public Page<UserVO> doSearch(String searchText, long pageNum, long pageSize){
        // 想办法兼容接口，把参数做一个转换
        // 假如 listUserVOByPage是其他项目提供的一个微服务（一个接口），但是人家的接口调用的参数和自己系统预期的参数不一致，
        // 就需要编写一个适配器去转换。
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setCurrent(pageNum);
        userQueryRequest.setPageSize(pageSize);
        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
        return userVOPage;
    }
}
