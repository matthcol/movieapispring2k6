param(
    [Parameter(Mandatory=$true)]
    [int]$MovieId,

    [Parameter(Mandatory=$true)]
    [string]$ActorIds
)

curl.exe -X 'PATCH' `
"http://localhost:8080/api/movie/$MovieId/actors" `
-H 'accept: */*' `
-H 'Content-Type: application/json' `
-d "[$ActorIds]"
