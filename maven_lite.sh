#!/bin/bash

#======#
# Aide #
#======#
# $1 = status de sortie, $2 = message optionnel
function help()
{
    echo "Utilisation : mvn_lite.sh [options] [arguments]"
    echo "Permet de compiler et lancer un projet java en utilisant"
    echo "le minimum d'options et de manipulation."
    echo "Facilite la compilation et le lancement d'un projet java"
    echo "plus simplement que maven."
    echo ""
    echo "Toutes les options prennent un seul argument sauf -c et -l qui n'en prennent aucun."
    echo ""
    echo "L'ordre des options n'a pas d'importance sauf pour l'option -f"
    echo "qui doit être la première option de la ligne de commande."
    echo "les guillements double ne sont pas obligatoires, sauf si l'argument contient un espace"
    echo ""
    echo "Les options qui sont utilisé avec -c alors qu'elles sont utilisable"
    echo "uniquement avec -l sont ignorées et inversement."
    echo ""
    echo "Les options qui sont utilisable avec -c ou -l sont utilisable avec -cl et -lc."
    echo ""
    echo "Les arguments obligatoires pour les options longues le sont aussi pour les options courtes :"
    echo "  -s , --source          Dossier racine du projet à compiler."
    echo ""
    echo "  -o , --output          Dossier de sortie des fichiers compilés."
    echo ""
    echo "  -cp, --classpath       Liste des fichiers jar et du dossier de sortie"
    echo "                         des fichiers compilés (le même dossier que pour"
    echo "                         l'option -o) à ajouter au classpath lors de la"
    echo "                         compilation et du lancement."
    echo "                         Les fichiers jar doivent être séparés par des ':'."
    echo "                         La valeur par defaut du classpath est le dossier de"
    echo "                         sortie des fichiers compilés si l'option -o est utilisé,"
    echo "                         sinon le classpath sera le dossier courent."
    echo ""
    echo "  -d , --dependency      Dossier contenant les fichiers jar utiliser"
    echo "                         par le programme. Tout les fichiers jar seront"
    echo "                         ajoutés au classpath lors de la compilation"
    echo "                         et du lancement."
    echo ""
    echo "  -n , --name            Permet de changer le nom du fichier contenant les"
    echo "                         chemins des fichiers java à compiler. Le nom par"
    echo "                         defaut est 'compile.list'."
    echo "                         Utilisable uniquement avec l'option -c."
    echo ""
    echo "  -e , --encoding        Permet de changer l'encodage des fichiers java"
    echo "                         à compiler. L'encodage par defaut est 'UTF-8'."
    echo "                         Utilisable uniquement avec l'option -c."
    echo ""
    echo "  -m , --main            Classe principale à lancer. Utilisable"
    echo "                         uniquement avec l'option -l."
    echo ""
    echo "  -dt, --data            Dossier contenant les données du projet."
    echo "                         Permet de copier le dossier de données"
    echo "                         dans le dossier de sortie. Utilisable"
    echo "                         uniquement avec l'option -c."
    echo ""
    echo "  -f , --file            Fichier de configuration. Permet de charger"
    echo "                         les options à partir d'un fichier de configuration,"
    echo "                         le séparateur sont l'espace et le retour à la ligne."
    echo "                         Les options du fichier de configuration prédomine"
    echo "                         sur les options de la ligne de commande."
    echo "                         L'option -f doit obligatoirement être la première"
    echo "                         option de la ligne de commande."
    echo ""
    echo ""
    echo "  -c , --compilation     Compiler le projet."
    echo ""
    echo "  -l , --launch          Lancer le projet."
    echo ""
    echo "  -cl, --compile-launch  Compiler et lancer le projet. (équivalent à -c -l)"
    echo ""
    echo "  -lc, --launch-compile  Compiler et lancer le projet. (équivalent à -c -l)"
    echo ""
    echo ""
    echo "  -h , --help            afficher l'aide et quitter."
    echo ""
    echo ""
    echo "Les options minimum obligatoires pour la compilation sont :"
    echo "  -s, --source       Dossier racine du projet à compiler."
    echo "  -o, --output       Dossier de sortie des fichiers compilés."
    echo "  -c, --compilation  Compiler le projet."
    echo ""
    echo ""
    echo "Les options minimum obligatoires pour le lancement sont :"
    echo "  -m , --main       Classe principale à lancer."
    echo "  -l , --launch     Lancer le projet."
    echo "  -cp, --classpath  Voir l'option -cp dans la listes des options."
    echo ""
    echo ""
    echo "Les options minimum obligatoires pour la compilation et le lancement sont :"
    echo "  -s , --source          Dossier racine du projet à compiler."
    echo "  -o , --output          Dossier de sortie des fichiers compilés."
    echo "  -m , --main            Classe principale à lancer."
    echo "  -cl, --compile-launch  Compiler et lancer le projet. (équivalent à -c -l)"
    echo ""
    echo ""
    echo ""
    echo "Exemple d'utiliation : './mvn_lite.sh -s src -o ./bin -m \"controleur.Main\" -cl'"
    echo "                       Cette ligne de commande va compiler et lancer le projet"
    echo "                       java contenu dans le dossier 'src' et lancer la classe"
    echo "                       'controleur.Main' avec le classpath './bin'."
    echo "                       Tout les fichiers compilés seront dans le dossier 'bin'."

    [[ ! -z "$2" ]] && { echo; echo; echo "$2"; echo; }
    exit "$1"
}


