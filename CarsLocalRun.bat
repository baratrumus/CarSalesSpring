call mvn clean package -Ppostgres
call java -jar target/dependency/webapp-runner.jar target/*.war --port 8082
