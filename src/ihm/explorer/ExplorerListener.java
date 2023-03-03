package ihm.explorer;

import java.util.List;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import controleur.Controleur;


public class ExplorerListener implements TreeModelListener 
{
    private Controleur ctrl;
    private Explorer explorer;
    private DefaultMutableTreeNode oldSelectionedNode;


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
    public void setOldSelectionedNode(DefaultMutableTreeNode oldSelectionedNode)
    {
        System.out.println("New old name : " + oldSelectionedNode.toString());
        if (this.oldSelectionedNode == null)
        {
            this.oldSelectionedNode = oldSelectionedNode;
        }
        else
        {
            System.out.println("Old old name : " + this.oldSelectionedNode.toString());
            if (!(this.oldSelectionedNode.toString()).equals(oldSelectionedNode.toString()))
            {
                System.out.println("Old old name : " + this.oldSelectionedNode.toString() + " --> " + oldSelectionedNode.toString());
                this.oldSelectionedNode = oldSelectionedNode;
            }
        }

        System.out.println();
    }

    @Override
    public void treeNodesChanged(TreeModelEvent tme)
    {
        System.out.println("Node changed --> Old name : " + this.oldSelectionedNode.toString());
        System.out.println("Node changed --> New name : " + this.explorer.getSelectionPath().getLastPathComponent().toString());
    }

    @Override
    public void treeNodesInserted(TreeModelEvent tme){}
    @Override
    public void treeNodesRemoved(TreeModelEvent tme){}
    @Override
    public void treeStructureChanged(TreeModelEvent tme){}

    
}
