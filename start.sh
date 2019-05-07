cd memopro-frontend
npm install
npm run build
rm -rf ../memopro-backend/src/main/resources/static
mkdir ../memopro-backend/src/main/resources/static
cp -r ./dist/* ../memopro-backend/src/main/resources/static
cd ../memopro-backend
mvn install
jps | grep memopro-backend | awk '{print $1}' | xargs kill -9
nohup java -jar ./target/memopro-backend-1.0-SNAPSHOT.jar &