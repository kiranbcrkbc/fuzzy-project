param([int]$Port=8080)
$ErrorActionPreference = 'Stop'
$suffix=if($Port -eq 8080){''}else{"-$Port"}
$pidFile = Join-Path $PSScriptRoot "data\server$suffix.pid"
if (!(Test-Path -LiteralPath $pidFile)) { Write-Output 'No background-server PID file. If running in a console, use Ctrl+C there.'; exit 0 }
$serverPid = [int](Get-Content -LiteralPath $pidFile)
$process = Get-CimInstance Win32_Process -Filter "ProcessId = $serverPid"
if (!$process) { Write-Output 'Recorded server is already stopped.'; exit 0 }
$expectedJar = Join-Path $PSScriptRoot 'app\dist\fuzzy-auditing-1.0.0.jar'
if ($process.Name -notmatch '^java(w)?\.exe$' -or !$process.CommandLine.Contains($expectedJar)) { throw 'PID does not belong to this project JAR. Refusing to stop it.' }
Stop-Process -Id $serverPid
Write-Output "Stopped this project's server (PID $serverPid). Data is retained."
