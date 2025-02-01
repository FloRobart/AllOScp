package ihm.explorer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.DropMode;
import javax.swing.JTree;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.DefaultTreeModel;

import ihm.menu.popUp.menu.PopUpMenuArbo;
import controleur.Controleur;


/*
 * Ce fichier fait partie du projet All'OScp
 * Copyright (C) 2024 Floris Robart <florobart.github@gmail.com>
 */
public class Explorer extends JTree implements MouseListener, MouseMotionListener
{
    private Controleur ctrl;
    private PopUpMenuArbo popUpMenuArbo;
    private MyTreeCellRenderer mycellRenderer;
    private ExplorerListener explorerListener;

    private boolean isSelectioned;
    private TreePath ancienTpSelectioned;

    private MyMutableTreeNode hovering;


    public Explorer(TreeModel tm, Controleur ctrl)
    {
        super(tm);

        this.ctrl = ctrl;
        this.popUpMenuArbo = new PopUpMenuArbo(this, this.ctrl);
        this.mycellRenderer = new MyTreeCellRenderer(this.ctrl);
        this.explorerListener = new ExplorerListener(this.ctrl, this);

        this.isSelectioned = false;
        this.ancienTpSelectioned = null;

        this.hovering = null;

        this.setEditable(true);
        this.setDragEnabled(true);
        this.setDropMode(DropMode.ON_OR_INSERT);
        this.setTransferHandler(new TreeTransferHandler());
        this.getModel().addTreeModelListener(this.explorerListener);
        this.setCellRenderer(this.mycellRenderer);
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
        Enumeration<TreeNode> enumTreeNode = ((MyMutableTreeNode) this.getModel().getRoot()).breadthFirstEnumeration();
        while(enumTreeNode.hasMoreElements())
        {
            MyMutableTreeNode node = (MyMutableTreeNode) enumTreeNode.nextElement();

            if (!node.isLeaf())
            {
                int row = this.getRowForPath(new TreePath(node.getPath()));
                this.expandRow(row);
            }
        }
    }

    /**
     * Permet de fermer l'ensemble des noeuds de l'arborescence
     */
    public void fermerArborescence()
    {
        List<Integer> lstRow = new ArrayList<Integer>();

        Enumeration<TreeNode> enumTreeNode = ((MyMutableTreeNode) this.getModel().getRoot()).breadthFirstEnumeration();
        while(enumTreeNode.hasMoreElements())
        {
            MyMutableTreeNode node = (MyMutableTreeNode) enumTreeNode.nextElement();
    
            if (node.isLeaf())
                continue;
    
            int row = this.getRowForPath(new TreePath(node.getPath()));
            lstRow.add(row);
        }

        for(int i = lstRow.size()-1; i >= 0; i--)
            this.collapseRow(lstRow.get(i));
    }


    /**
     * Rempli toute l'arborescence (en profondeur)
     * @param root Racine de l'arborescence quand cette méthode est appelée par l'utilisateur
     * @param rootFilePath le chemin absolut du fichier racine. Ce chemin correspond toujours au noeud racine
     */
    public synchronized void addAllNodes(MyMutableTreeNode root, String rootFilePath)  
    {
        File file = new File(rootFilePath);
        if (file.exists() && file.isDirectory())
        {
            List<File> lstFile = new ArrayList<File>();
            for (File f : file.listFiles())
                lstFile.add(f);

            List<File> lstFichier = new ArrayList<File>();
            Collections.sort(lstFile, (File o1, File o2) -> o1.getName().compareTo(o2.getName()));
            for (File f : lstFile)
            {
                if (f.isDirectory())
                {
                    final MyMutableTreeNode TEMP_DMTN = new MyMutableTreeNode(f.getName());
                    root.add(TEMP_DMTN);

                    this.addAllNodes(TEMP_DMTN, rootFilePath + File.separator + f.getName());
                }
                else
                    lstFichier.add(f);
            }

            for (File f : lstFichier)
            {
                final MyMutableTreeNode TEMP_DMTN = new MyMutableTreeNode(f.getName());
                root.add(TEMP_DMTN);

                this.addAllNodes(TEMP_DMTN, rootFilePath + File.separator + f.getName());
            }
        }
    }

    /**
     * Ajoute les noeuds fils à un noeud existant
     * @param node le noeud au quel rajouter les noeuds fils
     * @param filePath le chemin absolut du dossier à ajouter
     */
    public synchronized void addNode(MyMutableTreeNode node, String filePath)
    {
        File file = new File(filePath);
        if (file.exists() && file.isDirectory())
        {
            List<File> lstFile = new ArrayList<File>();
            for (File f : file.listFiles())
                lstFile.add(f);

            Collections.sort(lstFile, (File o1, File o2) -> o1.getName().compareTo(o2.getName()));
            /* Ajout des dossiers */
            for (File f : lstFile)
                if (f.isDirectory())
                    node.add(new MyMutableTreeNode(f.getName()));

            /* Ajout des fichiers */
            for (File f : lstFile)
                if (!f.isDirectory())
                    node.add(new MyMutableTreeNode(f.getName()));
        }
    }

