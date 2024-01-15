package ihm;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import ihm.explorer.MyMutableTreeNode;
import controleur.Controleur;

import javax.swing.tree.TreePath;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.awt.BorderLayout;

import ihm.explorer.Explorer;


public class PanelTab extends JPanel
{
    private Controleur          ctrl;

    private PanelFonctionGlobal panelFonctionGlobal;
    private PanelArborescence   panelGauche;
    private PanelArborescence   panelDroite;
    private JSplitPane          panelSpliter;

    private int weight;
    private int height;


    public PanelTab(Controleur ctrl, int widthParent, int indexOnglet, File fileGauche, File fileDroite)
    {
        this.ctrl = ctrl;

        /*--------------------------*/
        /* Créations des composants */
        /*--------------------------*/
        /* Ce Panel */
        this.setLayout(new BorderLayout());
        this.weight = this.getWidth();
        this.height = this.getHeight();

        /* Autres Panels */
        this.panelFonctionGlobal = new PanelFonctionGlobal(this.ctrl);
        this.panelGauche         = new PanelArborescence  (this.ctrl, fileGauche);

        this.panelDroite         = new PanelArborescence  (this.ctrl, fileDroite);
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
     * Permet d'ajouter un noeud à l'arborescence
     * @param nodeChildName : nom du noeud à ajouter
     * @param nodeParent : noeud parent du noeud à ajouter
     */
    public synchronized void addNode(String nodeChildName, TreePath nodeParent)
    {
        this.getPanelArborescence(this.ctrl.treePathToFile(nodeParent).getAbsolutePath()).addNode(nodeChildName, nodeParent);
    }

    /**
     * Permet de supprimer un noeud de l'arborescence
     * @param node : noeud à supprimer
     * @param filePath : chemin absolut du fichier ou du dossier à supprimer
     */
    public void removeNode(MyMutableTreeNode node, String filePath)
    {
        this.getPanelArborescence(filePath).removeNode(node);
    }

    /**
     * Permet de récupérer le panel de l'arborescence qui contenue le fichier ou le dossier passé en paramètre
     * @param filePath : chemin absolut du fichier ou du dossier
     * @return PanelArborescence : panel de l'arborescence qui contien le fichier ou le dossier passé en paramètre
     */
    private PanelArborescence getPanelArborescence(String filePath)
    {
        String fileParent = filePath.substring(0, filePath.lastIndexOf(File.separator));

        Explorer arborescence = this.panelGauche.getArborescence();
        for (int i = 0; i < arborescence.getRowCount(); i++)
        {
            String path = "";
            for (Object o : arborescence.getPathForRow(i).getPath())
                path += o + File.separator;

            path = path.substring(0, path.length()-1);

            if (path.equals(fileParent))
            {
                System.out.println(filePath + " est dans le panel de gauche");
                return this.panelGauche;
            }
        }

        arborescence = this.panelDroite.getArborescence();
        for (int i = 1; i < arborescence.getRowCount(); i++)
        {
            String path = "";
            for (Object o : arborescence.getPathForRow(i).getPath())
                path += o + File.separator;

            path = path.substring(0, path.length()-1);

            if (path.equals(fileParent))
            {
                System.out.println(filePath + " est dans le panel droite");
                return this.panelDroite;
            }
        }

        return null;
    }


    /**
     * Permet de récupérer l'arborescence
     * @return Explorer : arborescence
     */
    public Explorer getArborescence(String panel)
    {
        if(panel.equals("gauche"))
            return this.panelGauche.getArborescence();
        else if(panel.equals("droite"))
            return this.panelDroite.getArborescence();

        throw new IllegalArgumentException("Ligne 144, PanelGlobal\nErreur dans le nom du panel (seulement gauche ou droite autorisé)");
    }

    /**
     * Permet de récupérer le chemin du dossier racine
     * @return String : chemin absolut du dossier racine
     */
    public String getRoot(String panel)
    {
        if(panel.equals("gauche"))
            return this.panelGauche.getRoot();
        else if(panel.equals("droite"))
            return this.panelDroite.getRoot();

        throw new IllegalArgumentException("Ligne 158, PanelGlobal\nErreur dans le nom du panel (seulement gauche ou droite autorisé)");
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

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        this.panelSpliter.setDividerLocation(this.getWidth()/2);

        if (this.getWidth() != this.weight || this.getHeight() != this.height)
            this.panelSpliter.setDividerLocation(this.getWidth()/2);
    }
}
