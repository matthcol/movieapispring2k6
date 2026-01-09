curl.exe -X 'PUT' `
'http://localhost:8080/api/movie' `
-H 'accept: */*' `
-H 'Content-Type: application/json' `
-d '{\"duration\":177,\"genres\":[\"Action\",\"Sci-fi\",\"Adventure\",\"Fantastic\"],\"movieId\":1,\"releaseYear\":2009,\"title\":\"Avatar\"}'
