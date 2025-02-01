package ihm.explorer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;


/*
 * Ce fichier fait partie du projet All'OScp
 * Copyright (C) 2024 Floris Robart <florobart.github@gmail.com>
 */
public class TreeTransferHandler extends TransferHandler
{
    DataFlavor nodesFlavor;
    DataFlavor[] flavors = new DataFlavor[1];
    MyMutableTreeNode[] nodesToRemove;

    public TreeTransferHandler()
    {
        try
        {
            this.nodesFlavor = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=\"" + ihm.explorer.MyMutableTreeNode[].class.getName() + "\"");
            this.flavors[0] = this.nodesFlavor;
        } catch(ClassNotFoundException e) { System.out.println("Class not found : " + e.getMessage() + "\nErreur lors de la création du TreeTransferHandler"); }
    }

    public boolean canImport(TransferHandler.TransferSupport support)
    {
        if(!support.isDrop())
            return false;

        support.setShowDropLocation(true);
        if(!support.isDataFlavorSupported(this.nodesFlavor))
            return false;

        /* Vérification pour empécher de déplacé un élément dans lui même */
        JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
        JTree tree = (JTree) support.getComponent();
        int dropRow = tree.getRowForPath(dl.getPath());
        int[] selRows = tree.getSelectionRows();
        for(int i = 0; i < selRows.length; i++)
        {
            if(selRows[i] == dropRow)
                return false;

            MyMutableTreeNode treeNode = (MyMutableTreeNode) tree.getPathForRow(selRows[i]).getLastPathComponent();
            for (TreeNode offspring : Collections.list(treeNode.depthFirstEnumeration()))
                if (tree.getRowForPath(new TreePath(((MyMutableTreeNode)offspring).getPath())) == dropRow)
                    return false;
        }

        return true;
    }


    /**
     * Permet de déplacer les éléments sélectionnés dans l'arborescence
     * @param c : Le composant source
     * @return l'ensemble des éléments déplacés
     */
    public Transferable createTransferable(JComponent c)
    {
        JTree tree = (JTree) c;
        TreePath[] paths = tree.getSelectionPaths();
        if (paths == null)
            return null;

        /* Créez un tableau de nœuds de copies pour le transfert et un autre pour les nœuds qui seront supprimés dans exportDone après un dépôt réussi. */
        List<MyMutableTreeNode> copies   = new ArrayList<MyMutableTreeNode>();
        List<MyMutableTreeNode> toRemove = new ArrayList<MyMutableTreeNode>();
        MyMutableTreeNode firstNode = (MyMutableTreeNode) paths[0].getLastPathComponent();
        HashSet<TreeNode> doneItems = new LinkedHashSet<TreeNode>(paths.length);
        MyMutableTreeNode copy = this.copy(firstNode, doneItems, tree);

        copies.add(copy);
        toRemove.add(firstNode);
        for (int i = 1; i < paths.length; i++)
        {
            MyMutableTreeNode next = (MyMutableTreeNode) paths[i].getLastPathComponent();
            if (doneItems.contains(next))
                continue;

            /* Ne pas autoriser l'ajout de nœuds de niveau supérieur à la liste. */
            if (next.getLevel() < firstNode.getLevel())
            {
                break;
            }
            else if (next.getLevel() > firstNode.getLevel())
            {
                /* Noeud enfant */
                copy.add(copy(next, doneItems, tree));
                /* le nœud contient déjà un enfant */
            }
            else
            {
                /* Frères / soeurs */
                copies.add(copy(next, doneItems, tree));
                toRemove.add(next);
            }

            doneItems.add(next);
        }

        MyMutableTreeNode[] nodes = copies.toArray(new MyMutableTreeNode[copies.size()]);
        nodesToRemove = toRemove.toArray(new MyMutableTreeNode[toRemove.size()]);

        return new NodesTransferable(nodes);
    }


    /**
     * Permet de copier un noeud avec tout ses enfants (s'il en a)
     * @param node : Le noeud à copier
     * @param doneItems : L'ensemble des noeuds déjà copiés
     * @param tree : L'arborescence source
     * @return Le noeud copié
     */
    private MyMutableTreeNode copy(MyMutableTreeNode node, HashSet<TreeNode> doneItems, JTree tree)
    {
        MyMutableTreeNode copy = new MyMutableTreeNode(node);
        doneItems.add(node);

        for (int i=0; i<node.getChildCount(); i++)
            copy.add(copy((MyMutableTreeNode)((TreeNode)node).getChildAt(i), doneItems, tree));

        int row = tree.getRowForPath(new TreePath(copy.getPath()));
        tree.expandRow(row);

        return copy;
    }


    /**
     * Permet de déplacer les éléments sélectionnés dans l'arborescence
     * @param source : Le composant source
     * @param data : Les éléments à déplacer
     * @param action : L'action à effectuer
     */
    protected void exportDone(JComponent source, Transferable data, int action)
    {
        if((action & MOVE) == MOVE)
        {
            JTree tree = (JTree) source;
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

            // Supprimez les nœuds enregistrés dans nodesToRemove dans createTransferable.
            for(int i = 0; i < nodesToRemove.length; i++)
                model.removeNodeFromParent(nodesToRemove[i]);
        }
    }


    /**
     * Permet de définir les actions possibles
     */
    public int getSourceActions(JComponent c)
    {
        return COPY_OR_MOVE;
    }


    /**
     * 
     */
    public boolean importData(TransferHandler.TransferSupport support)
    {
        if(!canImport(support))
            return false;

        // Extraire les données de transfert.
        MyMutableTreeNode[] nodes = null;
        try
        {
            nodes = (MyMutableTreeNode[]) support.getTransferable().getTransferData(nodesFlavor);
        }
        catch(UnsupportedFlavorException ufe) { System.out.println("UnsupportedFlavor : " + ufe.getMessage()); System.out.println("Erreur dans la méthode importData (L186)"); }
        catch(java.io.IOException        ioe) { System.out.println("I/O error : "         + ioe.getMessage()); System.out.println("Erreur dans la méthode importData (L187)"); }

        /* Obtention des informations sur la destination. */
        JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
        int childIndex = dl.getChildIndex();
        TreePath dest = dl.getPath();
        MyMutableTreeNode parent = (MyMutableTreeNode) dest.getLastPathComponent();
        JTree tree = (JTree)support.getComponent();
        DefaultTreeModel model = (DefaultTreeModel)tree.getModel();

        /* Configuration du mode de drop. */
        int index = childIndex;    /* DropMode.INSERT */
        if(childIndex == -1)       /* DropMode.ON     */
            index = parent.getChildCount();

        /* Ajouter des données au modèle. */
        for(int i = 0; i < nodes.length; i++)
            model.insertNodeInto(nodes[i], parent, index++);

        return true;
    }


    /**
     * Permet d'obetenir le nom de la class
     * @return Le nom de la class
     */
    public String toString()
    {
        return getClass().getName();
    }


    /**
     * Class interne.
     * Permet de définir les données à transférer
     */
    public class NodesTransferable implements Transferable
    {
        MyMutableTreeNode[] nodes;

        public NodesTransferable(MyMutableTreeNode[] nodes)
        {
            this.nodes = nodes;
        }

        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException
        {
            if(!isDataFlavorSupported(flavor))
                throw new UnsupportedFlavorException(flavor);

            return this.nodes;
        }

        public DataFlavor[] getTransferDataFlavors()
        {
            return flavors;
        }

        public boolean isDataFlavorSupported(DataFlavor flavor)
        {
            return nodesFlavor.equals(flavor);
        }
    }
}
