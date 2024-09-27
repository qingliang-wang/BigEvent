# 实战篇

 

![image-20240920163934928](G:\study\2BigEvent\Code\PIC\image-20240920163934928.png)

## 1、环境搭建

![image-20240920164102381](G:\study\2BigEvent\Code\PIC\image-20240920164102381.png)

引入依赖

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.1.5</version>
    <relativePath/> <!-- lookup parent from repository -->
  </parent>

  <groupId>com.itheima</groupId>
  <artifactId>big-event</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>big-event</name>
  <url>http://maven.apache.org</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!--mybatis 依赖-->
    <dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-starter</artifactId>
      <version>3.0.0</version>
    </dependency>

    <!-- mysql 驱动-->
    <dependency>
      <groupId>com.mysql</groupId>
      <artifactId>mysql-connector-j</artifactId>
    </dependency>

  </dependencies>
</project>

```

 配置文件 application.yml

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/big_event
    username: root
    password: root
```

创建包结构，并准备实体类



### 开发流程

![image-20240920172639549](G:\study\2BigEvent\Code\PIC\image-20240920172639549.png)

结果实体类

```java
package com.itheima.pojo;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//统一响应结果
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;//业务状态码  0-成功  1-失败
    private String message;//提示信息
    private T data;//响应数据

    //快速返回操作成功响应结果(带响应数据)
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "操作成功", data);
    }

    //快速返回操作成功响应结果
    public static Result success() {
        return new Result(0, "操作成功", null);
    }

    public static Result error(String message) {
        return new Result(1, message, null);
    }
}

```

## 注册开发过程 ：

![image-20240920221050678](G:\study\2BigEvent\Code\PIC\image-20240920221050678.png)

> [!IMPORTANT]
>
> 出现错误 ，我们来对比一下看看错在哪里 
>
> ```java
> // 添加
> @Insert("insert into user(username,password,create_time,update_time)"+
> "values (#{username}),#{password},now(),now()")
> void add(String username, String password);
> ```

```java
    @Insert("insert into user(username, password, create_time, update_time) " +
            "values(#{username},#{md5String},now(),now())")
    void add(final String username, final String md5String);
```



### Spring validation 

参数校验框架

![image-20240920224651494](G:\study\2BigEvent\Code\PIC\image-20240920224651494.png)



### 全局异常处理器

在 Spring Boot 中，全局异常处理器可以用来捕获和处理应用程序中未处理的异常，并统一返回一个自定义的响应格式。你可以通过使用 `@ControllerAdvice` 和 `@ExceptionHandler` 注解来实现全局异常处理器。

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e)
    {
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage())?e.getMessage():"操作失败!");

    }
}

```

## 登录

![image-20240920230507308](G:\study\2BigEvent\Code\PIC\image-20240920230507308.png)

登录认证

![image-20240920231418135](G:\study\2BigEvent\Code\PIC\image-20240920231418135.png)

JWT 

JWT（JSON Web Token）是一种用于安全传递信息的开放标准，常用于身份验证和授权。它的结构由三个部分组成：**Header**、**Payload** 和 **Signature**。在 Spring Boot 中，JWT 常用于用户身份验证，通过生成、解析和验证令牌来保护 API。

生成JWT 令牌

```java
public void testGen(){
        // 生成jwt的代码
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","张三");
        // 生成 JWT 的代码
        // 设置过期时间为 1 小时
        Date expiresAt = new Date(System.currentTimeMillis() + 1000 * 60 * 60);

        // 生成 JWT 令牌
        String token = JWT.create()
                .withClaim("user", claims) // 放入自定义数据
                .withExpiresAt(expiresAt)  // 设置过期时间
                .sign(Algorithm.HMAC256("itheima")); // 使用 HMAC256 进行签名
         
        System.out.println(token);

    }
}

```

 验证JWT令牌

```java
@Test
    public void testParse() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3MjY4NTEwMzV9." +
                "E20ciz2wb18UrHso2lYY0RaawS4ouv0smFV5lRwvxII";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("itheima")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }
```





### 注册拦截器

![image-20240921002436523](G:\study\2BigEvent\Code\PIC\image-20240921002436523.png)

先书写拦截器，再配置进去







## 获取用户详细信息

![image-20240921003431081](G:\study\2BigEvent\Code\PIC\image-20240921003431081.png)



优化 ThreadLocal 

提供线程局部变量 用来存取数据





## 更新用户基本信息

![image-20240921012243841](G:\study\2BigEvent\Code\PIC\image-20240921012243841.png)

# VUE 

是一款构建用户界面的渐进式的JavaScript 框架

![image-20240923233901031](G:\study\2BigEvent\Code\PIC\image-20240923233901031.png)



## API 风格

选项是API

组合式api

![image-20240925153519298](G:\study\2BigEvent\Code\PIC\image-20240925153519298.png)



