package ihm.menu;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controleur.Controleur;


public class PanelSuppTheme extends JPanel implements ActionListener
{
    private Controleur ctrl;

    private JLabel lblTitre;
    private JPanel panelCentre;
    private JPanel panelSud;

    private List<JCheckBox> lstCheckBox;
    private List<String>    lstNomThemePerso;
    private List<JLabel>    lstLabel;
    private List<JButton>   lstButton;

    private JButton btnSupprimer;
    private JButton btnAnnuler;


    public PanelSuppTheme(Controleur ctrl)
    {
        this.ctrl = ctrl;
        
        this.setLayout(new BorderLayout(0, 20));

        this.panelCentre = new JPanel(new GridLayout(this.ctrl.getNbThemesPerso()+1, 4, 20, 5));
        this.panelSud    = new JPanel();

        this.lblTitre    = new JLabel("", SwingConstants.CENTER);
        this.lblTitre.setFont(new Font("Arial", Font.BOLD, 36));

        /*-------------------------*/
        /* Création des composants */
        /*-------------------------*/
        this.lstNomThemePerso = this.ctrl.getLstNameThemesPerso();
        int nbThemePerso = this.lstNomThemePerso.size();

        /* Checkbox */
        this.lstCheckBox = new ArrayList<JCheckBox>();
        for (int i = 0; i < nbThemePerso; i++)
        {
            this.lstCheckBox.add(new JCheckBox());
            this.lstCheckBox.get(i).setHorizontalAlignment(SwingConstants.RIGHT);
        }

        /* Labels nom theme */
        this.lstLabel = new ArrayList<JLabel>();
        for (int i = 0; i < nbThemePerso; i++)
        {
            this.lstLabel.add(new JLabel(this.lstNomThemePerso.get(i)));
            this.lstLabel.get(i).setHorizontalAlignment(SwingConstants.LEFT);
        }

        /* Entête de colonne */
        this.lstLabel.add(this.lstLabel.size(), new JLabel());
        this.lstLabel.get(this.lstLabel.size()-1).setHorizontalAlignment(SwingConstants.RIGHT);

        this.lstLabel.add(this.lstLabel.size(), new JLabel());
        this.lstLabel.get(this.lstLabel.size()-1).setHorizontalAlignment(SwingConstants.CENTER);

        this.lstLabel.add(this.lstLabel.size(), new JLabel());
        this.lstLabel.get(this.lstLabel.size()-1).setHorizontalAlignment(SwingConstants.CENTER);

        /* Boutons appliquer thème */
        this.lstButton = new ArrayList<JButton>();
        for (int i = 0; i < nbThemePerso; i++)
            this.lstButton.add(new JButton());

        /* Boutons supprimer et annuler */
        this.btnSupprimer = new JButton();
        this.btnAnnuler   = new JButton();


        /*----------------------*/
        /* Ajout des composants */
        /*----------------------*/
        this.panelCentre.add(this.lstLabel.get(this.lstLabel.size()-3));
        this.panelCentre.add(this.lstLabel.get(this.lstLabel.size()-2));
        this.panelCentre.add(this.lstLabel.get(this.lstLabel.size()-1));
        this.panelCentre.add(new JLabel());
        for (int i = 0; i < nbThemePerso; i++)
        {
            this.panelCentre.add(this.lstCheckBox.get(i));
            this.panelCentre.add(this.lstLabel   .get(i));
            this.panelCentre.add(this.lstButton  .get(i));
            this.panelCentre.add(new JLabel());
        }

        this.panelSud.add(this.btnAnnuler  );
        this.panelSud.add(this.btnSupprimer);

        this.add(this.lblTitre   , BorderLayout.NORTH );
        this.add(this.panelCentre, BorderLayout.CENTER);
        this.add(this.panelSud   , BorderLayout.SOUTH );


        /*---------------------------*/
        /* Activation des composants */
        /*---------------------------*/
        for (int i = 0; i < nbThemePerso; i++)
            this.lstButton.get(i).addActionListener(this);

        this.btnSupprimer.addActionListener(this);
        this.btnAnnuler.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnSupprimer)
        {
            JOptionPane.showConfirmDialog(this, this.ctrl.getLangage().get("supprimerTheme").get("msgConfirmation"), this.ctrl.getLangage().get("supprimerTheme").get("titreMsgConfirmation"), JOptionPane.YES_NO_OPTION);
        

        }

        if (e.getSource() == this.btnAnnuler)
        {

        }

        for (int i = 0; i < this.lstButton.size(); i++)
            if (e.getSource() == this.lstButton.get(i))
                this.ctrl.changerTheme(this.lstLabel.get(i).getText().replace(" ", "_"));
    }


    /**
     * Applique le thème à tout les composants du panel
     */
    public void appliquerTheme()
    {
        Color backGeneralColor = this.ctrl.getTheme().get("background");
        Color foreGeneralColor = this.ctrl.getTheme().get("foreground");
        Color btnBackColor     = this.ctrl.getTheme().get("buttonsBackground");


        /* Le Panel elle même */
        this.setBackground(backGeneralColor);
        this.setForeground(foreGeneralColor);

        this.panelCentre.setBackground(backGeneralColor);

        this.panelSud.setBackground(backGeneralColor);


        /* Label titre */
        this.lblTitre.setOpaque(false);
        this.lblTitre.setForeground(foreGeneralColor);

        
        for (int i = 0; i < this.lstCheckBox.size(); i++)
        {
            /* CheckBox */
            this.lstCheckBox.get(i).setOpaque(false);
            this.lstCheckBox.get(i).setForeground(foreGeneralColor);

            /* Label */
            this.lstLabel.get(i).setOpaque(false);
            this.lstLabel.get(i).setForeground(foreGeneralColor);

            /* Bouton */
            this.lstButton.get(i).setOpaque(true);
            this.lstButton.get(i).setBackground(btnBackColor);
            this.lstButton.get(i).setForeground(foreGeneralColor);
        }


        /* Boutons */
        this.btnSupprimer.setBackground(btnBackColor);
        this.btnSupprimer.setForeground(foreGeneralColor);

        this.btnAnnuler.setBackground(btnBackColor);
        this.btnAnnuler.setForeground(foreGeneralColor);
    }

    /**
     * Applique la langue
     */
    public void appliquerLangage()
    {
        HashMap<String, String> langageSuppTheme = this.ctrl.getLangage().get("supprimerTheme");

        /* Titre */
        this.lblTitre.setText(langageSuppTheme.get("titre"));

        /* Tête des colonnes */
        this.lstLabel.get(this.lstLabel.size()-3).setText(langageSuppTheme.get("lblSupprimer"     ));
        this.lstLabel.get(this.lstLabel.size()-2).setText(langageSuppTheme.get("lblNomTheme"      ));
        this.lstLabel.get(this.lstLabel.size()-1).setText(langageSuppTheme.get("lblAppliquerTheme"));

        /* List des boutons */
        String textButton = langageSuppTheme.get("btnAppliquerTheme");
        for (int i = 0; i < this.lstButton.size(); i++) 
            this.lstButton.get(i).setText(textButton);


        /* Bouton supprimer */
        this.btnSupprimer.setText(langageSuppTheme.get("btnSupprimer"));

        /* Bouton annuler */
        this.btnAnnuler.setText(langageSuppTheme.get("btnAnnuler"));
    }
}
