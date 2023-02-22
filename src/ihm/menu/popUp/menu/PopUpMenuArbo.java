package ihm.menu.popUp.menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import controleur.Controleur;


public class PopUpMenuArbo extends JPopupMenu implements ActionListener
{
    private Controleur ctrl;

    private JMenuItem changeDrive;
    private JMenuItem open;
    private JMenuItem rename;
    private JMenuItem newFile;
    private JMenuItem newFolder;
    private JMenuItem delete;
    private JMenuItem copy;
    private JMenuItem cut;
    private JMenuItem paste;
    private JMenuItem properties;


    public PopUpMenuArbo(Controleur ctrl)
    {
        this.ctrl = ctrl;

        /* Création des composants */
        this.changeDrive = new JMenuItem();
        this.open        = new JMenuItem();
        this.rename      = new JMenuItem();
        this.newFile     = new JMenuItem();
        this.newFolder   = new JMenuItem();
        this.delete      = new JMenuItem();
        this.copy        = new JMenuItem();
        this.cut         = new JMenuItem();
        this.paste       = new JMenuItem();
        this.properties  = new JMenuItem();

        /* Ajouts des composants */
        this.add(this.changeDrive);
        this.add(this.open       );
        this.add(this.rename   );
        this.addSeparator();
        this.add(this.newFile  );
        this.add(this.newFolder);
        this.addSeparator();
        this.add(this.delete   );
        this.add(this.copy     );
        this.add(this.cut      );
        this.add(this.paste    );
        this.addSeparator();
        this.add(this.properties);

        /* Activations des composants */
        this.changeDrive.addActionListener(this);
        this.open       .addActionListener(this);
        this.rename     .addActionListener(this);
        this.newFile    .addActionListener(this);
        this.newFolder  .addActionListener(this);
        this.delete     .addActionListener(this);
        this.copy       .addActionListener(this);
        this.cut        .addActionListener(this);
        this.paste      .addActionListener(this);
        this.properties .addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.changeDrive)
        {
            System.out.println("Changer de lecteur");
        }
        else if (e.getSource() == this.open)
        {
            System.out.println("Ouvrir");
        }
        else if (e.getSource() == this.rename)
        {
            System.out.println("Renommer");
        }
        else if (e.getSource() == this.newFile)
        {
            System.out.println("Nouveau fichier");
        }
        else if (e.getSource() == this.newFolder)
        {
            System.out.println("Nouveau dossier");
        }
        else if (e.getSource() == this.delete)
        {
            System.out.println("Supprimer");
        }
        else if (e.getSource() == this.copy)
        {
            System.out.println("Copier");
        }
        else if (e.getSource() == this.cut)
        {
            System.out.println("Couper");
        }
        else if (e.getSource() == this.paste)
        {
            System.out.println("Coller");
        }
        else if (e.getSource() == this.properties)
        {
            System.out.println("Propriétés");
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

        this.changeDrive.setBackground(backGeneralColor);
        this.changeDrive.setForeground(foreGeneralColor);

        this.open.setBackground(backGeneralColor);
        this.open.setForeground(foreGeneralColor);

        this.rename.setBackground(backGeneralColor);
        this.rename.setForeground(foreGeneralColor);

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

        this.properties.setBackground(backGeneralColor);
        this.properties.setForeground(foreGeneralColor);
    }

    /**
     * Permet d'appliquer la langue
     */
    public void appliquerLangage()
    {
        HashMap<String, String> PopUpMenuLangage = this.ctrl.getLangage().get("popUpMenu");


        this.changeDrive.setText(PopUpMenuLangage.get("changeDrive"));
        this.open       .setText(PopUpMenuLangage.get("open"));
        this.rename     .setText(PopUpMenuLangage.get("rename"));
        this.newFile    .setText(PopUpMenuLangage.get("newFile"));
        this.newFolder  .setText(PopUpMenuLangage.get("newFolder"));
        this.delete     .setText(PopUpMenuLangage.get("delete"));
        this.copy       .setText(PopUpMenuLangage.get("copy"));
        this.cut        .setText(PopUpMenuLangage.get("cut"));
        this.paste      .setText(PopUpMenuLangage.get("paste"));
        this.properties .setText(PopUpMenuLangage.get("properties"));
    }
}