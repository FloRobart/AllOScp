package ihm.explorer;

import java.awt.Color;

import javax.swing.tree.DefaultMutableTreeNode;

public class MyMutableTreeNode extends DefaultMutableTreeNode
{
    private Color foregroundColor;
    private static int STATIC_ID = 0;
    private int id;
    
    public MyMutableTreeNode()
    {
        super();
        this.id = ++STATIC_ID;
    }

    public MyMutableTreeNode(Object userObject)
    {
        super(userObject);
        this.id = ++STATIC_ID;
    }

    public MyMutableTreeNode(Object userObject, Color color)
    {
        super(userObject);
        this.foregroundColor = color;
        this.id = ++STATIC_ID;
    }

    public MyMutableTreeNode(Object userObject, boolean allowsChildren)
    {
        super(userObject, allowsChildren);
        this.id = ++STATIC_ID;
    }

    public Color getForgroundColor()
    {
        return this.foregroundColor;
    }

    public void setForgroundColor(Color forgroundColor)
    {
        this.foregroundColor = forgroundColor;
    }

    public int getId()
    {
        return this.id;
    }
}
