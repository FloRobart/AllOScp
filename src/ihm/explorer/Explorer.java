package ihm.explorer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.DropMode;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import controleur.Controleur;
import ihm.menu.popUp.menu.PopUpMenuArbo;



public class Explorer extends JTree implements MouseListener, MouseMotionListener
{
    private Controleur ctrl;
    private PopUpMenuArbo popUpMenuArbo;


    
    public Explorer(DefaultMutableTreeNode dmtn, Controleur ctrl)
    {
        super(dmtn);

        this.ctrl = ctrl;
        this.popUpMenuArbo = new PopUpMenuArbo(this.ctrl);

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
     * Crée l'arborescence à partir d'un fichier
     * @param root le fichier à partir duquel on crée l'arborescence
     * @return 
     */
    public static DefaultMutableTreeNode createTree(File root)  
    {  
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(root.getPath());  
        if(!(root.exists() && root.isDirectory()))  
            return top;  

        Explorer.remplirArbo(top , root.getPath());

        return top;
    }


    /**
     * Rempli l'arborescence
     * @param root le noeud racine
     * @param filename le nom du fichier
     */
    private static void remplirArbo(DefaultMutableTreeNode root, String filename)  
    {
        File temp = new File(filename);  
        
        if(temp.exists() && temp.isDirectory())
        {
            File[] filelist = temp.listFiles();
            
            for(int i=0; i < filelist.length; i++)  
            {
                final DefaultMutableTreeNode tempDmtn = new DefaultMutableTreeNode(filelist[i].getName());  
                root.add(tempDmtn);
                final String newfilename = new String(filename+"\\"+filelist[i].getName());

                Explorer.remplirArbo(tempDmtn, newfilename);
            }
        }
    }



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
            }
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
}