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


    /**
     * Permet d'appliquer le thème à l'ihm
     */
    public void appliquerTheme()
    {
        this.ihm.appliquerTheme();
    }

    /**
     * Permet de à l'ihm de récupérer la hashmap contenant les couleurs du thème
     * @return HashMap contenant les couleurs du thème
     */
    public HashMap<String, List<Color>> getTheme() { return this.metier.getTheme(); }

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


    public static void main(String[] args)
    {
        new Controleur();
    }
}