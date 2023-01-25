package ihm.menu;

import java.awt.Color;

import javax.swing.JFrame;

import controleur.Controleur;


public class FrameCreerTheme extends JFrame
{
    private Controleur ctrl;

    private PanelCreerTheme panelCreerTheme;

    public FrameCreerTheme(Controleur ctrl)
    {
        this.ctrl = ctrl;

        this.setTitle("Créer un thème");
        this.setSize(575, 425);

        this.panelCreerTheme = new PanelCreerTheme(this.ctrl);
        this.add(this.panelCreerTheme);


        this.appliquerTheme();
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
        this.panelCreerTheme.appliquerTheme();
    }
}
