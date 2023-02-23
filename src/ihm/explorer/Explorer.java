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
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

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
    //public static DefaultMutableTreeNode createTree(File root)  
    //{  
    //    DefaultMutableTreeNode top = new DefaultMutableTreeNode(root.getPath());  
    //    if(!(root.exists() && root.isDirectory()))  
    //        return top;
    //
    //    Explorer.remplirArbo(root.getPath());
    //
    //    return top;
    //}


    //private static void setRoot(DefaultMutableTreeNode root, String fileRootPath)
    //{
    //    File file = new File(fileRootPath);  
    //    
    //    System.out.println("root     : '" + root         + "'");
    //    System.out.println("fileRoot : '" + fileRootPath + "'\n");
    //    if (file.exists())
    //    {
    //        if(file.isDirectory())
    //        {
    //            File[] filelist = file.listFiles();
    //            
    //            for(int i=0; i < filelist.length; i++)  
    //            {
    //                final DefaultMutableTreeNode TEMP_DMTN = new DefaultMutableTreeNode(filelist[i].getName());
    //                root.add(TEMP_DMTN);
    //
    //                Explorer.remplirArbo(fileRootPath + File.separator + filelist[i].getName());
    //            }
    //        }
    //        else
    //        {
    //            
    //        }
    //    }
    //}


    /**
     * Rempli l'arborescence
     * @param node le noeud au quel rajouter les noeuds fils
     * @param filePath le chemin absolut du fichier
     */
    public void remplirArboRec(DefaultMutableTreeNode node, String filePath)  
    {
        File file = new File(filePath);
        if (file.exists() && file.isDirectory())
        {
            for (File f : file.listFiles())
            {
                final DefaultMutableTreeNode TEMP_DMTN = new DefaultMutableTreeNode(f.getName());
                node.add(TEMP_DMTN);

                this.remplirArboRec(TEMP_DMTN, filePath + File.separator + f.getName());
            }
        }
    }

    /**
     * Rempli l'arborescence
     * @param node le noeud au quel rajouter les noeuds fils
     * @param filePath le chemin absolut du fichier
     */
    public void remplirArbo(DefaultMutableTreeNode node, String filePath)  
    {
        File file = new File(filePath);
        if (file.exists() && file.isDirectory())
            for (File f : file.listFiles())
                node.add(new DefaultMutableTreeNode(f.getName()));
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

                // si double click
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
                            // si le noeud est une feuille
                            if (((TreeNode)(tp.getLastPathComponent())).isLeaf())
                            {
                                if (fileSelected.list().length != 0)
                                {
                                    // remplir l'arborescence a partir du fichier selectionné
                                    this.remplirArbo((DefaultMutableTreeNode) tp.getLastPathComponent(), pathFileSelected);

                                    // ouvrir le noeud
                                    this.expandPath(tp);
                                }
                            }
                            else
                            {
                                if (this.isExpanded(tp))
                                    this.expandPath(tp);
                                else
                                    this.collapsePath(tp);
                            }
                        }
                        else
                        {
                            System.out.println("ouvrire le fichier : '" + pathFileSelected + "'");
                        }
                    }
                }
            }
        }
        else
        {
            this.clearSelection();
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