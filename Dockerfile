
FROM adoptopenjdk/openjdk11:alpine-jre
COPY build/libs/notes-0.0.1-SNAPSHOT.jar mediscreen-notes.jar
ENTRYPOINT ["java", "-jar", "mediscreen-notes.jar"]