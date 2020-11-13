FROM openjdk:8
RUN apt-get update -y
RUN apt-get upgrade -y
COPY manipulatedatabase.java .
COPY mysql-connector-java-8.0.21.jar .
EXPOSE 3306
RUN javac manipulatedatabase.java
CMD ["java","-cp",".:/mysql-connector-java-8.0.21.jar","manipulatedatabase"]
