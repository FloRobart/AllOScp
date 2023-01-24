package ihm.menu;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.JPanel;

import controleur.Controleur;


public class PanelCreerTheme extends JPanel
{
    private static final String PATH_THEMES = "./bin/donnees/themes/";
    private Controleur ctrl;

    private File fileTheme;


    public PanelCreerTheme(Controleur ctrl)
    {
        this.ctrl = ctrl;
        this.ctrl.setNbThemePerso((this.ctrl.getNbThemePerso() + 1));
        this.fileTheme = new File(PanelCreerTheme.PATH_THEMES + "theme_perso_" + this.ctrl.getNbThemePerso() + ".xml");
        
        try { this.fileTheme.createNewFile(); } catch (IOException e) { e.printStackTrace(); System.out.println("ERREUR lors de la création du fichier " + "theme_perso_" + this.ctrl.getNbThemePerso() + ".xml"); }

        this.copyTheme();

        /*-------------------------*/
        /* Création des composants */
        /*-------------------------*/


        /*----------------------*/
        /* Ajout des composants */
        /*----------------------*/



        /*---------------------*/
        /* Ajout des listeners */
        /*---------------------*/
    }


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
        Color backGeneralColor = this.ctrl.getTheme().get("background");
        Color foreGeneralColor = this.ctrl.getTheme().get("foreground");


        /*-------------------*/
        /* Le Panel lui même */
        /*-------------------*/
        this.setBackground(backGeneralColor);
        this.setForeground(foreGeneralColor);


    }
}
