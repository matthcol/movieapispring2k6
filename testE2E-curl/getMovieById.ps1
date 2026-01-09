param(
    [Parameter(Mandatory=$true)]
    [int]$Id
)

curl.exe -X 'GET' `
"http://localhost:8080/api/movie/$Id" `
-H 'accept: */*'