##############################################################################

#Docker  file for authorization server
##############################################################################
FROM openjdk:11

#getting the jar file  inside  local variable
ARG  JAR_FILE=target/authorization-server-0.0.1-SNAPSHOT.jar

#adding the jar file to app.jar
ADD  $JAR_FILE app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

