package ihm.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controleur.Controleur;


public class PanelCreerTheme extends JPanel implements ActionListener
{
    private static final String PATH_THEMES = "./bin/donnees/themes/";
    private static final String[] ENS_LBL_STRING = new String[] {"Couleur générale du fond", "Couleur générale du texte", "Couleur de mauvaise action", "Couleur de bonne action", "Couleur de fond des titres", "Couleur de fond des zones de saisies", "Couleur du texte par défaut \n des zones de saisie", "Couleur de fond des boutons"};
    private static final String LST_CLES[] = new String[] {"background", "foreground", "disableColor", "enableColor", "titlesBackground", "saisiesBackground", "saisiesPlaceholder", "buttonsBackground"};


    private Controleur ctrl;

    private String nomAncienTheme;
    private File fileTheme;
    
    private JPanel pnlNomTheme;
    private JLabel lblNomTheme;
    private JTextField txtNomTheme;

    private JPanel pnlColor;
    private List<JLabel> lstLbl;
    private List<JButton> lstBtn;

    private JPanel panelSud;
    private JButton btnValider;
    private JButton btnAnnuler;

    private HashMap<String, Color> hmColorThemes;


    public PanelCreerTheme(Controleur ctrl)
    {
        this.ctrl = ctrl;
        this.setLayout(new BorderLayout(20, 20));


        /* Enregistrement du nom du thème actuel */
        this.nomAncienTheme = this.ctrl.getThemeUsed();


        /* Récupération des couleurs du thème actuel */
        this.hmColorThemes = this.ctrl.getTheme();


        // TODO : incrémenter le nombre de thème perso créé de 1


        /* Création du fichier du thème personnalisé */
        this.fileTheme = new File(PanelCreerTheme.PATH_THEMES + "theme_perso_" + /* TODO : remplacer par le nombre de thème perso créée */ + ".xml");
        try { this.fileTheme.createNewFile(); } catch (IOException e) { e.printStackTrace(); System.out.println("ERREUR lors de la création du fichier " + "theme_perso_" + /* TODO : remplacer par le nombre de thème perso créée */ + ".xml"); }


        /* Copie du thème utilisé dans le thème en cours de personnalisation */
        File fileThemeUsed  = new File(PanelCreerTheme.PATH_THEMES + "theme_"       + this.ctrl.getThemeUsed   () + ".xml"); // origine
        File fileThemePerso = new File(PanelCreerTheme.PATH_THEMES + "theme_perso_" + /* TODO : remplacer par le nombre de thème perso créée */ + ".xml"); // destination
        
        try { Files.copy(fileThemeUsed.toPath(), fileThemePerso.toPath(), StandardCopyOption.REPLACE_EXISTING); } catch (IOException e) { e.printStackTrace(); System.out.println("ERREUR lors de la copie du fichier " + "theme_" + this.ctrl.getThemeUsed() + ".xml"); }


        /* changement du théme pour appliquer le thème en cours de création */
        this.ctrl.changerTheme("perso_" + /* TODO : remplacer par le nombre de thème perso créée */);



        /*-------------------------*/
        /* Création des composants */
        /*-------------------------*/
        /* Label nom du thème */
        this.pnlNomTheme = new JPanel();
        this.lblNomTheme = new JLabel("Nom du thème : ");
        this.lblNomTheme.setFont(new Font("Liberation Sans", 0, 24));

        /* TexteField nom du thème */
        this.txtNomTheme = new JTextField("Perso " + /* TODO : remplacer par le nombre de thème perso créée */);
        this.txtNomTheme.setFont(new Font("Liberation Sans", 0, 24));
        this.txtNomTheme.setPreferredSize(new Dimension(150, 24));


        /* Panel des couleurs */
        this.pnlColor = new JPanel(new GridLayout(PanelCreerTheme.ENS_LBL_STRING.length, 2, 0, 5));

        this.lstLbl = new ArrayList<JLabel>();
        this.lstBtn = new ArrayList<JButton>();
        for (int i = 0; i < PanelCreerTheme.ENS_LBL_STRING.length; i++)
        {
            this.lstLbl.add(new JLabel (PanelCreerTheme.ENS_LBL_STRING[i]));
            this.lstBtn.add(new JButton("Couleur"));
        }


        /* Panel du bas */
        this.panelSud = new JPanel();

        /* Bouton valider */
        this.btnValider = new JButton("Valider");
        this.btnValider.setPreferredSize(new Dimension(100, 30));

        /* Bouton annuler */
        this.btnAnnuler = new JButton("Annuler");
        this.btnAnnuler.setPreferredSize(new Dimension(100, 30));



        /*----------------------*/
        /* Ajout des composants */
        /*----------------------*/
        /* Panel Nom du thème */
        this.pnlNomTheme.add(this.lblNomTheme);
        this.pnlNomTheme.add(this.txtNomTheme);

        /* Panel des couleurs, Ajout des labels et boutons des couleurs */
        for (int i = 0; i < PanelCreerTheme.LST_CLES.length; i++)
        {
            this.pnlColor.add(this.lstLbl.get(i));
            this.pnlColor.add(this.lstBtn.get(i));
        }

        /* Panel du bas */
        this.panelSud.add(this.btnAnnuler);
        this.panelSud.add(this.btnValider);

        /* Ajout des panels */
        this.add(this.pnlNomTheme, BorderLayout.NORTH);
        this.add(this.pnlColor   , BorderLayout.CENTER);
        this.add(this.panelSud   , BorderLayout.SOUTH);


        /*---------------------*/
        /* Ajout des listeners */
        /*---------------------*/
        /* Boutons valider et annuler */
        this.btnValider.addActionListener(this);
        this.btnAnnuler.addActionListener(this);

        /* Boutons des couleurs */
        for (int i = 0; i < this.lstBtn.size(); i++)
            this.lstBtn.get(i).addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        /* Validé */
        if (e.getSource() == this.btnValider)
        {
            if (true)// TODO : Vérifier que le nom du thème n'est pas déjà utilisé ou vide
            {
                /* Ajout du thème à la menuBarre */
                this.ctrl.ajouterThemePersoOnMenuBarre(/* TODO : remplacer par le nom du thème perso */);

                /* Ferme la fenêtre */
                this.ctrl.disposeFrameCreerTheme();
            }
        }

        /* Annulé */
        if (e.getSource() == this.btnAnnuler)
        {
            this.fileTheme.delete();
            this.ctrl.changerTheme(this.nomAncienTheme);
            this.ctrl.disposeFrameCreerTheme();
        }

        /* Boutons des couleurs */
        for (int i = 0; i < this.lstBtn.size(); i++)
        {
            if (e.getSource() == this.lstBtn.get(i))
            {
                Color color = JColorChooser.showDialog(this, "Choisir une couleur", this.hmColorThemes.get(PanelCreerTheme.LST_CLES[i]));
                this.hmColorThemes.put(PanelCreerTheme.LST_CLES[i], color);
                this.lstBtn.get(i).setBackground(color);

                this.enregistrerCouleur(color, PanelCreerTheme.LST_CLES[i]);

                this.ctrl.appliquerTheme();
            }
        }
    }


