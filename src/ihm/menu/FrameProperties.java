package ihm.menu;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Controleur;


public class FrameProperties extends JFrame
{
    public FrameProperties(Controleur ctrl, JFrame framePrincipale, String properties)
    {
        this.setTitle("Properties");
        this.setSize(600, 450);
        this.setLocationRelativeTo(framePrincipale);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.add(new PanelProperties(ctrl, properties));

        this.setVisible(true);
    }
}



class PanelProperties extends JPanel
{
    private Controleur ctrl;
    private JLabel lblProperties;


    public PanelProperties(Controleur ctrl, String properties)
    {
        this.ctrl = ctrl;

        this.lblProperties = new JLabel(properties);
        this.add(lblProperties);
    }
}