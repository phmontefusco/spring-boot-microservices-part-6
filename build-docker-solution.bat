cd api-gateway
call mvn clean package -Dmaven.test.skip
call .\build-docker.bat
cd..
cd discovery-server
call mvn clean package -Dmaven.test.skip
call .\build-docker.bat
cd..
cd inventory-service
call mvn clean package -Dmaven.test.skip
call .\build-docker.bat
cd..
cd order-service
call mvn clean package -Dmaven.test.skip
call .\build-docker.bat
cd..
cd product-service
call mvn clean package -Dmaven.test.skip
call .\build-docker.bat
cd..
cd notification-service
call mvn clean package -Dmaven.test.skip
call .\build-docker.bat
cd..
