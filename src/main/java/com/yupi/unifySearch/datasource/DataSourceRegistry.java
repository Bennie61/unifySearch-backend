package com.yupi.unifySearch.datasource;


import com.yupi.unifySearch.model.enums.SearchTypeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册器类 DataSourceRegistry
 * 注册器模式是一种基础常见的设计模式，它的主要意思是把多个类的实例注册到一个注册器类中去，然后需要哪个类，由这个注册器类统一调取。
 * 就是把很多类的实例，起个别名，然后按照key，value的形式放在注册器类里，以便之后统一调用。
 */
@Component
public class DataSourceRegistry {
    @Resource
    private UserDataSource userDataSource;
    @Resource
    private PostDataSource postDataSource;
    @Resource
    private PictureDataSource pictureDataSource;

    private Map<String, DataSource<?>> typeDataSourceMap;

    @PostConstruct
    public void doInit(){
        typeDataSourceMap = new HashMap(){{
            put(SearchTypeEnum.POST.getValue(), postDataSource);
            put(SearchTypeEnum.USER.getValue(), userDataSource);
            put(SearchTypeEnum.PICTURE.getValue(), pictureDataSource);
        }};
    }
    public DataSource getDataSourceByType(String type){
        if (typeDataSourceMap == null){
            return null;
        }
        return typeDataSourceMap.get(type);
    }
}
