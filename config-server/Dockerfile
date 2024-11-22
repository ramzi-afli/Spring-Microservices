##############################################################################

#Docker  file for config server
#author AFLI RAMZI
##############################################################################

FROM openjdk:11


#storing the jar file into  variable
ARG JAR_FILE=target/microservices-0.0.1-SNAPSHOT.jar


#adding the jar  file to  app.jar
ADD  $JAR_FILE app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
