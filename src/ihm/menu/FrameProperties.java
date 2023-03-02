package ihm.menu;

import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.*;

import controleur.Controleur;


public class FrameProperties extends JFrame
{
    public FrameProperties(Controleur ctrl, JFrame framePrincipale, HashMap<String, String> properties)
    {
        this.setTitle("Properties");
        this.setSize(600, 450);
        this.setResizable(false);
        this.setLocationRelativeTo(framePrincipale);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // TODO : finir le Panel properties
        this.add(new PanelProperties(ctrl));

        this.setVisible(true);
    }
}



class PanelProperties extends JPanel implements ActionListener
{
    private Controleur ctrl;
    
    private JLabel lblIcon;
    private JTextField txtName;

    private JLabel lblLocation;
    private JLabel lblTypeFile;
    private JLabel lblSize;
    private JLabel lblCreated;

    private JLabel lblContenue;
    private JScrollPane spContenue;

    private JLabel lblOnlyRead;
    private JCheckBox cbOnlyRead;

    private JLabel lblHidden;
    private JCheckBox cbHidden;


    public PanelProperties(Controleur ctrl)
    {
        this.ctrl = ctrl;

        /* Création des composants */
        this.lblIcon = new JLabel();
        this.txtName = new JTextField();

        this.lblLocation = new JLabel();
        this.lblTypeFile = new JLabel();
        this.lblSize = new JLabel();
        this.lblCreated = new JLabel();

        this.lblContenue = new JLabel();
        this.spContenue = new JScrollPane(this.lblContenue);

        this.lblOnlyRead = new JLabel();
        this.cbOnlyRead = new JCheckBox();

        this.lblHidden = new JLabel();
        this.cbHidden = new JCheckBox();


        /* Ajout des composants */
        


        /* Ajout des Listener */
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.cbOnlyRead)
        {
            // TODO : Modifier le fichier en lecture seule
        }
        else if (e.getSource() == this.cbHidden)
        {
            // TODO : Modifier le fichier en caché
        }
    }
}