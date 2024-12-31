# Java 21 이미지를 사용하여 빌드
FROM openjdk:21-jdk-slim AS build

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 JAR 파일을 컨테이너에 복사
COPY ./build/libs/LuckyBikiDeffenceAPIServer-0.0.1-SNAPSHOT.jar /app/LuckyBikiDeffenceAPIServer.jar

# Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "LuckyBikiDeffenceAPIServer.jar"]

# 포트 8080을 오픈
EXPOSE 8080

#ARG JAR_FILE=*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]