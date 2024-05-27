echo off

set "current=."
set "temp=temp"

if not exist "%temp%" mkdir "%temp%"

:: Pour chaque fichier, miditra recursivement de copiena izay *.java to temp
for /r "%current%" %%f in (*.java) do (
    set chemin_complet="%%~f"
    @REM set nom_fichier="%%~nf.java"

    copy "%%~f" "%temp%\" > nul
)

set /p projet="Nom du Jar:"
set archive=%projet%.jar

javac -d temp\class\ temp\*.java
cd %temp%\class
jar -cf %archive% .\
cd ..\..\
xcopy "temp\class\%archive%" "%current%" /y

rmdir /S /Q %temp%

move "%archive%" ..\Test\lib\