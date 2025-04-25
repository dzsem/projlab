#! powershell
# használat: ./listProjectFiles.ps1 [-src forrásmappa] [-out kimeneti csv fájl] [-exclude glob1] [-exclude glob2] [...]
#
# output ami a doksiba kell:
# ./scripts/listProjectFiles.ps1 -path "fungorium/src" -out "files.csv" -exclude "*Tester.java"

param(
	[string]$src="fungorium",
	[string]$out="",
	[string[]]$exclude={}
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

Print "file_name;size_bytes;creation_date;content"

$files = @(Get-ChildItem "$src/**.java" -Recurse -Exclude $exclude)

foreach ($file in $files) {
	$creation_date = git log --follow --format=%ad "--date=format:%Y. %m. %d. %H:%M" $file | tail -1
	$file_name = $file.Name
	$file_size_bytes = $file.Length

	Print "$file_name;$file_size_bytes;$creation_date;"
}