@echo off
set JAVA_HOME_J2H=C:\jdk1.5.0_02
j2h -js  "%JAVA_HOME_J2H%\demo\jfc\SwingSet2\src" -d examples\SwingSet_demo -jd %JAVA_HOME_J2H%\docs\api http://java.sun.com/j2se/1.5.0/docs/api -n SwingSet_DEMO -m 4
