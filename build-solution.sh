cd api-gateway
mvn clean package -Dmaven.test.skip
cd ..
mvn clean package -Dmaven.test.skip
cd ..
cd inventory-service
mvn clean package -Dmaven.test.skip
cd ..
cd order-service
mvn clean package -Dmaven.test.skip
cd ..
cd product-service
mvn clean package -Dmaven.test.skip
cd ..
cd notification-service
mvn clean package -Dmaven.test.skip
cd ..
