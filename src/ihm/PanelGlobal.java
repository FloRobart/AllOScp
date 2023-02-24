package ihm;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.tree.DefaultMutableTreeNode;

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
        this.panelGauche         = new PanelArborescence  (this.ctrl, new File("G:\\Mon Drive\\Projet_perso\\Test_Sync\\panelGauche"));
        System.out.println("\npanelGlobal\n");
        this.panelDroite         = new PanelArborescence  (this.ctrl, new File("G:\\Mon Drive\\Projet_perso\\Test_Sync\\panelDroite"));
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
     * Ajoute les noeuds fils à un noeud existant
     * @param node le noeud au quel rajouter les noeuds fils
     * @param filePath le chemin absolut du dossier à ajouter
     */
    public void addNode(DefaultMutableTreeNode node, String filePath)
    {
        String rootGauche = this.panelGauche.getRoot().substring(0, 1);
        String rootDroite = this.panelDroite.getRoot().substring(0, 1);

        //System.out.println("rootGauche : " + rootGauche);
        //System.out.println("rootDroite : " + rootDroite);

        Explorer arboGauche = this.panelGauche.getArborescence();
        Explorer arboDroite = this.panelDroite.getArborescence();

        arboGauche.getPathForRow(0);
        arboDroite.getPathForRow(0);

        //System.out.println("arboGauche : " + arboGauche);
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
