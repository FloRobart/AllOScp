package controleur;

import ihm.FramePrincipale;
import metier.Metier;


public class Controleur
{
    private Metier          metier;
    private FramePrincipale ihm;


    public Controleur()
    {
        this.metier = new Metier         ();
        this.ihm    = new FramePrincipale();
    }


    public static void main(String[] args)
    {
        new Controleur();
    }
}