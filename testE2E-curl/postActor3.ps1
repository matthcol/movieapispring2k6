curl.exe -X 'POST' `
'http://localhost:8080/api/person' `
-H 'accept: */*' `
-H 'Content-Type: application/json' `
-d '{
  \"name\": \"Sigourney Weaver\",
  \"birthdate\": \"1949-10-08\"
}'