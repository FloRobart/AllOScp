package ihm.menu;

import javax.swing.JFrame;

import controleur.Controleur;


public class FrameCreerTheme extends JFrame
{
    private Controleur ctrl;

    public FrameCreerTheme(Controleur ctrl)
    {
        this.ctrl = ctrl;

        this.setTitle("Créer un thème");
        this.setSize(300, 200);



        this.setVisible(true);
    }
}
