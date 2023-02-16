package ihm;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.Color;
import java.io.File;
import java.awt.BorderLayout;

import controleur.Controleur;
import ihm.explorer.Explorer;


public class PanelGlobal extends JPanel
{
    private Controleur          ctrl;

    private PanelFonctionGlobal panelFonctionGlobal;
    private PanelArborescence   panelGauche;
    private PanelArborescence   panelDroite;
    private JSplitPane          panelSpliter;


    public PanelGlobal(Controleur ctrl, int widthParent, int indexOnglet)
    {
        this.ctrl = ctrl;

        /*--------------------------*/
        /* Créations des composants */
        /*--------------------------*/
        /* Ce Panel */
        this.setLayout(new BorderLayout());

        /* Autres Panels */
        this.panelFonctionGlobal = new PanelFonctionGlobal(this.ctrl, indexOnglet);
        this.panelGauche         = new PanelArborescence  (this.ctrl, new File("G:\\Mon Drive\\Projet_perso\\Sync_files_and_folders\\testArboPanelGauche"));
        this.panelDroite         = new PanelArborescence  (this.ctrl, new File("G:\\Mon Drive\\Projet_perso\\Sync_files_and_folders\\testArboPanelDroite"));
        this.panelSpliter        = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.panelGauche, this.panelDroite);
        this.panelSpliter.setOneTouchExpandable(true);
		this.panelSpliter.setContinuousLayout(true);
		this.panelSpliter.setDividerLocation(widthParent/2);


        /*-----------------------*/
        /* Ajouts des composants */
        /*-----------------------*/
        /* Panels */
        this.add(this.panelFonctionGlobal, BorderLayout.NORTH );
        this.add(this.panelSpliter       , BorderLayout.CENTER);


        /*---------------------------*/
        /* Activtions des composants */
        /*---------------------------*/
        //this.addMouseListener(new PopClickListener());
    }


    /**
     * Permet de récupérer l'arborescence
     * @return Explorer : arborescence
     */
    public Explorer getArborescence(String panel)
    {
        if(panel.equals("gauche"))
        {
            return this.panelGauche.getArborescence();
        }
        
        if(panel.equals("droite"))
        {
            return this.panelDroite.getArborescence();
        }

        throw new IllegalArgumentException("Ligne 70, PanelGlobal\nErreur dans le nom du panel (seulement gauche ou droite autorisé)");
    }


    /**
     * Permet d'appliquer le thème à chaque élément de l'ihm qui en à besoins
     */
    public void appliquerTheme()
    {
        Color backGeneralColor = this.ctrl.getTheme().get("background");
        Color foreGeneralColor = this.ctrl.getTheme().get("foreground");

        /* Ce Panel */
        this.setBackground(backGeneralColor);
        this.setForeground(foreGeneralColor);
        
        /* Panel Spliter */
        this.panelSpliter.setBackground(backGeneralColor);
        this.panelSpliter.setForeground(foreGeneralColor);

        /* Autres Panels */
        this.panelFonctionGlobal.appliquerTheme();
        this.panelGauche.appliquerTheme();
        this.panelDroite.appliquerTheme();
    }

    /**
     * Permet d'appliquer le langage à chaque élément du panel
     */
    public void appliquerLangage()
    {
        this.panelFonctionGlobal.appliquerLangage();
        this.panelGauche.appliquerLangage();
        this.panelDroite.appliquerLangage();
    }
}