#================================================================#
# Demande à l'utilisateur de rentrer les informations manquantes #
#================================================================#
function demandeInfo()
{
    if [ "$1" = "source" ]
    then
        read -p "Veuillez entrez le dossier racine à partir du quel la compile.list vas être générer : " source
    else
        if [ "$1" = "output" ]
        then
            read -p "Veuillez entrez le dossier de output dans lequel vont être les fichier .class : " output
        fi
    fi
}


#=======================================#
# Vérifie si les arguments sont valides #
#=======================================#
# $1 = argument à vérifier, $2 = phrase à afficher si l'argument est invalide
function verifArguments()
{
    [[ ${1} =~ ^-{1,2}[a-z]+$ ]] && { echo "$2"; exit 1; }
    [[ ${1} =~ ^$ ]] && { echo "$2"; exit 1; }
    [[ -z $1 ]] && { echo "$2"; exit 1; }

    return 0
}

#=========================#
# Genère la compile liste #
#=========================#
# $1 = dossier source
function genererCompileList()
{
    for file in `ls -1 $1`
    do
        [[ -f $1"/"$file ]] && { [[ "${file##*.}" = "$extensionValide" ]] && echo $1"/"$file >> "$nomFichierSortie"; }
        [[ -d $1"/"$file ]] && { genererCompileList $1"/"$file; }
    done
}

#=========================#
# Liste les fichiers .jar #
#=========================#
# $1 = dossier source
function listerdependencies()
{
    if [ ! -z $dependency ]
    then
        for file in `ls -1 $1`
        do
            [[ -f $1"/"$file ]] && { [[ "${file##*.}" = "jar" ]] && dependencies+="$1/$file:"; }
            [[ -d $1"/"$file ]] && { listerdependencies $1"/"$file; }
        done

        dependencies=$( echo "$dependencies" | sed -e 's/\"//g' )
    fi
}


#============================#
# Compile les fichiers .java #
#============================#
function compilation()
{
    echo 'Compilation...'
    javac -cp "$classpath:$dependencies" -encoding $encoding -d "$output" @$nomFichierSortie && { echo 'Fin de la compilation.'; } || { echo "Erreur lors de la compilation."; exit 1; }
}

