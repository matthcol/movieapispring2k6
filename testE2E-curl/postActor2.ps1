curl.exe -X 'POST' `
'http://localhost:8080/api/person' `
-H 'accept: */*' `
-H 'Content-Type: application/json; charset=utf-8' `
--data-raw '{
  \"name\": \"Zoe Saldaña\",
  \"birthdate\": \"1978-06-19\"
}'