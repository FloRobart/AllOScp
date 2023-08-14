package ihm.menu;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import controleur.Controleur;


public class FrameCreerTheme extends JFrame implements WindowListener
{
    private Controleur ctrl;

    private PanelCreerTheme panelCreerTheme;

    public FrameCreerTheme(Controleur ctrl)
    {
        this.ctrl = ctrl;

        this.setTitle(this.ctrl.getLangage().get("creerTheme").get("titre"));
        this.setSize(575, 425);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.panelCreerTheme = new PanelCreerTheme(this.ctrl);
        this.add(this.panelCreerTheme);


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
        this.panelCreerTheme.appliquerTheme();
    }

    /**
     * Applique la langue
     */
    public void appliquerLangage()
    {
        this.setTitle(this.ctrl.getLangage().get("creerTheme").get("titre"));

        this.panelCreerTheme.appliquerLangage();
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        this.panelCreerTheme.cancelThemeCreation();
    }

    @Override
    public void windowClosed     (WindowEvent e) {}
    public void windowActivated  (WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowIconified  (WindowEvent e) {}
    public void windowOpened     (WindowEvent e) {}
}
