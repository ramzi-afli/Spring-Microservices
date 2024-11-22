#building auth-server  microservice
mvn -f ./authorization-server/ clean  install -DskipTests=true && docker build -t auth-service:$(date +%s) ./authorization-server/


#building gatewy-service  microservice
mvn -f ./gateway/ clean install -DskipTests=true && docker build -t gateway-service:$(date +%s) ./gateway/

#building config-service  microservice
mvn -f ./config-server/ clean install -DskipTests=true && docker build -config-serve:$(date +%s) ./config-serve/


#building discovery-service  microservice
mvn -f ./discovery/ clean install -DskipTests=true && docker build -discovery-serve:$(date +%s) ./discovery/
