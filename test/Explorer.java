import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;


class Explorer extends JPanel  
{
    JTree tree;

    String currentDirectory = null;
        
    public Explorer(String path)  
    {
        DefaultMutableTreeNode top = createTree(new File(path));

        tree = new JTree(top);
        add(tree);
        
        tree.addMouseListener(new MouseAdapter()  
            {  
                public void mouseClicked(MouseEvent me)  
                {  
                    doMouseClicked(me);  
                }  
            }
        );
    }

    
    DefaultMutableTreeNode createTree(File temp)  
    {  
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(temp.getPath());  
        if(!(temp.exists() && temp.isDirectory()))  
            return top;  

        this.fillTree(top , temp.getPath());

        return top;
    }
    
    void fillTree(DefaultMutableTreeNode root, String filename)  
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
                System.out.println("fileName : " + newfilename);

                this.fillTree(tempDmtn,newfilename);
    
            }
        }
    } 
    
    void doMouseClicked(MouseEvent me)  
    {  
        TreePath tp=tree.getPathForLocation(me.getX(),me.getY());  
        System.out.println("tp : " + tp);
    }
}


class ExplorerTest extends JFrame  
{  
  
    ExplorerTest(String path)  
    {  
        super("Windows Exploder ");  
        add(new Explorer(path));  
        setDefaultCloseOperation(EXIT_ON_CLOSE);  
        setSize(400,400);  
        setVisible(true);  
    }  
  
    public static void main(String[] args)  
    {  
        new ExplorerTest("..");  
    }  
}