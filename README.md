# Blog By Vuejs SpringBoot Docker

## Springboot application.yml
```yml
server:
  port: 9000

spring:
  jpa:
    database-platform: com.enigmabridge.hibernate.dialect.SQLiteDialect
    hibernate:
      ddl-auto: update
    show-sql: false
  resources:
    static-locations: classpath:/static/,classpath:/templates/
  datasource:
    driver-class-name: org.sqlite.JDBC
    url: jdbc:sqlite:/db.sqlite3
    username:
    password:
```

## Build the image

```Dockerfile
FROM openjdk:13
MAINTAINER "ahri"<ahriknow@ahriknow.cn>
VOLUME /tmp
ADD blog-0.0.1-SNAPSHOT.jar /blog.jar
ADD db.sqlite3 /db.sqlite3
EXPOSE 9000
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/blog.jar"]
```

## Run a container

```bash
docker container run --name blog -p 80:9000 -d ahriknow/springblog:v1
```

- `--name blog` 容器名为 blog
- `-p 80:9000` 将容器 9000 端口映射到宿主机 80 端口
- `-d` 后台运行
- `ahriknow/springblog:v1` 镜像

## Technology stack
- [Vuejs](https://cn.vuejs.org/)
- [Element-ui](https://element.eleme.cn/#/zh-CN)
- [Springboot](https://spring.io/projects/spring-boot/)

## Auth ahri 20200301
