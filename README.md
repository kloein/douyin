# 1、项目简介
本项目是基于微服务架构实现的简单抖音后端，提供了视频服务与用户服务，
其中包括视频上传，视频推流，点赞与评论，用户注册与登录，用户间关注功能

# 2、如何运行项目
各配置详细详细请参考各配置文件样例
## 2.1、mysql
在8.0以上版本的mysql中，执行下列语句
```SQL
CREATE DATABASE `douyin`;

USE `douyin`;

CREATE TABLE `t_user`(
	`id` bigint(20) PRIMARY KEY AUTO_INCREMENT COMMENT '编号',
	`username` VARCHAR(32) NOT NULL COMMENT '用户名',
	`password` VARCHAR(255) NOT NULL COMMENT 'MD5加密后的用户密码',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_deleted` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)'
);

CREATE TABLE `t_like`(
	`id` bigint(20) PRIMARY KEY AUTO_INCREMENT COMMENT '编号',
	`uid` bigint(20) NOT NULL COMMENT '用户id',
	`vid` bigint(20) NOT NULL COMMENT '视频id',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `is_deleted` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)'
);

CREATE TABLE `t_follow`(
	`id` bigint(20) PRIMARY KEY AUTO_INCREMENT COMMENT '编号',
	`uid` bigint(20) NOT NULL COMMENT '被关注者id',
	`follower_id` bigint(20) NOT NULL COMMENT '关注者id',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `is_deleted` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)'
);

CREATE TABLE `t_comment`(
	`id` bigint(20) PRIMARY KEY AUTO_INCREMENT COMMENT '编号',
	`uid` bigint(20) NOT NULL COMMENT '评论者id',
	`vid` bigint(20) NOT NULL COMMENT '视频id',
	`comment_text` VARCHAR(255) NOT NULL COMMENT '评论详情',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`is_deleted` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)'
);

CREATE TABLE `t_video`(
	`id` bigint(20) PRIMARY KEY AUTO_INCREMENT COMMENT '编号',
	`uid` bigint(20) NOT NULL COMMENT '发布者id',
	`play_url` VARCHAR(255) NOT NULL COMMENT '视频地址',
	`cover_url` VARCHAR(255) NOT NULL COMMENT '封面地址',
	`title` VARCHAR(255) NOT NULL COMMENT '视频标题',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`is_deleted` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)'
);
```
## 2.2、redis
请启动redis服务
## 2.3、nacos
本项目使用nacos作为微服务的注册中心，使用版本为1.1.4
## 2.4、阿里云oss
本项目采用阿里云oss为视频以及封面存储提供服务，请配置你的阿里云oss
## 2.5、客户端
客户端以及接口信息请见[抖音项目（青训营）](https://bytedance.feishu.cn/docx/doxcnbgkMy2J0Y3E6ihqrvtHXPg)

# 3、使用技术
## 3.1、简介
* 语言：基于java开发
* 框架：SpringBoot、SpringMVC、MybatisPlus、Feign
* 中间件：redis，nacos，SpringCloud GateWay
* 数据库：MySQL
* 开发工具：idea，git
* 其他：阿里云oss、ffmpeg、docker

## 3.2、部分场景举例
* 使用nacos以及Feign整合为微服务提供调度
* 使用多线程并发查询视频、用户等信息
* 使用redis为部分热点数据作缓存
* 使用ffmpeg截取视频封面
* 使用阿里云oss存储视频以及封面，提供公网访问
* 由于客户端提供API限制，不做MQ处理，使用MQ的版本请见[抖音项目第一版](https://github.com/kloein/kdoushen)


# 4、致谢
[字节跳动后端青训营](https://youthcamp.bytedance.com/)