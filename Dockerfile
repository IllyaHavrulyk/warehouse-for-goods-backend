FROM adoptopenjdk/openjdk11:ubi
EXPOSE 8080
WORKDIR /app
COPY target/warehouse-for-goods-backend-0.0.1-SNAPSHOT.jar .
ENTRYPOINT [ "java", "-jar", "warehouse-for-goods-backend-0.0.1-SNAPSHOT.jar" ]