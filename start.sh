cd memopro-frontend
npm install
npm run build
mkdir ../memopro-backend/src/main/resource/static
cp ./dist/* ../memopro-backend/src/main/resource/static
cd ../memopro-backend
mvn install
nohup java -jar ./target/memopro-backend-1.0-SNAPSHOT.jar &