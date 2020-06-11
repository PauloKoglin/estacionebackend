# Estacione Server-App #

### Pré-requisitos ###
* JDK 8

### Como executar ###

* mvnw.cmd clean package
* java -jar target/eventar-0.1.0.jar
* aplicação executando na porta :8080! 

### Como executar em debug###
* java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n  -jar target/eventar-0.1.0.jar# estacionebackend
