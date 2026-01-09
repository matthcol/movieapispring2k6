param(
    [Parameter(Mandatory=$true)]
    [string]$profiles = 'h2'
)

$env:JAVA_HOME="$env:USERPROFILE\Documents\JAVA\jdk-21"
echo "$env:JAVA_HOME"
$env:PATH="$env:PATH;$env:USERPROFILE\Documents\JAVA\apache-maven-3.9.12\bin"
echo "$env:PATH"
$env:SPRING_PROFILES_ACTIVE="jpa,$profiles"
echo "$env:SPRING_PROFILES_ACTIVE"

mvn.cmd spring-boot:run