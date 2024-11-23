#building auth-server  microservice
mvn -f ./authorization-server/ clean  install -DskipTests=true 


#building gatewy-service  microservice
mvn -f ./gateway/ clean install -DskipTests=true 

#building config-service  microservice
mvn -f ./config-server/ clean install -DskipTests=true 


#building discovery-service  microservice
mvn -f ./discovery/ clean install -DskipTests=true 
