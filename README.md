# UnifySearch聚合搜索项目介绍
> 作者：[Bennie](https://github.com/Bennie61)
* 基于Spring Boot + Vue + Elastic Stack的一站式信息搜索平台，用户可在一个页面集中搜索出不同来源、不同类型的内容，提升搜索体验。当企业中有多个项目的数据需要被搜索时，无需针对每个项目单独开发搜索功能，可以直接将数据接入搜索中台，提升开发效率。
## 01 技术栈
* Spring Boot 2.7 框架
* SpringBoot-Template 项目模版
* Knife4j 项目的Swagger接口文档和接口测试工具
* MySQL 数据库
* Elastic Stack
  * ElasticSearch 搜索引擎
  * Logstash 数据管道
  * Kibana 数据可视化
* 数据抓取
  * 离线和实时抓取
  * Jsoup 和 HttpClient 库
___
## 02 为什么要使用一站式信息搜索或聚合搜索？
现有的聚合搜索平台案例：
* [今日热榜](https://tophub.today/) 
* 分析：该页面含有多种不同来源的数据，若为每一种来源都设计一个请求接口，在页面加载时会同时发起多个请求，会导致请求阻塞、页面卡顿。此外，浏览器还存在着资源请求并发数的限制。
查看该网站的后台请求，发现页面仅向后台发送一个统一的请求，用一个接口请求完所有来源的数据。
* GitHub 搜索框
* 业务场景：一个搜索框同时查询多种类型的数据，默认展示某一tab的数据列表，此外同时也要查询其他类型的信息，比如其他类型数据的总数，反馈给用户。
![img_1.png](Note/images/img_1.png)
* 一种实现方式：在用户点击某个左侧 tab 的时候，只调用这个 tab 的接口。（https://www.code-nav.cn/search/resource?searchText=java）
* 该方式无法实现上述业务场景，且存在问题：
1）请求不同接口的参数可能不一致，增加前后端沟通成本；
2）前端页面编写调用多个接口的代码，代码存在冗余重复。

>**小结：聚合搜索利用统一的接口，通过不同的参数去区分查询的数据源，从而实现一站式集中搜索。**
___
## 03 设计模式如何优化系统代码？
1. 门面模式如何隐藏系统复杂性，提供更简单易用、更高层的接口？
* 项目实践案例:
  * 前提：Service层编写了 UserService、PostService以及PictureService，以及对应的具体实现类，其中，分别实现了 listUserVOByPage()、listPostVOByPage()、searchPicture()方法。
  * 原始做法是直接在Controller中依次调用或并行执行上面三个方法，但随着数据源类型的增多，每次新增一种类型的数据源，都要Controller中新增代码，会导致大量的查询逻辑会堆积在Controller代码中，
  * 因此，采用了门面模式（也称外观模式），设计了一个高层次的门面类SearchFacade，用来封装数据搜索的底层实现，精简Controller代码的同时也让高层模块可以更加容易使用这些子方法，有利于日后系统的拓展。
* 具体做法：
  * 将UserService、PostService和PictureService的对象作为门面类的成员属性，并使用`@Resource`注解（依赖注入）为门面类提供它所依赖的其他类的实例对象，
  * 门面类SearchFacade中实现了searchAll()方法，提供给高层的Controller来调用，该方法中分别调用成员属性各自具体实现类的方法，如listUserVOByPage()。
>**小结：门面类SearchFacade在这里充当了一个中间层，将Controller层与子接口解耦，使得系统更易于扩展和维护。**

2. 在实践中如何使用注册器模式来管理组件或服务？
* 注册器模式基本思想：
  * **把多个类的实例注册到一个注册器类中去，然后需要哪个类，就由这个注册器类统一调取。**
  * 给很多类的实例，起个别名，然后按照key，value的形式放在注册器类里，以便之后统一调用
* 项目实践案例：
  * 由于需要根据type类型去调用不同的数据源对象，导致代码中存在大量if-else分支结构。
  * 存在的问题：
(1) 可读性差: 大量的 if-else 分支会使代码变得冗长，降低可读性；
(2) 扩展困难: 如果需要添加新的功能或条件，就需要修改现有的分支结构;
(3) 耦合度高。
  * 具体做法：编写一个注册器类`DataSourceRegistry`，其中维护一个`typeDataSourceMap`对象成员，其类型为`Map<String, DataSource<?>>`，在`doInit()`方法新建HashMap来存储类实例名称和类实例，代码如下所示。
```java
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
        if (typeDataSourceMap == null) return null;
        return typeDataSourceMap.get(type);
    }
}
```
  * 门面类获取某类数据源对象的调用方式：`DataSource<?> dataSource = dataSourceRegistry.getDataSourceByType(type);`，通过该方式可以替代if-else语句，降低耦合，减少圈复杂度。
  * UML类图如下：
![img.png](Note/images/img.png)