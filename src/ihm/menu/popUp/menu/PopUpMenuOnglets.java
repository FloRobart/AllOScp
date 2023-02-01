package ihm.menu.popUp.menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import controleur.Controleur;


public class PopUpMenuOnglets extends JPopupMenu implements ActionListener
{
    private Controleur ctrl;

    private JMenuItem nawTab;
    private JMenuItem close;
    private JMenuItem duplicate;
    private JMenuItem rename;

    public PopUpMenuOnglets(Controleur ctrl)
    {

        this.ctrl = ctrl;

        /* Création des composants */
        this.nawTab  = new JMenuItem();
        this.close     = new JMenuItem();
        this.duplicate  = new JMenuItem();
        this.rename     = new JMenuItem();

        /* Ajouts des composants */
        this.add(this.nawTab);
        this.add(this.close   );
        this.add(this.duplicate);
        this.add(this.rename   );

        /* Activations des composants */
        this.nawTab .addActionListener(this);
        this.close    .addActionListener(this);
        this.duplicate .addActionListener(this);
        this.rename    .addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.nawTab)
        {
            this.ctrl.ajouterOnglet();
        }

        if (e.getSource() == this.close)
        {
            this.ctrl.supprimerOnglet();
        }

        if (e.getSource() == this.duplicate)
        {
            //this.ctrl.dupliquerOnglet();
        }

        if (e.getSource() == this.rename)
        {
            String nom = JOptionPane.showInputDialog(this.ctrl.getFramePrincipale(), "Nom de l'onglet");

            if (this.nomOngletValide(nom))
                this.ctrl.renameOnglet(nom);
        }
    }

    /**
     * Vérifie si le nom de l'onglet n'est pas null et ne contient pas que des espaces.
     */
    private boolean nomOngletValide(String nomOnglet)
    {
        if (nomOnglet == null) return false;

		for (int i = 0; i < nomOnglet.length(); i++)
			if (nomOnglet.charAt(i) != ' ') { return true; }

        return false;
    }


    /**
     * Permet d'appliquer le theme
     */
    public void appliquerTheme()
    {
        Color backGeneralColor = this.ctrl.getTheme().get("background");
        Color foreGeneralColor = this.ctrl.getTheme().get("foreground");


        this.setBackground(backGeneralColor);
        this.setForeground(foreGeneralColor);

        this.nawTab.setBackground(backGeneralColor);
        this.nawTab.setForeground(foreGeneralColor);

        this.close.setBackground(backGeneralColor);
        this.close.setForeground(foreGeneralColor);

        this.duplicate.setBackground(backGeneralColor);
        this.duplicate.setForeground(foreGeneralColor);

        this.rename.setBackground(backGeneralColor);
        this.rename.setForeground(foreGeneralColor);
    }

    /**
     * Permet d'appliquer la langue
     */
    public void appliquerLangage()
    {
        HashMap<String, String> PopUpMenuLangage = this.ctrl.getLangage().get("popUpMenu");


        this.nawTab   .setText(PopUpMenuLangage.get("newTab"      ));
        this.close    .setText(PopUpMenuLangage.get("closeTab"    ));
        this.duplicate.setText(PopUpMenuLangage.get("duplicateTab"));
        this.rename   .setText(PopUpMenuLangage.get("rename"      ));
    }
}