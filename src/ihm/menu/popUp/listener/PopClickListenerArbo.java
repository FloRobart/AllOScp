package ihm.menu.popUp.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controleur.Controleur;
import ihm.explorer.Explorer;
import ihm.menu.popUp.menu.PopUpMenuArbo;


public class PopClickListenerArbo extends MouseAdapter
{
    private PopUpMenuArbo menu;

    public PopClickListenerArbo(Explorer arbo, Controleur ctrl)
    {
        this.menu = new PopUpMenuArbo(arbo, ctrl);
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