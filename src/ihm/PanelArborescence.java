package ihm;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import java.awt.BorderLayout;

import java.awt.Color;
import java.io.File;
import java.util.Enumeration;

import controleur.Controleur;
import ihm.explorer.Explorer;
import ihm.explorer.MyCellRenderer;


public class PanelArborescence extends JPanel
{
    private Controleur ctrl;
    private File rootFile;

    private JScrollPane scrollPane;

    private Explorer    arborescence;
    private MyCellRenderer mycellRenderer;


    public PanelArborescence(Controleur ctrl, File rootFile)
    {
        this.ctrl     = ctrl;
        this.rootFile = rootFile;

        /*--------------------------*/
        /* Créations des composants */
        /*--------------------------*/
        /* Ce Panel */
        this.setLayout(new BorderLayout());

        /* ScrollPane */
        this.scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setSize(200, 400);

        /* Arborescence */
        DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode(this.rootFile.getAbsolutePath());
        this.arborescence = new Explorer(new DefaultTreeModel(treeRoot), this.ctrl);
        this.arborescence.addAllNodes(treeRoot, this.rootFile.getAbsolutePath());
        this.arborescence.ouvrirArborescence();

        //this.ctrl.addFolderListener(this.rootFile.getAbsolutePath());


        /* Adaptation de l'arborescence */
        this.mycellRenderer = new MyCellRenderer(ctrl);
        this.arborescence.setCellRenderer(this.mycellRenderer);


        /*-----------------------*/
        /* Ajouts des composants */
        /*-----------------------*/
        /* ScrollPane */
        this.scrollPane.setViewportView(this.arborescence);

        /* Ce Panel */
        this.add(this.scrollPane);


        /*---------------------------*/
        /* Activtions des composants */
        /*---------------------------*/
    }


    /**
     * Permet de récupérer l'arborescence
     * @return Explorer : arborescence
     */
    public Explorer getArborescence()
    {
        return this.arborescence;
    }

    /**
     * Permet de récupérer le chemin du dossier racine
     * @return String : chemin absolut du dossier racine
     */
    public String getRoot()
    {
        return this.rootFile.getAbsolutePath();
    }

    /**
     * Permet de récupérer le fichier racine
     * @return File : fichier racine
     */
    public File getRootFile()
    {
        return this.rootFile;
    }

    /**
     * Permet d'ajouter un noeud à l'arborescence
     * @param nodeChildName : nom du noeud à ajouter
     * @param nodeParent : noeud parent du noeud à ajouter
     */
    public synchronized void addNode(String nodeChildName, TreePath nodeParent)
    {
        this.arborescence.addNode(nodeChildName, nodeParent);
    }

    /**
     * Permet de supprimer un noeud de l'arborescence
     * @param node : noeud à supprimer
     * @param filePath : chemin absolut du fichier ou du dossier à supprimer
     */
    public void removeNode(DefaultMutableTreeNode node)
    {
        //this.arborescence.removeNode(node);
    }


    /**
     * Permet d'appliquer le thème à chaque élément du panel
     */
    public void appliquerTheme()
    {
        Color backGeneralColor = this.ctrl.getTheme().get("background");
        Color foreGeneralColor = this.ctrl.getTheme().get("foreground");

        this.setBackground(backGeneralColor);
        this.setForeground(foreGeneralColor);

        /* ScrollPane */
        this.scrollPane.setBackground(backGeneralColor);
        this.scrollPane.setForeground(foreGeneralColor);

        this.scrollPane.getViewport().setBackground(backGeneralColor);
        this.scrollPane.getViewport().setForeground(foreGeneralColor);

        this.scrollPane.getHorizontalScrollBar().setBackground(backGeneralColor);
        this.scrollPane.getVerticalScrollBar().setBackground(backGeneralColor);

        /* Arborescence */
        this.arborescence.setBackground(backGeneralColor);
        this.arborescence.setForeground(foreGeneralColor);
        this.arborescence.appliquerTheme();
        this.mycellRenderer.appliquerTheme();
    }


    /**
     * Permet d'appliquer le langage à chaque élément du panel
     */
    public void appliquerLangage()
    {
        this.arborescence.appliquerLangage();
    }
}
