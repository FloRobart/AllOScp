#!/bin/bash

# Création des dossiers bin et donnees s'ils n'existent pas #
ls ./bin/         >> /dev/null 2>&1 || mkdir "./bin/"
ls ./bin/donnees/ >> /dev/null 2>&1 || mkdir "./bin/donnees/"

cp -r ./donnees/ ./bin/

generateur_compile-list.sh ".\src"

echo Compilation...
javac -cp "$CLASSPATH:./bin/donnees/jar_libraries/jdom-2.0.6.jar:." -encoding utf8 -d "./bin" @compile.list && (echo Lancement de l\'application... ; java -cp "$CLASSPATH:./bin:./bin/donnees/jar_libraries/jdom-2.0.6.jar:." controleur.Controleur && echo Fin de l\'execution. || echo -e "\nErreur de L\'EXECUTION.") || echo -e "\nErreur de COMPILATION."