    /**
     * enregistre la couleur dans le fichier du thème personnalisé
     */
    public void enregistrerCouleur(Color color, String cle)
    {
        // lire le fichier du theme personnalisé
    }


    /**
     * Applique le thème à tout les composants du panel
     */
    public void appliquerTheme()
    {
        Color backGeneralColor = this.hmColorThemes.get("background");
        Color foreGeneralColor = this.hmColorThemes.get("foreground");
        Color backBtnColor     = this.hmColorThemes.get("buttonsBackground");


        /*-------------------*/
        /* Le Panel lui même */
        /*-------------------*/
        this.setBackground(backGeneralColor);
        this.setForeground(foreGeneralColor);

        /*--------------*/
        /* Nom du thème */
        /*--------------*/
        this.pnlNomTheme.setBackground(backGeneralColor);
        this.pnlNomTheme.setForeground(foreGeneralColor);

        this.lblNomTheme.setBackground(backGeneralColor);
        this.lblNomTheme.setForeground(foreGeneralColor);

        this.txtNomTheme.setBackground(backGeneralColor);
        this.txtNomTheme.setForeground(foreGeneralColor);


        /*--------------------*/
        /* Choix des couleurs */
        /*--------------------*/
        this.pnlColor.setBackground(backGeneralColor);
        this.pnlColor.setForeground(foreGeneralColor);

        for (int i = 0; i < PanelCreerTheme.LST_CLES.length; i++)
        {
            this.lstLbl.get(i).setBackground(backGeneralColor);
            this.lstLbl.get(i).setForeground(foreGeneralColor);

            this.lstBtn.get(i).setBackground(this.hmColorThemes.get(PanelCreerTheme.LST_CLES[i]));
            this.lstBtn.get(i).setForeground(foreGeneralColor);
        }


        /*---------------------------*/
        /* Boutons valider et annulé */
        /*---------------------------*/
        this.panelSud.setBackground(backGeneralColor);
        this.panelSud.setForeground(foreGeneralColor);

        this.btnValider.setBackground(backBtnColor);
        this.btnValider.setForeground(foreGeneralColor);

        this.btnAnnuler.setBackground(backBtnColor);
        this.btnAnnuler.setForeground(foreGeneralColor);
    }
}
