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
	 * Permet de récupérer le nom du thème utilisé
	 * @return Nom du thème utilisé
	 */
	public String getThemeUsed() { return this.metier.getThemeUsed(); }

    /**
     * Change le thème à utilisé dans le fichier de sauvegarde.
     * Charge en mémoire le nouveau thème.
     * Met à jour l'ihm.
     * @param theme : Nom du thème à utiliser
     */
    public void changerTheme(String theme) { this.metier.setThemeUsed(theme); }


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






    /*======*/
    /* Main */
    /*======*/
    public static void main(String[] args)
    {
        new Controleur();
    }
}