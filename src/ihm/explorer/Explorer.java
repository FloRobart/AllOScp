package ihm.explorer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DropMode;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.DefaultTreeModel;

import controleur.Controleur;
import ihm.menu.popUp.menu.PopUpMenuArbo;



public class Explorer extends JTree implements MouseListener, MouseMotionListener
{
    private Controleur ctrl;
    private PopUpMenuArbo popUpMenuArbo;


    
    public Explorer(TreeModel tm, Controleur ctrl)
    {
        super(tm);

        this.ctrl = ctrl;
        this.popUpMenuArbo = new PopUpMenuArbo(this, this.ctrl);

        this.setEditable(true);
        this.setDragEnabled(true);
        this.setDropMode(DropMode.ON_OR_INSERT);
        this.setTransferHandler(new TreeTransferHandler());
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);

        this.setRootVisible(true);
        this.setShowsRootHandles(false);


        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }


    /**
     * Permet d'ouvrir l'ensemble des noeuds de l'arborescence
     */
    public void ouvrirArborescence()
    {
        Enumeration<TreeNode> enumTreeNode = ((DefaultMutableTreeNode) this.getModel().getRoot()).breadthFirstEnumeration();
        while(enumTreeNode.hasMoreElements())
        {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumTreeNode.nextElement();

            if (node.isLeaf())
                continue;

            int row = this.getRowForPath(new TreePath(node.getPath()));
            this.expandRow(row);
        }
    }

    /**
     * Permet de fermer l'ensemble des noeuds de l'arborescence
     */
    public void fermerArborescence()
    {
        List<Integer> lstRow = new ArrayList<Integer>();

        Enumeration<TreeNode> enumTreeNode = ((DefaultMutableTreeNode) this.getModel().getRoot()).breadthFirstEnumeration();
        while(enumTreeNode.hasMoreElements())
        {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumTreeNode.nextElement();
    
            if (node.isLeaf())
                continue;
    
            int row = this.getRowForPath(new TreePath(node.getPath()));
            lstRow.add(row);
        }

        for(int i = lstRow.size()-1; i >= 0; i--)
            this.collapseRow(lstRow.get(i));
    }


    /**
     * Rempli toute l'arborescence
     * @param node le noeud au quel rajouter les noeuds fils
     * @param filePath le chemin absolut du fichier
     */
    public synchronized void addAllNodes(DefaultMutableTreeNode node, String filePath)  
    {
        File file = new File(filePath);
        if (file.exists() && file.isDirectory())
        {
            for (File f : file.listFiles())
            {
                final DefaultMutableTreeNode TEMP_DMTN = new DefaultMutableTreeNode(f.getName());
                node.add(TEMP_DMTN);

                this.addAllNodes(TEMP_DMTN, filePath + File.separator + f.getName());
            }
        }
    }

    /**
     * Ajoute les noeuds fils à un noeud existant
     * @param node le noeud au quel rajouter les noeuds fils
     * @param filePath le chemin absolut du dossier à ajouter
     */
    public synchronized void addNodeTemp(DefaultMutableTreeNode node, String filePath)
    {
        File file = new File(filePath);
        if (file.exists() && file.isDirectory())
            for (File f : file.listFiles())
                node.add(new DefaultMutableTreeNode(f.getName()));
    }

    public synchronized void addNode(String nodeChildName, TreePath nodeParent)
    {
        ((DefaultTreeModel) this.getModel()).insertNodeInto(new DefaultMutableTreeNode(nodeChildName), (MutableTreeNode) (nodeParent.getLastPathComponent()), ((MutableTreeNode) (nodeParent.getLastPathComponent())).getChildCount());
    }

    /**
     * Permet de supprimer un noeud de l'arborescence
     * @param node : noeud à supprimer
     * @param filePath : chemin absolut du fichier ou du dossier à supprimer
     */
    public synchronized void removeNode(TreePath node)
    {
        ((DefaultTreeModel) this.getModel()).removeNodeFromParent((MutableTreeNode) (node.getLastPathComponent()));
    }



    @Override
    public void mouseClicked(MouseEvent me)
    {
        TreePath tp = this.getPathForLocation(me.getX(),me.getY());

        if(tp != null)
        {
            if (me.getButton() == MouseEvent.BUTTON3)
            {
                this.setSelectionPath(tp);

                this.popUpMenuArbo.show(this, me.getX(), me.getY());
            }
            else if (me.getButton() == MouseEvent.BUTTON1)
            {
                this.setSelectionPath(tp);

                if (me.getClickCount() == 2)
                {
                    String pathFileSelected = "";
                    for (Object o : tp.getPath())
                        pathFileSelected += o.toString() + File.separator;

                    pathFileSelected = pathFileSelected.substring(0, pathFileSelected.length()-1);

                    File fileSelected = new File(pathFileSelected);
                    if (fileSelected.exists())
                    {
                        if (fileSelected.isDirectory())
                        {
                            if (((TreeNode)(tp.getLastPathComponent())).isLeaf())
                            {
                                if (fileSelected.list().length != 0)
                                {
                                    this.addNodeTemp((DefaultMutableTreeNode) tp.getLastPathComponent(), pathFileSelected); /* Génére les noeuds fils sur un seul étage */

                                    this.expandPath(tp); /* Ouverture du noeud séléctionnée */

                                    //this.ctrl.addFolderListener(pathFileSelected); /* Ajout du listener sur le dossier sélectionner */
                                }
                            }
                            else
                            {
                                if (this.isExpanded(tp))
                                {
                                    this.expandPath(tp);
                                    //if (fileSelected.isDirectory())
                                        //this.ctrl.addFolderListener(pathFileSelected);
                                }
                                else
                                {
                                    this.collapsePath(tp);
                                    //if (fileSelected.isDirectory())
                                        //this.ctrl.removeFolderListener(pathFileSelected);
                                }
                            }
                        }
                        else
                        {
                            File file = new File(pathFileSelected);
                            if (file.exists())
                            {
                                try { Desktop.getDesktop().open(file); }
                                catch (IOException ex) { Logger.getLogger(Explorer.class.getName()).log(Level.SEVERE, "Erreur lors de l'ouverture du fichier '" + pathFileSelected + "'", ex); ex.printStackTrace(); System.out.println("Erreur lors de l'ouverture du fichier '" + pathFileSelected + "'"); }
                            }
                        }
                    }
                }
            }
        }
        else
        {
            this.clearSelection();
            this.ctrl.setCut(false);
        }
    }

    @Override
    public void mouseEntered (MouseEvent me) {}
    @Override
    public void mouseExited  (MouseEvent me) {}
    @Override
    public void mousePressed (MouseEvent me) {}
    @Override
    public void mouseReleased(MouseEvent me) {}
    @Override
    public void mouseDragged (MouseEvent me) {}
    @Override
    public void mouseMoved   (MouseEvent me) {}


    /**
     * Permet d'appliquer le thème à chaque élément de l'aborescence
     */
    public void appliquerTheme()
    {
        this.popUpMenuArbo.appliquerTheme();
    }

    /**
     * Permet d'appliquer le langage à chaque élément de l'aborescence
     */
    public void appliquerLangage()
    {
        this.popUpMenuArbo.appliquerLangage();
    }
}