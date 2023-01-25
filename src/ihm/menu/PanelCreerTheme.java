package ihm.menu;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.BorderLayout;

import controleur.Controleur;


public class PanelCreerTheme extends JPanel
{
    private static final String PATH_THEMES = "./bin/donnees/themes/";
    private static final String[] ENS_LBL_STRING = new String[] {"Couleur générale du fond", "Couleur générale du texte", "Couleur de mauvaise action", "Couleur de bonne action", "Couleur de fond des titres", "Couleur de fond des zones de saisies", "Couleur du texte par défaut \n des zones de saisie", "Couleur de fond des boutons"};
    private static final String LST_CLES[] = new String[] {"background", "foreground", "disableColor", "enableColor", "titlesBackground", "saisiesBackground", "saisiesPlaceholder", "buttonsBackground"};


    private Controleur ctrl;

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
        this.hmColorThemes = this.ctrl.getTheme();
        this.ctrl.setNbThemePerso((this.ctrl.getNbThemePerso() + 1));
        this.fileTheme = new File(PanelCreerTheme.PATH_THEMES + "theme_perso_" + this.ctrl.getNbThemePerso() + ".xml");
        
        try { this.fileTheme.createNewFile(); } catch (IOException e) { e.printStackTrace(); System.out.println("ERREUR lors de la création du fichier " + "theme_perso_" + this.ctrl.getNbThemePerso() + ".xml"); }

        this.copyTheme();

        this.setLayout(new BorderLayout(20, 20));

        /*-------------------------*/
        /* Création des composants */
        /*-------------------------*/
        this.pnlNomTheme = new JPanel();
        this.lblNomTheme = new JLabel("Nom du thème : ");
        this.lblNomTheme.setFont(new java.awt.Font("Liberation Sans", 0, 24));

        this.txtNomTheme = new JTextField("Perso " + this.ctrl.getNbThemePerso());
        this.txtNomTheme.setFont(new java.awt.Font("Liberation Sans", 0, 24));
        this.txtNomTheme.setPreferredSize(new java.awt.Dimension(150, 24));


        this.pnlColor = new JPanel(new GridLayout(PanelCreerTheme.ENS_LBL_STRING.length, 2, 0, 5));
        this.lstLbl = new ArrayList<JLabel>();
        this.lstBtn = new ArrayList<JButton>();
        for (int i = 0; i < PanelCreerTheme.ENS_LBL_STRING.length; i++)
        {
            this.lstLbl.add(new JLabel (PanelCreerTheme.ENS_LBL_STRING[i]));
            this.lstBtn.add(new JButton("Couleur"));
        }


        this.panelSud = new JPanel();

        this.btnValider = new JButton("Valider");
        this.btnValider.setPreferredSize(new java.awt.Dimension(100, 30));

        this.btnAnnuler = new JButton("Annuler");
        this.btnAnnuler.setPreferredSize(new java.awt.Dimension(100, 30));



        /*----------------------*/
        /* Ajout des composants */
        /*----------------------*/
        this.pnlNomTheme.add(this.lblNomTheme);
        this.pnlNomTheme.add(this.txtNomTheme);

        for (int i = 0; i < PanelCreerTheme.LST_CLES.length; i++)
        {
            this.pnlColor.add(this.lstLbl.get(i));
            this.pnlColor.add(this.lstBtn.get(i));
        }


        this.panelSud.add(this.btnAnnuler);
        this.panelSud.add(this.btnValider);

        this.add(this.pnlNomTheme, BorderLayout.NORTH);
        this.add(this.pnlColor   , BorderLayout.CENTER);
        this.add(this.panelSud   , BorderLayout.SOUTH);


        /*---------------------*/
        /* Ajout des listeners */
        /*---------------------*/

    
        this.appliquerTheme();
    }


    /**
     * Copie le thème utilisé dans le fichier de thème personnalisé en cours de création
     */
    private void copyTheme()
    {
        String themeUsed = this.ctrl.getThemeUsed();

        File fileThemeUsed = new File(PanelCreerTheme.PATH_THEMES + "theme_" + themeUsed + ".xml");
        File fileThemePerso = new File(PanelCreerTheme.PATH_THEMES + "theme_perso_" + this.ctrl.getNbThemePerso() + ".xml");

        try
        {
            Files.copy(fileThemeUsed.toPath(), fileThemePerso.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) { e.printStackTrace(); System.out.println("ERREUR lors de la copie du fichier " + "theme_" + themeUsed + ".xml"); }
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
