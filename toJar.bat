echo off

set src="controller\*.java"
javac "%src%" -d classes

set archive=".\classes"
jar -cf Sprint_FW.jar -C %archive% .

move Sprint_FW.jar ..\Test\lib
rmdir /s /q %archive%