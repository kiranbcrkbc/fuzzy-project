param([switch]$DemoOnly,[ValidateSet('mysql','demo')][string]$Profile='mysql')
$ErrorActionPreference='Stop'
if (!$DemoOnly) { throw 'This clears dedicated demo records and encrypted objects. Back up first; explicitly pass -DemoOnly to confirm.' }
Set-Location -LiteralPath $PSScriptRoot
$jar=Join-Path $PSScriptRoot 'app\dist\fuzzy-auditing-1.0.0.jar'
$running=Get-CimInstance Win32_Process -Filter "Name = 'java.exe'" | Where-Object { $_.CommandLine -and $_.CommandLine.Contains('fuzzy-auditing-1.0.0.jar') }
if ($running) { throw 'Stop the fuzzy-auditing server before resetting. No data was changed.' }
if ($Profile -eq 'mysql') { . (Join-Path $PSScriptRoot 'data\trusted\mysql-env.ps1') }
$env:FUZZY_DATA_ROOT=Join-Path $PSScriptRoot $(if($Profile -eq 'demo') {'data\h2-demo'} else {'data'})
$env:FUZZY_DEMO='true'
& java -jar $jar "--spring.profiles.active=$Profile" --spring.main.web-application-type=none --fuzzy.reset-demo=true --fuzzy.reset-guard=RESET_FUZZY_DEMO
if ($LASTEXITCODE -ne 0) { throw 'Reset failed. Inspect the error before restarting.' }
