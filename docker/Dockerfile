FROM openjdk:13
MAINTAINER "ahri"<ahriknow@ahriknow.cn>
VOLUME /tmp
ADD blog-20200304.jar /blog.jar
ADD db.sqlite3 /db.sqlite3
EXPOSE 9000
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/blog.jar"]
