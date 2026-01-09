$json = @'
{
  \"title\": \"Avatar: The Way of Water\",
  \"releaseYear\": 2022,
  \"duration\": 192,
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