package ihm;

import javax.swing.JPanel;

import java.awt.Color;

import controleur.Controleur;
import ihm.menu.popUp.PopClickListener;

public class PanelDroite extends JPanel
{
    private Controleur ctrl;


    public PanelDroite(Controleur ctrl)
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
}