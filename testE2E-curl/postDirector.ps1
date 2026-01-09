curl.exe -X 'POST' `
'http://localhost:8080/api/person' `
-H 'accept: */*' `
-H 'Content-Type: application/json' `
-d '{
  \"name\": \"James Cameron\",
  \"birthdate\": \"1954-08-16\"
}'