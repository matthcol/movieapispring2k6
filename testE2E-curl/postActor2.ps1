curl.exe -X 'POST' `
'http://localhost:8080/api/person' `
-H 'accept: */*' `
-H 'Content-Type: application/json' `
-d '{
  \"name\": \"Zoe Saldaña\",
  \"birthdate\": \"1978-06-19\"
}'