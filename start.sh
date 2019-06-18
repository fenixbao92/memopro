jps | grep memopro-backend | awk '{print $1}' | xargs kill -9
cd ./memopro-backend
mvn install
nohup java -jar ./target/memopro-backend-1.0-SNAPSHOT.jar &
