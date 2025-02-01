package ihm.explorer;

import java.io.File;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import controleur.Controleur;


/*
 * Ce fichier fait partie du projet All'OScp
 * Copyright (C) 2024 Floris Robart <florobart.github@gmail.com>
 */
public class ExplorerListener implements TreeModelListener 
{
    private Controleur ctrl;
    private Explorer explorer;
    private String oldSelectionedNode;


    public ExplorerListener(Controleur ctrl, Explorer explorer)
    {
        super();
        this.ctrl = ctrl;
        this.explorer = explorer;
    }

    /**
     * Permet sauvegarder le noeud selectionner.
     * Cela permet d'avoir l'ancien nom d'un noeud avant qu'il soit renommer
     * @param oldSelectionedNode
     */
    public void setOldSelectionedNode(MyMutableTreeNode oldSelectionedNode)
    {
        this.oldSelectionedNode = oldSelectionedNode.toString();
    }

    @Override
    public void treeNodesChanged(TreeModelEvent tme)
    {
        File file = new File(this.ctrl.treePathToFile(this.explorer.getSelectionPath()).getParent() + File.separator + this.oldSelectionedNode);
        this.ctrl.renameFile(file, this.explorer.getSelectionPath().getLastPathComponent().toString());
    }

    @Override
    public void treeNodesInserted(TreeModelEvent tme){}
    @Override
    public void treeNodesRemoved(TreeModelEvent tme){}
    @Override
    public void treeStructureChanged(TreeModelEvent tme){}

    
}
