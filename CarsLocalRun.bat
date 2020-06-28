call mvn -B -s settings.xml -DskipTests=true clean package -Ppostgres
call java -cp target/dependency/* webapp.runner.launch.Main target/*.war --port 8082  --enable-naming
