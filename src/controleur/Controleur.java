package controleur;

import java.util.HashMap;
import java.util.List;

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
     * 
     */
    public void ajouterThemePerso(String nom, HashMap<String, Color> theme) { this.metier.ajouterThemePerso(nom, theme); }


    /**
     * Permet de connaitre le nombre de thème personnalisé créer par l'utilisateur
     */
    public int getNbThemePerso() { return this.metier.getNbThemePerso(); }

    /**
     * Permet de modifier le nombre de thème personnalisé créer par l'utilisateur
     */
    public void setNbThemePerso(int nbThemePerso) { this.metier.setNbThemePerso(nbThemePerso); }

    /**
     * Permet de récupérer la liste des noms des thèmes personnalisé
     * @return Liste des noms des thèmes personnalisé
     */
    public List<String> getLstNomThemePerso() { return this.metier.getLstNomThemePerso(); }


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
    
    


    /*========*/
    /* Autres */
    /*========*/
    //public int getWidthFrame() { return this.ihm.getWidth(); }






    /*======*/
    /* Main */
    /*======*/
    public static void main(String[] args)
    {
        new Controleur();
    }
}