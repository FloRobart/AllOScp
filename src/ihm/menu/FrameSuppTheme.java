package ihm.menu;

import java.awt.Color;

import javax.swing.JFrame;

import controleur.Controleur;


public class FrameSuppTheme extends JFrame
{
    private Controleur ctrl;

    private PanelSuppTheme panelSuppTheme;

    public FrameSuppTheme(Controleur ctrl)
    {
        this.ctrl = ctrl;

        this.setTitle(this.ctrl.getLangage().get("supprimerTheme").get("titre"));
        this.setSize(500, 50 * this.ctrl.getNbThemesPerso() + 100);
        //this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.panelSuppTheme = new PanelSuppTheme(this.ctrl);
        this.add(this.panelSuppTheme);


        this.appliquerTheme();
        this.appliquerLangage();
        this.setVisible(true);
    }


    /**
     * Applique le thème à tout les composants du panel
     */
    public void appliquerTheme()
    {
        Color backGeneralColor = this.ctrl.getTheme().get("background");
        Color foreGeneralColor = this.ctrl.getTheme().get("foreground");


        /*--------------------*/
        /* La Frame elle même */
        /*--------------------*/
        this.setBackground(backGeneralColor);
        this.setForeground(foreGeneralColor);


        /*-------*/
        /* Panel */
        /*-------*/
        this.panelSuppTheme.appliquerTheme();
    }


    /**
     * Applique la langue
     */
    public void appliquerLangage()
    {
        this.setTitle(this.ctrl.getLangage().get("supprimerTheme").get("titre"));

        this.panelSuppTheme.appliquerLangage();
    }
}
