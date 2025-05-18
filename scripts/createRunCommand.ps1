$ROOT_FOLDER = "$PSScriptRoot\..\fungorium"
$BASE_PATH = "$ROOT_FOLDER\src\main\java\projlab\fungorium"
$BUILD_PATH = ".\build"

$all_java_files = @(Get-ChildItem "$ROOT_FOLDER\**\*.java" -Recurse)
$folders = @(".")

$prev_location = Get-Location
Set-Location $BASE_PATH

foreach ($java_file in $all_java_files) {
	$directory = Resolve-Path -Path (Split-Path $java_file) -Relative

	if ($folders -notcontains $directory -and $directory -notlike "..*") {
		$folders += $directory
	}
}

$manifest_relative_path = "..\$(Resolve-Path -Path "$ROOT_FOLDER\manifest.txt" -Relative)"
$joined_folders = ($folders | % {"$_\*.java"}) -join ' '

Set-Location $prev_location

Write-Output "cd `"$(Resolve-Path -Path $BASE_PATH -Relative)`""
Write-Output ""
Write-Output "javac -d `"$BUILD_PATH`" $joined_folders"
Write-Output ""
Write-Output "cd `"$BUILD_PATH`""
Write-Output ""
Write-Output "jar -cmvf $manifest_relative_path fungorium.jar *"
Write-Output ""
Write-Output "java -jar fungorium.jar"
