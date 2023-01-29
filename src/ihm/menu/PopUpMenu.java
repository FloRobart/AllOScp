package ihm.menu;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;


public class PopUpMenu extends JPopupMenu
{
    private JMenuItem anItem;

    public PopUpMenu()
    {
        anItem = new JMenuItem("Click Me!");
        add(anItem);
    }
}