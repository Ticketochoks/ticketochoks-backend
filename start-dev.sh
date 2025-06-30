#!/bin/bash
export DB_URL=jdbc:postgresql://<host>:<port>/<database>
export DB_USERNAME=
export DB_PASSWORD=
export SPRING_PROFILES_ACTIVE=dev

./gradlew bootRun
