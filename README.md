# Strive

How to start the Strive Backend Application
---
1. Install homebrew (https://brew.sh/)
1. Install postgres with homebrew `brew install postgres`
1. Create postgres user 
	2. `psql postgres`
	2. `CREATE ROLE postgres WITH LOGIN PASSWORD 'postgres';`
1. Run `mvn clean install` to build your application
1. Run schema migration `java -jar target/app-1.0.SNAPSHOT.jar db migrate config.yml --migrations migrations.xml`
1. Insert questions `psql strive < questions.sql`
1. Start application with `java -jar target/app-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`
