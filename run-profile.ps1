param(
    [Parameter(Mandatory=$true)]
    [string]$profiles = 'h2'
)

$env:JAVA_HOME='C:\Users\nruffenach\Documents\JAVA\jdk-21'
$env:PATH="$env:PATH;C:\Users\nruffenach\Documents\JAVA\apache-maven-3.9.12\bin"
$env:SPRING_PROFILES_ACTIVE="jpa,$profiles"

mvn.cmd spring-boot:run
