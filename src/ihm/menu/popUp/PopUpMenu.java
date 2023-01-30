package ihm.menu.popUp;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import controleur.Controleur;

import java.awt.event.*;


public class PopUpMenu extends JPopupMenu implements ActionListener
{
    private Controleur ctrl;

    private JMenuItem newFile;
    private JMenuItem newFolder;

    public PopUpMenu(Controleur ctrl)
    {
        this.ctrl = ctrl;

        /* Création des composants */
        this.newFile   = new JMenuItem("Nouveau fichier");
        this.newFolder = new JMenuItem("Nouveau dossier");

        /* Ajouts des composants */
        this.add(this.newFile  );
        this.add(this.newFolder);

        /* Activations des composants */
        this.newFile  .addActionListener(this);
        this.newFolder.addActionListener(this);
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


    public void appliquerTheme()
    {
        this.setBackground(this.ctrl.getTheme().get("background"));
        this.setForeground(this.ctrl.getTheme().get("foreground"));

        this.newFile.setBackground(this.ctrl.getTheme().get("background"));
        this.newFile.setForeground(this.ctrl.getTheme().get("foreground"));

        this.newFolder.setBackground(this.ctrl.getTheme().get("background"));
        this.newFolder.setForeground(this.ctrl.getTheme().get("foreground"));
    }
}