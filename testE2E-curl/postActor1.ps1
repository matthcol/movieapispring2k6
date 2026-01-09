curl.exe -X 'POST' `
'http://localhost:8080/api/person' `
-H 'accept: */*' `
-H 'Content-Type: application/json' `
-d '{
  \"name\": \"Sam Worthington\",
  \"birthdate\": \"1976-08-02\"
}'