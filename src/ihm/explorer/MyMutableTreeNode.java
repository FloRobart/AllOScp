package ihm.explorer;

import java.awt.Color;

import javax.swing.tree.DefaultMutableTreeNode;

public class MyMutableTreeNode extends DefaultMutableTreeNode
{
    private Color foregroundColor;
    
    public MyMutableTreeNode()
    {
        super();
    }

    public MyMutableTreeNode(Object userObject)
    {
        super(userObject);
    }

    public MyMutableTreeNode(Object userObject, Color color)
    {
        super(userObject);
        this.foregroundColor = color;

    }

    public MyMutableTreeNode(Object userObject, boolean allowsChildren)
    {
        super(userObject, allowsChildren);
    }
}
