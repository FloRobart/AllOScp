package ihm;

import java.awt.Color;

import javax.swing.JFrame;

import controleur.Controleur;
import ihm.menu.MenuBarre;


public class FramePrincipale extends JFrame
{
    private Controleur ctrl;

    private MenuBarre menuBarre;


    /**
     * Constructeur de la frame principale
     * @param ctrl : Controleur qui fait le lien avec le metier
     */
    public FramePrincipale(Controleur ctrl)
    {
        this.ctrl = ctrl;

        this.setTitle("Frame principale");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.menuBarre = new MenuBarre(this.ctrl);
        this.setJMenuBar(this.menuBarre);
        
        
        this.appliquerTheme();
        this.setVisible(true);
    }


    /**
     * Permet d'appliquer le thème à chaque élément de l'ihm qui en à besoins
     */
    public void appliquerTheme()
    {
        Color backGeneralColor = this.ctrl.getTheme().get("background");
        Color foreGeneralColor = this.ctrl.getTheme().get("foreground");

        this.setBackground(backGeneralColor);
        this.setForeground(foreGeneralColor);

        this.menuBarre.appliquerTheme();
    }
}
