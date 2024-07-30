package ihm.menu.popUp.menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;

import ihm.explorer.Explorer;
import controleur.Controleur;


/*
 * Ce fichier fait partie du projet All'OScp
 * Copyright (C) 2024 Floris Robart <florobart.github@gmail.com>
 */
public class PopUpMenuArbo extends JPopupMenu implements ActionListener
{
    private Controleur ctrl;

    private Explorer explorer;

    private JMenuItem changeDrive;
    private JMenuItem open;
    private JMenuItem openWith;
    private JMenuItem edit;
    private JMenuItem rename;
    private JMenuItem newFile;
    private JMenuItem newFolder;
    private JMenuItem delete;
    private JMenuItem copy;
    private JMenuItem copyPath;
    private JMenuItem cut;
    private JMenuItem paste;
    private JMenuItem properties;
    private JMenuItem refresh;


    public PopUpMenuArbo(Explorer arbo, Controleur ctrl)
    {
        this.ctrl = ctrl;

        this.explorer = arbo;

        /* Création des composants */
        this.changeDrive = new JMenuItem();
        this.open        = new JMenuItem();
        this.openWith    = new JMenuItem();
        this.edit        = new JMenuItem();
        this.rename      = new JMenuItem();
        this.newFile     = new JMenuItem();
        this.newFolder   = new JMenuItem();
        this.delete      = new JMenuItem();
        this.copy        = new JMenuItem();
        this.copyPath    = new JMenuItem();
        this.cut         = new JMenuItem();
        this.paste       = new JMenuItem();
        this.properties  = new JMenuItem();
        this.refresh     = new JMenuItem();


        /* Ajout des accélérators */
        // TODO : regler le probleme des raccourcis clavier
        //this.changeDrive.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
        this.open       .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        this.openWith   .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        this.rename     .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
        //this.newFile    .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        //this.newFolder  .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        this.delete     .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        this.copy       .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        this.cut        .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        this.paste      .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        this.properties .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
        this.refresh    .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));



        /* Ajouts des composants */
        this.add(this.open     );
        this.add(this.openWith );
        this.add(this.edit     );
        this.add(this.rename   );
        this.addSeparator();
        this.add(this.newFile  );
        this.add(this.newFolder);
        this.addSeparator();
        this.add(this.copy     );
        this.add(this.copyPath );
        this.add(this.paste    );
        this.add(this.cut      );
        this.add(this.delete   );
        this.addSeparator();
        this.add(this.properties);
        this.add(this.refresh   );

        /* Activations des composants */
        this.changeDrive.addActionListener(this);
        this.open       .addActionListener(this);
        this.openWith   .addActionListener(this);
        this.edit       .addActionListener(this);
        this.rename     .addActionListener(this);
        this.newFile    .addActionListener(this);
        this.newFolder  .addActionListener(this);
        this.delete     .addActionListener(this);
        this.copy       .addActionListener(this);
        this.copyPath   .addActionListener(this);
        this.cut        .addActionListener(this);
        this.paste      .addActionListener(this);
        this.properties .addActionListener(this);
        this.refresh    .addActionListener(this);
    }

    @Override
    public void show(Component c, int x, int y)
    {
        super.show(c, x, y);

        if (this.explorer.getPathForLocation(x, y).getParentPath() == null)
            this.add(this.changeDrive, 0);
        else
            this.remove(this.changeDrive);

        this.edit.setEnabled(!(this.ctrl.treePathToFile(this.explorer.getPathForLocation(x, y)).isDirectory() || this.ctrl.getFileExtension(this.ctrl.treePathToFile(this.explorer.getPathForLocation(x, y))).equals("zip")));
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        /* Changer de lecteur */
        if (source == this.changeDrive)
        {
            System.out.println("Changer de lecteur");
            this.ctrl.changeDrive(this.explorer);
        }
        /* Ouvrir */
        else if (source == this.open)
        {
            this.ctrl.openFile(this.ctrl.treePathToFile(this.explorer.getSelectionPath()));
        }
        /* Ouvrir avec */
        else if (source == this.openWith)
        {
            System.out.println("Ouvrir avec");
            this.ctrl.openFileWith(this.ctrl.treePathToFile(this.explorer.getSelectionPath()));
        }
        /* Editer */
        else if (source == this.edit)
        {
            this.ctrl.editFile(this.ctrl.treePathToFile(this.explorer.getSelectionPath()));
        }
        /* Renommer */
        else if (source == this.rename)
        {
            this.explorer.startEditingAtPath(this.explorer.getSelectionPath());
        }
        /* Nouveau fichier */
        else if (source == this.newFile)
        {
            this.ctrl.newElement(this.explorer, this.determinateFolderDestination(), 0);
            this.explorer.expandPath(this.determinateTreePathDestination());
        }
        /* Nouveau dossier */
        else if (source == this.newFolder)
        {
            this.ctrl.newElement(this.explorer, this.determinateFolderDestination(), 1);
            this.explorer.expandPath(this.determinateTreePathDestination());
        }
        /* Supprimer */
        else if (source == this.delete)
        {
            this.ctrl.deleteElement(this.explorer, this.ctrl.treePathToFile(this.explorer.getSelectionPath()));
        }
        /* Copier */
        else if (source == this.copy)
        {
            this.ctrl.copyElement(this.ctrl.treePathToFile(this.explorer.getSelectionPath()), false);
        }
        /* Copier le chemin */
        else if (source == this.copyPath)
        {
            this.ctrl.copyPath(this.ctrl.treePathToFile(explorer.getSelectionPath()).getAbsolutePath());
        }
        /* Couper */
        else if (source == this.cut)
        {
            this.ctrl.cutElement(this.explorer, this.ctrl.treePathToFile(this.explorer.getSelectionPath()));
        }
        /* Coller */
        else if (source == this.paste)
        {
            this.ctrl.pasteElement(this.explorer, this.determinateFolderDestination());
            this.explorer.expandPath(this.explorer.getSelectionPath());
        }
        /* Propriété */
        else if (source == this.properties)
        {
            this.ctrl.properties(this.ctrl.treePathToFile(this.explorer.getSelectionPath()));
        }
        /* Rafraichir */
        else if (source == this.refresh)
        {
            this.ctrl.refresh(this.explorer, this.explorer.getSelectionPath());
        }
    }

    /**
     * Permet de determiner le dossier de destination en fonction de la sélection de l'utilisateur
     * @return File : le dossier de destination
     */
    private File determinateFolderDestination()
    {
        File folderDestination = this.ctrl.treePathToFile(this.explorer.getSelectionPath());
        if (!folderDestination.isDirectory())
            folderDestination = folderDestination.getParentFile();

        return folderDestination;
    }

    /**
     * Permet de determiner le dossier de destination en fonction de la sélection de l'utilisateur
     * @return File : le dossier de destination
     */
    private TreePath determinateTreePathDestination()
    {
        TreePath tpParent = this.explorer.getSelectionPath();
        if (!this.ctrl.treePathToFile(this.explorer.getSelectionPath()).isDirectory())
            tpParent = tpParent.getParentPath();

        return tpParent;
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

        this.openWith.setBackground(backGeneralColor);
        this.openWith.setForeground(foreGeneralColor);

        this.edit.setBackground(backGeneralColor);
        this.edit.setForeground(foreGeneralColor);

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

        this.copyPath.setBackground(backGeneralColor);
        this.copyPath.setForeground(foreGeneralColor);

        this.cut.setBackground(backGeneralColor);
        this.cut.setForeground(foreGeneralColor);

        this.paste.setBackground(backGeneralColor);
        this.paste.setForeground(foreGeneralColor);

        this.properties.setBackground(backGeneralColor);
        this.properties.setForeground(foreGeneralColor);

        this.refresh.setBackground(backGeneralColor);
        this.refresh.setForeground(foreGeneralColor);
    }

    /**
     * Permet d'appliquer la langue
     */
    public void appliquerLangage()
    {
        HashMap<String, String> PopUpMenuLangage = this.ctrl.getLangage().get("popUpMenu");


        this.changeDrive.setText(PopUpMenuLangage.get("changeDrive"));
        this.open       .setText(PopUpMenuLangage.get("open"));
        this.openWith   .setText(PopUpMenuLangage.get("openWith"));
        this.edit       .setText(PopUpMenuLangage.get("edit"));
        this.rename     .setText(PopUpMenuLangage.get("rename"));
        this.newFile    .setText(PopUpMenuLangage.get("newFile"));
        this.newFolder  .setText(PopUpMenuLangage.get("newFolder"));
        this.delete     .setText(PopUpMenuLangage.get("delete"));
        this.copy       .setText(PopUpMenuLangage.get("copy"));
        this.copyPath   .setText(PopUpMenuLangage.get("copyPath"));
        this.cut        .setText(PopUpMenuLangage.get("cut"));
        this.paste      .setText(PopUpMenuLangage.get("paste"));
        this.properties .setText(PopUpMenuLangage.get("properties"));
        this.refresh    .setText(PopUpMenuLangage.get("refresh"));
    }
}