#!/bin/bash

# Script para ejecutar el CLI con Gradle
# Uso: ./run-cli.sh.sh command --version=1.0 file.ps

COMMAND=$1
shift

OPTIONS=$@

./gradlew cli:run --args="$COMMAND $OPTIONS"