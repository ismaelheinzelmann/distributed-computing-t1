# Cloud File Storage System
Distributed cloud file storage system using web-services (micro-services architecture), a didactic Api Gateway, a didactic Load Balancer, and Kafka for message queue.

# Dependencies
Docker, Docker Compose, Curl, Java 17, maven.

# Run the project
In the root of the project, run `bash build`. Wait for the compose to end, then run `bash config` and wait for the responses. Finally, open your browser at `localhost:3000`.
To stop the application run `docker compose stop`, and to destroy the application `docker compose down`, both in the root of the application. 

