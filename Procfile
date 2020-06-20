#  Web-Runner command to start web application

#web java $JAVA_OPTS -jar target/*.jar --spring.datasource.url=${JDBC_DATABASE_URL} -Dserver.port=$PORT
#web: java -jar target/forum-1.jar --spring.config.location=heroku.properties
#web: java  -jar target/carSalesSpring.jar --spring.datasource.url=${JDBC_DATABASE_URL} -Dserver.port=$PORT
#web: java -cp target/dependency/* webapp.runner.launch.Main target/*.war --port $PORT

web: java $JAVA_OPTS -jar target/dependency/webapp-runner.jar target/*.war --port $PORT


# Run liquibase, using maven wrapper
# release: ./mvnw -Dliquibase.changeLogFile=src/main/resources/db/master.xml -Dliquibase.url=$JDBC_DATABASE_URL -Dliquibase.promptOnNonLocalDatabase=false liquibase:update

release: sh ./mvnw liquibase:update
