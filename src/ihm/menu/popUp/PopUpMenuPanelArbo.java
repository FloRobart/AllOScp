package ihm.menu.popUp;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import controleur.Controleur;

import java.awt.Color;
import java.awt.event.*;
import java.util.HashMap;


public class PopUpMenuPanelArbo extends JPopupMenu implements ActionListener
{
    private Controleur ctrl;

    private JMenuItem newFile;
    private JMenuItem newFolder;
    private JMenuItem delete;
    private JMenuItem copy;
    private JMenuItem cut;
    private JMenuItem paste;
    private JMenuItem rename;
    private JMenuItem properties;

    public PopUpMenuPanelArbo(Controleur ctrl)
    {
        this.ctrl = ctrl;

        /* Création des composants */
        this.newFile    = new JMenuItem();
        this.newFolder  = new JMenuItem();
        this.delete     = new JMenuItem();
        this.copy       = new JMenuItem();
        this.cut        = new JMenuItem();
        this.paste      = new JMenuItem();
        this.rename     = new JMenuItem();
        this.properties = new JMenuItem();

        /* Ajouts des composants */
        this.add(this.newFile  );
        this.add(this.newFolder);
        this.addSeparator();
        this.add(this.delete   );
        this.add(this.copy     );
        this.add(this.cut      );
        this.add(this.paste    );
        this.addSeparator();
        this.add(this.rename   );
        this.add(this.properties);

        /* Activations des composants */
        this.newFile   .addActionListener(this);
        this.newFolder .addActionListener(this);
        this.delete    .addActionListener(this);
        this.copy      .addActionListener(this);
        this.cut       .addActionListener(this);
        this.paste     .addActionListener(this);
        this.rename    .addActionListener(this);
        this.properties.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.newFile)
        {
            System.out.println("nouveau fichier");
        }

        if (e.getSource() == this.newFolder)
        {
            System.out.println("nouveau dossier");
        }
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

        this.newFile.setBackground(backGeneralColor);
        this.newFile.setForeground(foreGeneralColor);

        this.newFolder.setBackground(backGeneralColor);
        this.newFolder.setForeground(foreGeneralColor);

        this.delete.setBackground(backGeneralColor);
        this.delete.setForeground(foreGeneralColor);

        this.copy.setBackground(backGeneralColor);
        this.copy.setForeground(foreGeneralColor);

        this.cut.setBackground(backGeneralColor);
        this.cut.setForeground(foreGeneralColor);

        this.paste.setBackground(backGeneralColor);
        this.paste.setForeground(foreGeneralColor);

        this.rename.setBackground(backGeneralColor);
        this.rename.setForeground(foreGeneralColor);

        this.properties.setBackground(backGeneralColor);
        this.properties.setForeground(foreGeneralColor);
    }

    /**
     * Permet d'appliquer la langue
     */
    public void appliquerLangage()
    {
        HashMap<String, String> PopUpMenuLangage = this.ctrl.getLangage().get("popUpMenu");


        this.newFile   .setText(PopUpMenuLangage.get("newFile"));
        this.newFolder .setText(PopUpMenuLangage.get("newFolder"));
        this.delete    .setText(PopUpMenuLangage.get("delete"));
        this.copy      .setText(PopUpMenuLangage.get("copy"));
        this.cut       .setText(PopUpMenuLangage.get("cut"));
        this.paste     .setText(PopUpMenuLangage.get("paste"));
        this.rename    .setText(PopUpMenuLangage.get("rename"));
        this.properties.setText(PopUpMenuLangage.get("properties"));
    }
}