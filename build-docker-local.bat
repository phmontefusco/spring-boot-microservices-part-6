cd api-gateway
call mvn clean package -Dmaven.test.skip
call .\build-docker-local.bat
cd..
cd discovery-server
call mvn clean package -Dmaven.test.skip
call .\build-docker-local.bat
cd..
