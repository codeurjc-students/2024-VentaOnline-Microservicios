##FRONTEND
#selection of the image base
FROM node:18-alpine AS builder

#definition of the work directory in /project to execute commands
WORKDIR /project

#files from frontend are copied on work directory
COPY frontend/ .

#dependencies app need are installed
RUN npm install

#files to production are generated in the specific route "new"
#RUN npm run build -- --base=href=/new/

##BACKEND

#addition of java image
FROM maven:3.9.0-eclipse-temurin-19 as backends

#definition of the work directory in /project to execute commands
WORKDIR /project

#project dependencies are copied on the work directory
COPY pom.xml /project/

#project dependencies are downloaded
RUN mvn clean verify --fall-never

#backend project code is copied on the work directory
COPY backend/src /project/src

# files are copied
# origin --> dist/frontend
# destiny --> src/main/resources/static/(new)
COPY --from=builder /project/dist/frontend /project/src/main/resources/static

# app building once the code is compiled
RUN mvn clean package -o -DskipTests=true

## image for the app container
FROM eclipse-temurin:19-jdk

# definition of the work directory where JAR file finds
WORKDIR /usr/src/app/

# JAR file of compilation container is copied on JAR work directory
COPY --from=backend /project/target/*.jar /usr/src/app/

# port exposes container
EXPOSE 8443

# command to execute to do docker run
ENTRYPOINT ["java","jar",""]