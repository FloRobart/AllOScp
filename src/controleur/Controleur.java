package controleur;

import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import java.awt.Color;

import ihm.FramePrincipale;
import metier.Metier;


public class Controleur
{
    private Metier          metier;
    private FramePrincipale ihm;


    public Controleur()
    {
        this.metier = new Metier         (this);
        this.ihm    = new FramePrincipale(this);
    }

    /*========*/
    /* Thèmes */
    /*========*/
    /**
     * Permet d'appliquer le thème à l'ihm
     */
    public void appliquerTheme() { this.ihm.appliquerTheme(); }

    /**
     * Permet de à l'ihm de récupérer la hashmap contenant les couleurs du thème
     * @return HashMap contenant les couleurs du thème
     */
    public HashMap<String, Color> getTheme() { return this.metier.getTheme(); }

	/**
	 * Permet de récupérer le nom partièle du thème utilisé (nom complet : theme_X.xml)
	 * @return Nom du thème utilisé (nom renvoyé par cette méthode : X)
	 */
	public String getThemeUsed() { return this.metier.getThemeUsed(); }

    /**
     * Change le thème à utilisé dans le fichier de sauvegarde.
     * Charge en mémoire le nouveau thème.
     * Met à jour l'ihm.
     * @param theme : Nom du thème à utiliser
     */
    public void changerTheme(String theme) { this.metier.setThemeUsed(theme); }

    /**
     * Permet de renommer le fichier avec le nom du thème et de changer le nom enregistrer dans le fichier de sauvegarde.
     * @param nomFichier : nouveau nom du fichier
     */
    public void setNomFichier(String nomFichier) { this.metier.setNomFichier(nomFichier); }

    /**
     * Permet de vérifier si le nom du thème est valide
     * @param nomTheme : Nom du thème à vérifier
     * @return boolean : true si le nom est valide, sinon false
     */
    public boolean verifNomTheme(String nomTheme) { return this.metier.verifNomTheme(nomTheme); }

    /**
     * Permet de mettre à jour la liste des noms des thèmes dans le métier. 
     */
    public void majLstNomTheme() { this.metier.majLstNomTheme(); }

    /**
     * Permet de fermer la fenêtre de création de thème
     */
    public void disposeFrameCreerTheme() { this.ihm.disposeFrameCreerTheme(); }

    /**
     * Permet de fermer la fenêtre de suppression de thème
     */
    public void disposeFrameSuppTheme() { this.ihm.disposeFrameSuppTheme(); }

    /**
	 * Permet d'ajouter un nouveau thème personnalisé à la menuBarre
     * @param nomTheme : Nom du thème dans la menuBarre
	 */
	public void ajouterThemePersoOnMenuBarre(String nomTheme) { this.ihm.ajouterThemePersoOnMenuBarre(nomTheme); }

    /**
	 * Permet de récupérer la liste des noms des thèmes perso créer par l'utilisateur.
	 * @return List : liste des noms des thèmes perso.
	 */
	public List<String> getLstNameThemesPerso() { return this.metier.getLstNameThemesPerso(); }

    /**
     * Permet de récupérer le nombre de thèmes perso créer par l'utilisateur.
     * @return int : nombre de thèmes perso.
     */
    public int getNbThemesPerso() { return this.metier.getNbThemesPerso(); }

    /**
     * Permet de modifier le nombre de thèmes perso créer par l'utilisateur.
     */
    public void majNbThemesPerso() { this.metier.majNbThemesPerso(); }

    /**
     * Permet de modifier le nom du thème utilisé
     * @param theme : Nom du thème à utiliser
     */
    public void setNomTheme(String theme) { this.metier.setNomTheme(theme); }

    /**
	 * Permet de modifier un élément du thème dans le fichier xml.
	 * @param nameElement : nom de l'élément à modifier.
	 * @param color : nouvelle couleur de l'élément.
	 * @return boolean : true si l'élément a été modifié, false sinon.
	 */
    public boolean setElementTheme(String nomElement, Color couleur) { return this.metier.setElementTheme(nomElement, couleur); }

    /**
     * Permet de supprimer un thème perso.
     * Supprime le fichier du thème perso.
     * Met à jour la liste des noms des thèmes perso.
     * Met à jour le nombre de thèmes perso.
     * Met à jour la menuBarre.
     * @param lstNomsThemes : Liste des noms des thèmes perso à supprimer
     */
    public void supprimerThemePerso(List<String> lstNomsThemes)
    {
        this.metier.supprimerThemePerso(lstNomsThemes);
        this.ihm.supprimerThemePersoOnMenuBarre(lstNomsThemes);
    }

    /**
     * Permet de récupérer la liste des clés de la HashMap contenant les couleurs du thème.
     * @return List : liste des clés
     */
    public String[] getEnsClesThemes() { return this.metier.getEnsClesThemes(); }


    /*==========*/
    /* Langages */
    /*==========*/
    /**
     * Permert de récupérer toute les couleurs de thème charger en mémoire.
     * @return HashMap - liste des couleurs du thème.
     * 
     * object possible dans la hashmap : 
     * 
     * list.get(0) = couleur de fond.
     * list.get(1) = couleur du texte.
     * list.get(2) = couleur de hint / placeHolder (n'existe pas toujours).
     */
    public HashMap<String, HashMap<String, String>> getLangage() { return this.metier.getLangage();}

    /**
     * Change le langage à utilisé dans le fichier de sauvegarde.
     * Charge en mémoire le nouveau langage.
     * Met à jour l'ihm.
     * @param langage : Nom du langage à utiliser
     */
    public void changerLangage(String langage) { this.metier.setLangageUsed(langage); }

    /**
     * Permet de récupérer le nom du langage utilisé
     * @return String : nom du langage utilisé
     */
    public String getLangageUsed() { return this.metier.getLangageUsed(); }

    /**
     * Permet d'appliquer le langage à l'ihm.
     */
    public void appliquerLangage() { this.ihm.appliquerLangage(); }



    /*=====================*/
    /* Gestion des onglets */
    /*=====================*/
    /**
     * Permet d'ajouter un onglet à l'ihm
     */
    public void ajouterOnglet() { this.ihm.ajouterOnglet(); }

    /**
     * Permet de supprimer l'onglet séléctionner dans l'ihm
     */
    public void supprimerOnglet() { this.ihm.supprimerOnglet(); }

    /**
     * Permet de faire le focus sur l'onglet précédent
     */
    public void ongletPrecedent() { this.ihm.ongletPrecedent(); }

    /**
     * Permet de faire le focus sur l'onglet suivant
     */
    public void ongletSuivant() { this.ihm.ongletSuivant(); }

    /**
     * Permet de fermer la fenêtre principale
     */
    public void closeParent() { this.ihm.dispose(); }
    
    /**
     * Permet de renommer un onglet
     * @param nom : Nouveau nom de l'onglet
     */
    public void renameOnglet(String nom) { this.ihm.renameOnglet(nom); }

    /**
     * Permet de récupérer la frame principale (utile pour les JDialog qui doivent connaitre leur parent)
     * @return JFrame : frame principale
     */
    public JFrame getFramePrincipale() { return this.ihm; }
    


    /*========*/
    /* Autres */
    /*========*/






    /*======*/
    /* Main */
    /*======*/
    public static void main(String[] args)
    {
        new Controleur();
    }
}