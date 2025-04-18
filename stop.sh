#!/bin/bash

APP_NAME="qrest-0.0.1-SNAPSHOT.jar"

PID=$(ps aux | grep $APP_NAME | grep -v grep | awk '{print $2}')

if [ -z "$PID" ]; then
  echo "⚠️  Application is not running."
else
  echo "🛑 Stopping application with PID: $PID"
  kill $PID
  echo "✅ Application stopped."
fi
