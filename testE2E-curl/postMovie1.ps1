$json = @'
{
  \"title\": \"Avatar\",
  \"releaseYear\": 2009,
  \"duration\": 162,
  \"genres\": [
    \"Action\", \"Sci-fi\", \"Adventure\"
  ]
}
'@

curl.exe -X 'POST' `
'http://localhost:8080/api/movie' `
-H 'accept: */*' `
-H 'Content-Type: application/json' `
-d $json