    /**
     * Permet d'ajouter un noeud à l'arborescence
     * @param nodeChildName : nom du noeud à ajouter
     * @param nodeParent : noeud parent du noeud à ajouter
     */
    public synchronized void insertNode(String nodeChildName, TreePath nodeParent)
    {
        /* Permet de récuperer l'index au quel placer le nouveau pour qu'il sois ranger dans l'ordre alphabétique */
        List<MyMutableTreeNode> lstChildNodes = this.ctrl.getChildrenNodes((MutableTreeNode) (nodeParent.getLastPathComponent()));
        lstChildNodes.add(new MyMutableTreeNode(nodeChildName));
        Collections.sort(lstChildNodes, (MyMutableTreeNode o1, MyMutableTreeNode o2) -> o1.toString().compareTo(o2.toString()));
        int index = 0;
        for (index = 0; index < lstChildNodes.size(); index++)
            if (lstChildNodes.get(index).toString().equals(nodeChildName)) break;

        /* Ajoute le noeud à l'arborescence */
        ((DefaultTreeModel) this.getModel()).insertNodeInto(new MyMutableTreeNode(nodeChildName), (MutableTreeNode) (nodeParent.getLastPathComponent()), index);
    }

    /**
     * Permet de supprimer un noeud de l'arborescence
     * @param nodeToRemove : noeud à supprimer
     */
    public synchronized void removeNode(TreeNode nodeToRemove)
    {
        ((DefaultTreeModel) this.getModel()).removeNodeFromParent((MutableTreeNode) (nodeToRemove));
    }



    @Override
    public void mouseClicked(MouseEvent me)
    {
        TreePath tp = this.getPathForLocation(me.getX(),me.getY());

        if(tp != null)
        {
            this.isSelectioned = true;
            if (me.getButton() == MouseEvent.BUTTON3)
            {
                this.setSelectionPath(tp);
                this.ctrl.setSelectionPath(tp);
                this.explorerListener.setOldSelectionedNode((MyMutableTreeNode)tp.getLastPathComponent());
                this.popUpMenuArbo.show(this, me.getX(), me.getY());
            }
            else if (me.getButton() == MouseEvent.BUTTON1)
            {
                this.setSelectionPath(tp);
                this.ctrl.setSelectionPath(tp);
                this.explorerListener.setOldSelectionedNode((MyMutableTreeNode)tp.getLastPathComponent());
                if (me.getClickCount() == 2)
                {
                    this.isSelectioned = false;
                    File fileSelected = this.ctrl.treePathToFile(tp);
                    if (!fileSelected.exists()) { System.out.println("Erreur, le fichier '" + fileSelected.getAbsolutePath() + "' n'existe pas"); }
                    if (fileSelected.isDirectory())
                    {
                        if (((TreeNode)(tp.getLastPathComponent())).isLeaf())
                        {
                            if (fileSelected.list().length != 0)
                            {
                                for (File f : fileSelected.listFiles())
                                    this.insertNode(f.getName(), tp); /* Génére les noeuds fils sur un seul étage */

                                this.expandPath(tp); /* Ouverture du noeud séléctionnée */
                            }
                        }
                        else
                        {
                            if (!this.isExpanded(tp))
                            {
                                // supprime les noeuds fils du noeud séléctionné
                                TreeNode selectionnedNode = (TreeNode)(tp.getLastPathComponent());
                                while(selectionnedNode.getChildCount() != 0)
                                    this.removeNode(selectionnedNode.getChildAt(0));
                            }
                            else
                                this.expandPath(tp); /* Ouverture du noeud séléctionnée */
                        }
                    }
                    else
                    {
                        try
                        {
                            this.ctrl.editFile(fileSelected);
                        }
                        catch (Exception e)
                        {
                            try
                            {
                                this.ctrl.openFile(fileSelected);
                            }
                            catch (Exception ex) { ex.printStackTrace(); }
                        }
                    }

                    this.clearSelection();
                    this.ctrl.setSelectionPath(null);
                    this.isSelectioned = false;
                }
            }
        }
        else
        {
            this.clearSelection();
            this.ctrl.setSelectionPath(null);
            this.isSelectioned = false;
            this.ctrl.setCut(false);
        }
    }

    /**
     * Permet de récuperer l'id du noeud sur le quel la souris est en train de passer
     * @return l'id du noeud sur le quel la souris est en train de passer
     */
    public MyMutableTreeNode getHovering() { return this.hovering; }

    @Override
    public void mouseMoved   (MouseEvent me)
    {
        TreePath tp = this.getPathForLocation(me.getX(),me.getY());

        if (tp != null && !tp.equals(this.ancienTpSelectioned))
        {
            this.hovering = ((MyMutableTreeNode) tp.getLastPathComponent());
            this.ancienTpSelectioned = tp;
            this.repaint();
        }
        
        if (tp == null)
        {
            this.hovering = null;
            this.ancienTpSelectioned = null;
            this.repaint();
        }
    }

    @Override
    public void mouseEntered (MouseEvent me) {}
    @Override
    public void mouseExited  (MouseEvent me) { this.hovering = null; this.ancienTpSelectioned = null; this.repaint(); }
    @Override
    public void mousePressed (MouseEvent me) {}
    @Override
    public void mouseReleased(MouseEvent me) {}
    @Override
    public void mouseDragged (MouseEvent me) {}


    /**
     * Permet d'appliquer le thème à chaque élément de l'aborescence
     */
    public void appliquerTheme()
    {
        this.popUpMenuArbo.appliquerTheme();
        this.mycellRenderer.appliquerTheme();
    }

    /**
     * Permet d'appliquer le langage à chaque élément de l'aborescence
     */
    public void appliquerLangage()
    {
        this.popUpMenuArbo.appliquerLangage();
    }
}