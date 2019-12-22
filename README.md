
![](https://github.com/0xcaffebabe/seeaw4/workflows/Java%20CI/badge.svg)

![](https://img.shields.io/badge/JDK-1.8-green)

# SEEAW4

- see anywhere 4.0 version

新一代跨平台远程控制系统

## 背景

该项目起源我初学编程时的一个小demo

当时只是使用JAVA简单的IO 配合一些android编程，实现了一个可以从安卓手机远程查看电脑桌面的小程序
后来随着技术的不断学习，先后几次对项目进行了重写，也实现了诸如远程shell等功能

从单文件到丰富类层次结构再到实践设计模式，从BIO->NIO->再到netty
这期间接触到了一些工程化思想/方法，也积极地将这些思想/方法应用到实际开发中

从另一方面来说，这个项目也算是见证了我的学习历程

## 导航

之前的seeaw旧版本

- [seeaw](https://github.com/0xcaffebabe/seeaw)
- [seeaw3](https://github.com/0xcaffebabe/seeaw3-server-control)

## 模块

- common

一些通用类库以及一些接口定义

- server

服务端模块，负责处理客户端连接并能承受一定并发量，能独立打包运行部署

- client

客户端模块，连接服务端能独立运行并且可以与其他客户端连接通信

- desktop

构建一个跨平台(win,linux,osx)的桌面客户端程序，可以独立运行（该项目必须在JDK1.8下构建，如果使用的是openjdk，需要安装openjfx依赖）

- android

构建一个安卓客户端，功能同desktop

- web

提供一个web接口，能独立打包部署，可以通过该接口访问server

- terminal

可以集成到客户端，负责一些平台相关的远程控制功能

## 使用

执行构建

```shell script
mvn package
```

- 启动server

```shell script
java -jar ./seeaw4_server/target/seeaw4_server-1.0-SNAPSHOT-jar-with-dependencies.jar
```












