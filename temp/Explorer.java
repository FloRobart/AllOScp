import java.io.*;
import java.util.Date;
import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;  
import javax.swing.tree.*;  


class Explorer extends JPanel implements ActionListener  
{
    JTextArea textArea;
    JTree tree;
    JTable jtb;  
    JScrollPane jsp;  
    JScrollPane jspTable;  
    
    String currDirectory=null;  
    
    final String[] colHeads={"File Name","SIZE(in Bytes)","Date"};  
    String[][]data={{"","","",""}};  
        
    Explorer(String path)  
    {
        textArea=new JTextArea(4,30);
        
        File temp=new File(path);  
        MyMutableTreeNode top = createTree(temp);

        tree = new JTree(top);
        tree.setBackground(Color.BLACK);
        tree.setForeground(Color.WHITE);
        tree.setOpaque(true);

        jsp = new JScrollPane(tree);  
        
        final String[] colHeads={"File Name","SIZE(in Bytes)","Read Only","Hidden"};  
        String[][]data={{"","","","",""}};
        jtb=new JTable(data, colHeads);  
        jspTable=new JScrollPane(jtb);  
        
        setLayout(new BorderLayout());
        add(jsp,BorderLayout.WEST);  

        add(jspTable,BorderLayout.CENTER);
        
        tree.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent me)  
            {  
                doMouseClicked(me);  
            }
        });
    }
    
    public void actionPerformed(ActionEvent ev)  
    {
        remove(jsp);  
        jsp=new JScrollPane(tree);  
        setVisible(false);

        add(jsp,BorderLayout.WEST);  

        tree.addMouseListener(new MouseAdapter()  
        {  
            public void mouseClicked(MouseEvent me)  
            {  
                doMouseClicked(me);  
            }  
        });  
        
        setVisible(true);  
    }  

    
    MyMutableTreeNode createTree(File temp)  
    {  
        MyMutableTreeNode top=new MyMutableTreeNode(temp.getPath());  
        if(!(temp.exists() && temp.isDirectory()))  
            return top;  
        
        fillTree(top,temp.getPath());  
        
        return top;  
    }  
    
    void fillTree(MyMutableTreeNode root, String filename)  
    {  
        File temp=new File(filename);  
        
        if(!(temp.exists() && temp.isDirectory())) { return; }

        File[] filelist=temp.listFiles();
        for(int i=0; i<filelist.length; i++)
        {  
            if(!filelist[i].isDirectory()) { continue; }

            final MyMutableTreeNode tempDmtn=new MyMutableTreeNode(filelist[i].getName());  
            root.add(tempDmtn);  
            final String newfilename = new String(filename+"\\"+filelist[i].getName());  
            Thread t = new Thread()  
            {  
                public void run()  
                {  
                    fillTree(tempDmtn,newfilename);  
                }
            };

            t.start();
        }
    } 
    
    void doMouseClicked(MouseEvent me)  
    {  
        TreePath tp=tree.getPathForLocation(me.getX(),me.getY());  
        if(tp==null) return;  
        
        String s=tp.toString();  
        s=s.replace("[","");  
        s=s.replace("]","");  
        s=s.replace(", ","\\");
        showFiles(s);  
    
    }  
    
    void showFiles(String filename)  
    {  
        File temp=new File(filename);  
        data=new String[][]{{"","","",""}};  
        remove(jspTable);  
        jtb=new JTable(data, colHeads);  
        jspTable=new JScrollPane(jtb);  
        setVisible(false);  
        add(jspTable,BorderLayout.CENTER);  
        setVisible(true);  

        if(!temp.exists()) return;  
        if(!temp.isDirectory()) return;  
        
        //System.out.println(filename);  
        File[] filelist=temp.listFiles();  
        int fileCounter=0;

        data=new String[filelist.length][4];  
        for(int i=0; i<filelist.length; i++)  
        {  
            if(filelist[i].isDirectory())  
                continue;

            data[fileCounter][0]=new String(filelist[i].getName());  
            data[fileCounter][1]=new String(filelist[i].length()+"");  
            data[fileCounter][2]=new String(!filelist[i].canWrite()+"");  
            data[fileCounter][3]=new String(new Date(filelist[i].lastModified())+"");  
            fileCounter++;  
        }
        
        String dataTemp[][]=new String[fileCounter][4];  
        for(int k=0; k<fileCounter; k++)
        {
            dataTemp[k]=data[k];
        }
        data=dataTemp;  
        

        remove(jspTable);  
        jtb=new JTable(data, colHeads);  
        jspTable=new JScrollPane(jtb);  
        setVisible(false);  
        add(jspTable,BorderLayout.CENTER);  
        setVisible(true);  
    }
}  



class ExplorerTest extends JFrame  
{  

    ExplorerTest(String path)  
    {  
        this.setTitle("Windows Exploder");  
        this.add(new Explorer(path));  
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);  
        this.setSize(400,400);  
        this.setVisible(true);  
    }


    public static void main(String[] args)  
    {  
        new ExplorerTest("C:\\Mon_Drive\\IUT\\TP");  
    }
}