#!/bin/bash

# Script for executing the CLI with Gradle
# Usage: ./run-cli.sh command --version=1.0 file.ps config.json

# Extract the command (first argument)
COMMAND=$1
shift

# Extract the version option (e.g., --version=1.0)
OPTIONS=""
while [[ $1 == --* ]]; do
  OPTIONS="$OPTIONS $1"
  shift
done

# The next argument should be the filename
FILENAME=$1
shift

# The next argument should be the optional config
CONFIG=$1
if [[ $CONFIG == *.* ]]; then
  shift
else
  CONFIG=""
fi

# The remaining arguments are the subcommand and its arguments
ARGS="$OPTIONS $FILENAME $CONFIG $COMMAND"

# Run the Gradle command with the assembled arguments
./gradlew cli:run --args="$ARGS"