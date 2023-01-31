package ihm;

import javax.swing.JPanel;

import java.awt.Color;

import controleur.Controleur;


public class PanelFonctionGlobal extends JPanel
{
    private Controleur ctrl;


    public PanelFonctionGlobal(Controleur ctrl)
    {
        this.ctrl = ctrl;
    }


    /**
     * Permet d'appliquer le thème à chaque élément du panel
     */
    public void appliquerTheme()
    {
        Color backGeneralColor = this.ctrl.getTheme().get("background");
        Color foreGeneralColor = this.ctrl.getTheme().get("foreground");

        this.setBackground(backGeneralColor);
        this.setForeground(foreGeneralColor);
    }

    /**
     * Permet d'appliquer le langage à chaque élément du panel
     */
    public void appliquerLangage()
    {
        // TODO : Appliquer le langage
    }
}
