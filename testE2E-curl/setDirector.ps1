param(
    [Parameter(Mandatory=$true)]
    [int]$MovieId,

    [Parameter(Mandatory=$true)]
    [int]$DirectorId
)


curl.exe -X 'PATCH' `
"http://localhost:8080/api/movie/$MovieId/director/$DirectorId" `
-H 'accept: */*'
