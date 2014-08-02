@echo off
REM Change this Script, to suit your needs

REM The next few line combines the command line args into one
set _j2h_parameter=
:GetParameter
if "%1" == "" goto end
set _j2h_parameter=%_j2h_parameter% "%1"
shift
goto GetParameter
:end

REM runs Java2HTML
set MBACKUP=%CLASSPATH%
set CLASSPATH=C:\TOOLS\JAVA2HTML\j2h.jar;%CLASSPATH%
java j2h %_j2h_parameter%
set CLASSPATH=%MBACKUP%
