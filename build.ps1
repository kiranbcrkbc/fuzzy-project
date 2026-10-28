$ErrorActionPreference = 'Stop'
$running=Get-CimInstance Win32_Process -Filter "Name = 'java.exe'" | Where-Object { $_.CommandLine -and $_.CommandLine.Contains('fuzzy-auditing-1.0.0.jar') }
if ($running) { throw 'Stop the application before a clean build; Windows may lock the running JAR. Use .\stop-demo.ps1 or Ctrl+C in its console.' }
Set-Location -LiteralPath (Join-Path $PSScriptRoot 'app')
$buildOut=Join-Path $PSScriptRoot ('app\builds\' + (Get-Date -Format 'yyyyMMdd-HHmmss'))
if (Get-Command mvn -ErrorAction SilentlyContinue) { & mvn -B package "-Dfuzzy.build.directory=$buildOut" } else { & .\mvnw.cmd -B package "-Dfuzzy.build.directory=$buildOut" }
if ($LASTEXITCODE -ne 0) { throw 'Build or tests failed. Inspect Maven output.' }
New-Item -ItemType Directory -Force (Join-Path $PSScriptRoot 'app\dist') | Out-Null
Copy-Item -LiteralPath (Join-Path $buildOut 'fuzzy-auditing-1.0.0.jar') -Destination (Join-Path $PSScriptRoot 'app\dist\fuzzy-auditing-1.0.0.jar') -Force
$buildOut | Set-Content -LiteralPath (Join-Path $PSScriptRoot 'evidence\latest-build-path.txt')
Write-Output 'Runnable package: app\dist\fuzzy-auditing-1.0.0.jar'