#====================#
# Lance le programme #
#====================#
function lancement()
{
    echo 'Lancement du programme...'
    java -cp "$classpath:$dependencies" $main && { echo 'Fin de l'\''execution.'; } || { echo "Erreur lors du lancement du programme."; exit 1; }
}


#======#
# Main #
#======#
#-----------#
# Varaibles #
#-----------#
extensionValide="java"
nomFichierSortie="compile.list"
dependencies=""
encoding="UTF-8"
compilation=1
lancement=1
args=("$@")
ancienArg="a"


#-----------#
# Execution #
#-----------#
[[ -z $1 ]] && { help 1 "Aucun argument donnée"; }
if [ $1 = "-h" ] || [ $1 = "--help" ]
then
    help 0
fi

if [ $1 = "-f" ] || [ $1 = "--file" ]
then
    [[ -z $2 ]] && { help 1 "Aucun fichier de configuration donnée pour l'option '-f' ou '--file'"; }
    ls $2 > /dev/null 2>&1 || { echo "Le fichier de configuration '$2' n'existe pas"; exit 1; }
    [[ ! -f $2 ]] && { echo "Le fichier de configuration '$2' n'est pas un fichier"; exit 1; }

    for line in `cat $2`
    do
        line=$( echo "$line" | sed -e 's/\"//g' )
        args+=("$line")
    done

    unset args[0]
    unset args[1]
fi


args+=("-a")
for arg in "${args[@]}"
do
    case "${ancienArg}" in
        "-s") verifArguments $arg "Aucune source donnée pour l'option '-s'" && source=$arg ;;
        "--source") verifArguments $arg "Aucune source donnée pour l'option '--source'" && source=$arg ;;

        "-o") verifArguments $arg "Aucune sortie donnée pour l'option '-o'" && output=$arg ;;
        "--output") verifArguments $arg "Aucune sortie donnée pour l'option '--output'" && output=$arg ;;

        "-cp") verifArguments $arg "Aucun classpath donnée pour l'option '-cp'" && classpath=$arg ;;
        "--classpath") verifArguments $arg "Aucun classpath donnée pour l'option '--classpath'" && classpath=$arg ;;

        "-d") verifArguments $arg "Aucune dépendance donnée pour l'option '-d'" && dependency=$arg ;;
        "--dependency") verifArguments $arg "Aucune dépendance donnée pour l'option '--dependency'" && dependency=$arg ;;

        "-n") verifArguments $arg "Aucun nom donnée pour l'option '-n'" && nomFichierSortie=$arg ;;
        "--name") verifArguments $arg "Aucun nom donnée pour l'option '--name'" && nomFichierSortie=$arg ;;

        "-e") verifArguments $arg "Aucun encodage donnée pour l'option '-e'" && encoding=$arg ;;
        "--encoding") verifArguments $arg "Aucun encodage donnée pour l'option '--encoding'" && encoding=$arg ;;

        "-m") verifArguments $arg "Aucun fichier main donnée pour l'option '-m'" && main=$arg ;;
        "--main") verifArguments $arg "Aucun fichier main donnée pour l'option '--main'" && main=$arg ;;

        "-dt") verifArguments $arg "Aucun dossier de données donnée pour l'option '-dt'" && data=$arg ;;
        "--data") verifArguments $arg "Aucun dossier de données donnée pour l'option '--data'" && data=$arg ;;

        "-c") compilation=0 ;;
        "--compilation") compilation=0 ;;

        "-l") lancement=0 ;;
        "--launch") lancement=0 ;;

        "-cl") compilation=0 ; lancement=0 ;;
        "--compile-launch") compilation=0 ; lancement=0 ;;
        "-lc") compilation=0 ; lancement=0 ;;
        "--launch-compile") compilation=0 ; lancement=0 ;;
    esac

    ancienArg=$arg
done


if [ $compilation -eq 0 ]
then
    # Vérification du dossier source
    until ls $source > /dev/null 2>&1 && ! [[ -z $source ]]
    do
        echo "Le dossier source '$source' n'existe pas"
        demandeInfo "source"
    done

    # Vérification du dossier de sortie
    while [[ -z $output ]]
    do
        echo 'aucun dossier de sortie donnée'
        demandeInfo "output"
    done

    # Vérification du dossier de sortie
    ls -d $output > /dev/null 2>&1 && { [[ -d $output ]] || { echo "'$output' n'est pas un dossier."; exit 1; } } || {
        read -p "Le dossier de sortie '$output' n'existe pas. Voulez-vous le créer ? (y/n) : " reponse
        [[ ${reponse} =~ ^[yY]([eE][sS])?$ ]] && {
            mkdir -p $output && echo "Le dossier '$output' a été créé" || { echo "Erreur lors de la création du dossier '$output'"; exit 1; }
        } || { echo "Le dossier '$output' n'a pas été créé"; exit 0; }
    }

    echo "output='$output'"
    echo "source='$source'"
    [[ "$output"="$source" ]] && { echo "output='$output'"; echo "source='$source'"; echo Le dossier source doit être différent du dossier de sortie; exit 1; }

    # Copie du dossier de données
    if [ ! -z $data ]
    then
        [[ -d $data ]] && { cp -fr "$data" "$output" || { echo "Erreur lors de la copie du dossier '$data' dans le dossier '$output'"; exit 1; }; } || { echo "Le dossier de données '$data' n'existe pas"; exit 1; }
    fi

    [[ -z $classpath ]] && classpath=$output
    listerdependencies $dependency

    ls $nomFichierSortie > /dev/null 2>&1 && { rm -f $nomFichierSortie; } || { echo "Erreur lors de la suppression du fichier '$nomFichierSortie'."; exit 1; }
    genererCompileList $source; ls $nomFichierSortie > /dev/null 2>&1 || { echo "Erreur lors de la génération du fichier '$nomFichierSortie'."; exit 1; }
    compilation
    ls $nomFichierSortie > /dev/null 2>&1 && { rm -f $nomFichierSortie; } || { echo "Erreur lors de la suppression du fichier '$nomFichierSortie'."; exit 1; }
fi


[[ $lancement -eq 0 ]] && {
    [[ -z $classpath ]] && { help 1 "L'option -cp ou --classpath est obligatoire pour lancer le programme"; }
    [[ -z $main ]] && { help 1 "L'option -m ou --main est obligatoire pour lancer le programme"; }
    listerdependencies $dependency
    lancement
}
