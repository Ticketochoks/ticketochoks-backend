$env:DB_URL="jdbc:postgresql://<host>:<port>/<database>"
$env:DB_USERNAME=""
$env:DB_PASSWORD=""
$env:SPRING_PROFILES_ACTIVE="dev"

./gradlew.bat bootRun
