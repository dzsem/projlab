#! powershell
# használat: createRunCommand.ps1 -out "buildAndRun.bat" [-in "fungorium-mappa"]

param(
	[string] $in="$PSScriptRoot\..\fungorium",
	[string] $out=""
)

function Print {
	param(
		[string]$line
	)

	if ($out -eq "") {
		Write-Output $line
	}
	else {
		Add-Content -Path $out -Value $line -Encoding Ascii
	}
}

if ($out -ne "" -and (Test-Path $out -PathType Leaf)) {
	Clear-Content -Path $out
}

$BASE_PATH = "$in\src\main\java\projlab\fungorium"
$RESOURCE_PATH = "$in\src\main\resources"
$BUILD_PATH = ".\build"

$all_java_files = @(Get-ChildItem "$in\**\*.java" -Recurse)
$folders = @(".")

$prev_location = Get-Location
Set-Location $BASE_PATH

foreach ($java_file in $all_java_files) {
	$directory = Resolve-Path -Path (Split-Path $java_file) -Relative

	if ($folders -notcontains $directory -and $directory -notlike "..*") {
		$folders += $directory
	}
}

$manifest_relative_path = "..\$(Resolve-Path -Path "$in\manifest.txt" -Relative)"
$joined_folders = ($folders | % {"$_\*.java"}) -join ' '

Set-Location $prev_location

Print "rem Run in: cmd"
Print ""
Print "cd `"$(Resolve-Path -Path $BASE_PATH -Relative)`""
Print "javac -d `"$BUILD_PATH`" $joined_folders"
Print "cd `"$BUILD_PATH`""
Print "xcopy $RESOURCE_PATH . /E /C /I /Y"
Print "jar -cmvf $manifest_relative_path fungorium.jar *"
Print ""
Print "java -jar fungorium.jar"
Print "exit /B"