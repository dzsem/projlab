@echo off
rem Run in: cmd
rem wd: fungorium

cd ".\src\main\java\projlab\fungorium"

javac -d ".\build" .\*.java .\actions\game\*.java .\actions\insectologist\*.java .\actions\menu\*.java .\actions\mycologist\*.java .\controllers\*.java .\interfaces\*.java .\models\effects\*.java .\models\player\*.java .\models\*.java .\tests\*.java .\utilities\*.java .\views\gamecomponents\*.java .\views\menu\*.java .\windowing\game\*.java .\windowing\menu\*.java

cd ".\build"

xcopy ..\..\..\..\resources . /E /C /I /Y

jar -cmvf ..\..\..\..\..\..\manifest.txt fungorium-1.0.jar *

exit /B