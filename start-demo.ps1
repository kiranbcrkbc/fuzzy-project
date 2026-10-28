param(
 [ValidateSet('mysql','demo')][string]$Profile = $(if (Test-Path (Join-Path $PSScriptRoot 'data\trusted\mysql-env.ps1')) {'mysql'} else {'demo'}),
 [switch]$Background,
 [int]$Port = 8080
)
$ErrorActionPreference = 'Stop'
Set-Location -LiteralPath $PSScriptRoot
$jar = Join-Path $PSScriptRoot 'app\dist\fuzzy-auditing-1.0.0.jar'
if (!(Test-Path -LiteralPath $jar)) { throw 'Package missing. Run .\build.ps1 first.' }
if ($Profile -eq 'mysql') {
 $localConfig = Join-Path $PSScriptRoot 'data\trusted\mysql-env.ps1'
 if (Test-Path -LiteralPath $localConfig) { . $localConfig }
 if (!$env:DB_PASSWORD) { throw 'MySQL password is not configured. Supply DB_USER, DB_PASSWORD and DB_URL or run -Profile demo.' }
}
$env:FUZZY_DATA_ROOT = Join-Path $PSScriptRoot $(if($Profile -eq 'demo') {'data\h2-demo'} else {'data'})
$env:FUZZY_DEMO = 'true'
$java = (Get-Command java -ErrorAction Stop).Source
$arguments = @('-jar', ('"' + $jar + '"'), ('--spring.profiles.active=' + $Profile), ('--server.port=' + $Port))
if ($Background) {
 New-Item -ItemType Directory -Force (Join-Path $PSScriptRoot 'data\logs') | Out-Null
 $existing = Get-NetTCPConnection -State Listen -LocalPort $Port -ErrorAction SilentlyContinue
 if ($existing) { throw "Port $Port is already in use. Stop the existing app or select -Port." }
 $suffix=if($Port -eq 8080){''}else{"-$Port"}
 $process = Start-Process -FilePath $java -ArgumentList $arguments -WorkingDirectory $PSScriptRoot -WindowStyle Hidden -PassThru -RedirectStandardOutput (Join-Path $PSScriptRoot "data\logs\server$suffix.log") -RedirectStandardError (Join-Path $PSScriptRoot "data\logs\server$suffix-error.log")
 $process.Id | Set-Content -LiteralPath (Join-Path $PSScriptRoot "data\server$suffix.pid")
 Write-Output "Starting PID $($process.Id), profile $Profile. URL: http://127.0.0.1:$Port/login"
 Write-Output "Logs: data\logs\server$suffix.log. Stop with .\stop-demo.ps1 -Port $Port."
} else {
 Write-Output "Profile: $Profile. Open http://127.0.0.1:$Port/login. Ctrl+C stops the app."
 & $java -jar $jar "--spring.profiles.active=$Profile" "--server.port=$Port"
}
