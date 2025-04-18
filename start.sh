#!/bin/bash
echo "Starting Spring Boot application..."
nohup java -jar target/qrest-0.0.1-SNAPSHOT.jar > app.log 2>&1 &
echo "Application started. Logs: app.log"
