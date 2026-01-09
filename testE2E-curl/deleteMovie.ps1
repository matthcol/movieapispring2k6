param(
    [Parameter(Mandatory=$true)]
    [int]$Id
)
curl.exe -X 'DELETE' `
"http://localhost:8080/api/movie/$Id" `
-H 'accept: */*'
