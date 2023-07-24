cd api-gateway
call mvn clean package -Dmaven.test.skip
cd..
cd discovery-server
call mvn clean package -Dmaven.test.skip
cd..
cd inventory-service
call mvn clean package -Dmaven.test.skip
cd..
cd order-service
call mvn clean package -Dmaven.test.skip
cd..
cd product-service
call mvn clean package -Dmaven.test.skip
cd..
cd notification-service
call mvn clean package -Dmaven.test.skip
cd..
