# 项目介绍

基于Vue3 + Spring Boot + Elastic Stack 的一站式聚合搜索平台，用户可以在同一页面集中搜索不同来源、不同内容的数据，提升用户的 检索效率和搜索体验。

# 项目内容

对于个人用户来说，使用该聚合搜素平台可以在同一个页面中搜索出不同的来源、内容，提升搜索效率和搜索体验。

对于企业来说，当企业需要扩展更多搜索项目时，无需针对每个搜索项目单独开发搜索功能，可以直接将各个项目的数据接入搜索中台，提高开发效率、降低维护成本。

后端项目地址：

前端项目地址：

搜文章：

Figure/result1.png

搜图片：

![result2](F:\javalearning\Project\springboot-init-master\Figure\result2.png)

搜用户：

![result3](F:\javalearning\Project\springboot-init-master\Figure\result3.png)

## 项目架构图

![architecture](F:\javalearning\Project\architecture.png)

## 技术栈

### 前端

- Vue3
- Ant Design Vue
- Lodash



### 后端

- Spring Boot
- MySQL
- Elasticsearch 搜索引擎
- 数据抓取
  - 离线
  - 实时
- 数据同步（4中同步方式）
  - 定时
  - 双写
  - Logstash
  - Canal
- JMeter压力测试

项目正在完善中...

等待部署

最后感谢 [鱼皮](https://github.com/liyupi) 大佬的分享。



# 项目环境

1. jdk8
2. elasticsearch-7.17.11
3. kibana7.17.11
4. logstash7.17.11



### Elasticsearch 搜索引擎

1）修改 `application.yml` 的 Elasticsearch 配置为你自己的：

```yml
spring:
  elasticsearch:
    uris: http://localhost:9200
    username: root
    password: 123456
```

2）复制 `sql/post_es_mapping.json` 文件中的内容，通过调用 Elasticsearch 的接口或者 Kibana Dev Tools 来创建索引（相当于数据库建表）

```
PUT post_v1
{
 参数见 sql/post_es_mapping.json 文件
}
```

这步不会操作的话需要补充下 Elasticsearch 的知识，或者自行百度一下~

3）开启同步任务，将数据库的帖子同步到 Elasticsearch

找到 job 目录下的 `FullSyncPostToEs` 和 `IncSyncPostToEs` 文件，取消掉 `@Component` 注解的注释，再次执行程序即可触发同步：

```java
// todo 取消注释开启任务
//@Component
```
