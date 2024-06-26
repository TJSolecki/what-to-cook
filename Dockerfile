FROM gradle:jdk21-alpine

RUN mkdir /home/gradle/buildWorkspace

COPY . /home/gradle/buildWorkspace

WORKDIR /home/gradle/buildWorkspace

RUN gradle build --no-daemon

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/home/gradle/buildWorkspace/build/libs/WhatToCook-0.0.1-SNAPSHOT.jar"]
