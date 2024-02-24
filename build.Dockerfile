FROM maven:3.8-openjdk-17-slim
WORKDIR /home/app

ENTRYPOINT mvn spring-boot:run \
                -Dmaven.repo.local=/root/.m2 \
                -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000"
