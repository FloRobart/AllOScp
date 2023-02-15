package ihm.explorer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.Enumeration;

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

    private TreePath tpSelectionned;
    
    public Explorer(DefaultMutableTreeNode dmtn, Controleur ctrl)
    {
        super(dmtn);

        this.ctrl = ctrl;
        this.popUpMenuArbo = new PopUpMenuArbo(this.ctrl);
        this.tpSelectionned = null;

        this.setDragEnabled(true);
        this.setDropMode(DropMode.ON_OR_INSERT);
        this.setTransferHandler(new TreeTransferHandler());
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);

        this.expandTree(this);


        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }


    /**
     * 
     * @param tree
     */
    private void expandTree(JTree tree)
    {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
        Enumeration<TreeNode> enumTreeNode = root.breadthFirstEnumeration();

        while(enumTreeNode.hasMoreElements())
        {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumTreeNode.nextElement();

            if (node.isLeaf())
                continue;

            int row = tree.getRowForPath(new TreePath(node.getPath()));
            tree.expandRow(row);
        }
    }


    /**
     * Crée l'arborescence à partir d'un fichier
     * @param temp
     * @return
     */
    public static DefaultMutableTreeNode createTree(File temp)  
    {  
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(temp.getPath());  
        if(!(temp.exists() && temp.isDirectory()))  
            return top;  

        Explorer.remplirArbo(top , temp.getPath());

        return top;
    }


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

                Explorer.remplirArbo(tempDmtn,newfilename);
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
            DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) tp.getLastPathComponent();

            if (me.getButton() == MouseEvent.BUTTON3)
            {
                System.out.println("Clic droit sur " +  dmtn.getUserObject());

                this.setSelectionPath(tp);

                this.popUpMenuArbo.show(this, me.getX(), me.getY());
            }
            else if (me.getButton() == MouseEvent.BUTTON1)
            {
                //System.out.println("Clic gauche sur " + dmtn.getUserObject());
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me)
    {
        //System.out.println("pressed --> " + me.getX() + " : " + me.getY());
        if (me.getButton() == MouseEvent.BUTTON1)
        {
            this.tpSelectionned = this.getPathForLocation(me.getX(),me.getY());
            if (this.tpSelectionned != null)
            {
                //DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) this.tpSelectionned.getLastPathComponent();

                //System.out.println(dmtn.getUserObject() + " Séléctionné");
            }
            else
            {
                //System.out.println("aucun élément Séléctionné");
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent me) {}


    @Override
    public void mouseDragged(MouseEvent me)
    {
        //System.out.println("dragged --> " + me.getX() + " : " + me.getY());
    }


    @Override
    public void mouseMoved(MouseEvent me)
    {
        //System.out.println("moved --> " + me.getX() + " : " + me.getY());
    }
}