call mvn -B -s settings.xml -DskipTests=true clean package -Ppostgres
call java -cp target/dependency/* webapp.runner.launch.Main target/*.war  --enable-naming
