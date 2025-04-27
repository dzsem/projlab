param(
	[Parameter(Mandatory=$true)]
	[string]$test
)

$testprocessor_path = "$PSScriptRoot/../TestProcessor"
$fungorium_path = "$PSScriptRoot/../fungorium"

$testprocessor_jar = "$testprocessor_path/target/testprocessor-1.0.jar"
$fungorium_jar = "$fungorium_path/target/fungorium-1.0.jar"

$test_path = "$PSScriptRoot/../fungorium/src/main/java/projlab/fungorium/tests"

$input_path = "$test_path/input/$test.in"
$expected_path = "$test_path/expected/$test.out"
$output_path = "$test_path/$test.out"

$original_location = Get-Location

function Compile {
	param(
		[string]$wd
	)

	Write-Output "Compiling $wd..."

	Set-Location -Path $wd

	mvn compile
	mvn jar:jar
}

Compile $fungorium_path
Compile $testprocessor_path

Set-Location -Path $test_path

Write-Output "Running input file..."

Get-Content $input_path | java -jar $fungorium_jar

Write-Output "Testing output..."

$result = java -jar $testprocessor_jar $expected_path $output_path

$result_first_line = Write-Output $result | head -1

if ($result_first_line -eq "SUCCESS") {
	Write-Output "SUCCESS"
}

Set-Location $original_location