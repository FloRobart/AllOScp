@echo off

:: Création des dossiers bin et donnees s'ils n'existent pas ::
IF NOT EXIST "./bin/"         ( mkdir "./bin" )
IF NOT EXIST "./bin/donnees/" ( mkdir "./bin/donnees/" )

XCOPY "./donnees" "./bin/donnees" /E /Y >NUL

call generateur_compile-list.bat ".\src"

echo Compilation...
call javac -cp "%CLASSPATH%;.\bin\donnees\jar_libraries\jdom-2.0.6.jar;." -encoding utf8 -d ".\bin" "@compile.list" && ( echo Lancement du programme... & call java -cp "%CLASSPATH%;.\bin;.\bin\donnees\jar_libraries\jdom-2.0.6.jar;." controleur.Controleur && echo Fin de l'execution. || ( echo. & echo Erreur d'EXECUTION. )) || echo Erreur de COMPILATION.

goto :eof