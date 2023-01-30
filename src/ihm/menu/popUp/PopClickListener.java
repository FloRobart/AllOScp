package ihm.menu.popUp;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controleur.Controleur;


public class PopClickListener extends MouseAdapter
{
    private PopUpMenu menu;

    public PopClickListener(Controleur ctrl)
    {
        this.menu = new PopUpMenu(ctrl);
    }

    public void mousePressed(MouseEvent e)
    {
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e)
    {
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e)
    {
        this.menu.show(e.getComponent(), e.getX(), e.getY());
    }

    public void appliquerTheme()
    {
        this.menu.appliquerTheme();
    }
}