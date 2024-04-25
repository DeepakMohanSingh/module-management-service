FROM openjdk
ADD target/module-0.0.1-SNAPSHOT.jar module-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "module-0.0.1-SNAPSHOT.jar"]