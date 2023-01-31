package ihm.menu.popUp;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controleur.Controleur;


public class PopClickListener extends MouseAdapter
{
    private PopUpMenuPanelArbo menu;

    public PopClickListener(Controleur ctrl)
    {
        this.menu = new PopUpMenuPanelArbo(ctrl);
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

    /**
     * Appliquer le theme
     */
    public void appliquerTheme()
    {
        this.menu.appliquerTheme();
    }

    /**
     * Applique la langue
     */
    public void appliquerLangage()
    {
        this.menu.appliquerLangage();
    }
}