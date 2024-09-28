FROM openjdk:17

RUN mkdir /app
WORKDIR /app/
ADD ./cafe_shop_microservices-1.0.jar /app/
ADD ./application.properties /app/

CMD java -jar cafe_shop_microservices-1.0.jar

EXPOSE 8080