package ihm.menu.popUp.menu;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import controleur.Controleur;
import ihm.explorer.Explorer;
import metier.FileTransferable;


public class PopUpMenuArbo extends JPopupMenu implements ActionListener
{
    private Controleur ctrl;

    private Explorer arborescence;

    private JMenuItem changeDrive;
    private JMenuItem open;
    private JMenuItem rename;
    private JMenuItem newFile;
    private JMenuItem newFolder;
    private JMenuItem delete;
    private JMenuItem copy;
    private JMenuItem copyPath;
    private JMenuItem cut;
    private JMenuItem paste;
    private JMenuItem properties;


    public PopUpMenuArbo(Explorer arbo, Controleur ctrl)
    {
        this.ctrl = ctrl;

        this.arborescence = arbo;

        /* Création des composants */
        this.changeDrive = new JMenuItem();
        this.open        = new JMenuItem();
        this.rename      = new JMenuItem();
        this.newFile     = new JMenuItem();
        this.newFolder   = new JMenuItem();
        this.delete      = new JMenuItem();
        this.copy        = new JMenuItem();
        this.copyPath    = new JMenuItem();
        this.cut         = new JMenuItem();
        this.paste       = new JMenuItem();
        this.properties  = new JMenuItem();


        /* Ajout des accélérators */
        this.changeDrive.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
        this.open       .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        this.rename     .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
        //this.newFile    .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        //this.newFolder  .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        this.delete     .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        this.copy       .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        this.cut        .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        this.paste      .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        this.properties .setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));



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
        this.add(this.copyPath );
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
        this.copyPath   .addActionListener(this);
        this.cut        .addActionListener(this);
        this.paste      .addActionListener(this);
        this.properties .addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        /* Changer de lecteur */
        if (source == this.changeDrive)
        {
            System.out.println("Changer de lecteur");
        }
        /* Ouvrir */
        else if (source == this.open)
        {
            this.ctrl.open(this.ctrl.treePathToFile(this.arborescence.getSelectionPath()));
        }
        /* Renommer */
        else if (source == this.rename)
        {
            System.out.println("Renommer");
            this.ctrl.rename(this.arborescence, this.ctrl.treePathToFile(this.arborescence.getSelectionPath()));
        }
        /* Nouveau fichier */
        else if (source == this.newFile)
        {
            this.ctrl.newElement(this.arborescence, this.determinateFolderDestination(), 0);
            this.arborescence.expandPath(this.determinateTreePathDestination());
        }
        /* Nouveau dossier */
        else if (source == this.newFolder)
        {
            this.ctrl.newElement(this.arborescence, this.determinateFolderDestination(), 1);
            this.arborescence.expandPath(this.determinateTreePathDestination());
        }
        /* Supprimer */
        else if (source == this.delete)
        {
            System.out.println("Supprimer");
            File file = this.ctrl.treePathToFile(this.arborescence.getSelectionPath());
            this.supprimerDossier(file);

            this.arborescence.removeNode(this.arborescence.getSelectionPath());
        }
        /* Copier */
        else if (source == this.copy)
        {
            System.out.println("Copier");

            String filePath = "";
            for(Object o : this.arborescence.getSelectionPath().getPath())
                filePath += o.toString() + File.separator;

            filePath = filePath.substring(0, filePath.length()-1);

            List<File> lstFiles = new ArrayList<File>();
            lstFiles.add(new File(filePath));

            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new FileTransferable(lstFiles), null);
        }
        /* Copier le chemin */
        else if (source == this.copyPath)
        {
            String filePath = "";
            for(Object o : this.arborescence.getSelectionPath().getPath())
                filePath += o.toString() + File.separator;

            filePath = filePath.substring(0, filePath.length()-1);

            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection("\"" + filePath + "\""), null);
        }
        /* Couper */
        else if (source == this.cut)
        {
            System.out.println("Couper");
        }
        /* Coller */
        else if (source == this.paste)
        {
            System.out.println("Coller");

            String filePath = "";
            for (Object o : this.arborescence.getSelectionPath().getPath())
                filePath += o.toString() + File.separator;

            filePath = filePath.substring(0, filePath.length() - 1);

            File fileDestination = new File(filePath);
            if (!fileDestination.isDirectory())
                fileDestination = fileDestination.getParentFile();

            this.pasteFolder(fileDestination);
            this.arborescence.expandPath(this.arborescence.getSelectionPath());
        }
        /* Propriété */
        else if (source == this.properties)
        {
            System.out.println("Propriétés");
        }
    }

    /**
     * Permet de determiner le dossier de destination en fonction de la sélection de l'utilisateur
     * @return File : le dossier de destination
     */
    private File determinateFolderDestination()
    {
        File folderDestination = this.ctrl.treePathToFile(this.arborescence.getSelectionPath());
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
        TreePath tpParent = this.arborescence.getSelectionPath();
        if (!this.ctrl.treePathToFile(this.arborescence.getSelectionPath()).isDirectory())
            tpParent = tpParent.getParentPath();

        return tpParent;
    }

    /**
     * Permet de coller un dossier et son contenue dans le dossier sélectionné
     * @param folderDestination : le dossier de destination (le dossier de destination + le nom du dossier à coller)
     */
    @SuppressWarnings("unchecked")
    private void pasteFolder(File folderDestination)
    {
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        try
        {
            Transferable t = cb.getContents(null);
            if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
            {
                List<File> fileList = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);

                for (File f : fileList)
                {
                    if (f.isDirectory())
                        this.pasteFolderRec(f, new File(folderDestination.getAbsolutePath() + File.separator + f.getName()));
                    else
                        this.pasteFile(f, new File(folderDestination.getAbsolutePath() + File.separator + f.getName()));
                }
            }
        }
        catch (Exception ex) { ex.printStackTrace(); System.out.println("Erreur lors du collage des fichiers"); }
    }

    /**
     * Permet de coller un dossier et son contenue dans le dossier sélectionné
     * @param folderToPaste : le dossier à coller
     * @param folderDestination : le dossier de destination (le dossier de destination + le nom du dossier à coller)
     */
    private void pasteFolderRec(File folderToPaste, File folderDestination)
    {
        System.out.println("Collage du dossier " + folderToPaste.getAbsolutePath() + " dans " + folderDestination.getAbsolutePath());
        try
        {
            if (folderDestination.mkdir())
            {
                System.out.println("dossier créé");
                for (File f : folderToPaste.listFiles())
                {
                    if (f.isDirectory())
                        this.pasteFolderRec(f, new File(folderDestination.getAbsolutePath() + File.separator + f.getName()));
                    else
                        this.pasteFile(f, new File(folderDestination.getAbsolutePath() + File.separator + f.getName()));
                }
            }
            else
                System.out.println("le dossier existe déjà");
        }
        catch (Exception ex) { ex.printStackTrace(); System.out.println("Erreur lors de la création du dossier"); }
    }

    /**
     * Permet de coller un fichier dans le dossier sélectionné
     * @param fileToPaste : le fichier à coller
     * @param fileDestination : le fichier de destination (dossier de destination + nom du fichier)
     */
    private void pasteFile(File fileToPaste, File fileDestination)
    {
        try
        {
            Files.copy(fileToPaste.toPath(), fileDestination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e)
        {
            e.printStackTrace(); System.out.println("Erreur lors du collage du fichier");
        }
    }

    /**
     * Permet de supprimer un dossier et son contenu
     * @param folderToCreate : le dossier à supprimer
     */
    private void createNewFolder(File folderToCreate)
    {
        System.out.println("Création du dossier " + folderToCreate.getAbsolutePath());
        try
        {
            if (folderToCreate.mkdir())
                System.out.println("dossier créé");
            else
                System.out.println("le dossier existe déjà");
        }
        catch (Exception ex) { ex.printStackTrace(); System.out.println("Erreur lors de la création du dossier"); }
    }

    /**
     * Permet de créer un nouveau fichier
     * @param fileToCreate : le fichier à créer
     */
    private void createNewFile(File fileToCreate)
    {
        try
        {
            if (fileToCreate.createNewFile())
                System.out.println("Fichier créé");
            else
                System.out.println("le fichier existe déjà");
        }
        catch (Exception ex) { ex.printStackTrace(); System.out.println("Erreur lors de la création du fichier"); }
    }

    /**
     * Permet de copier un dossier et son contenu
     * @param file : le dossier à copier
     * @return la liste des fichiers copiés et dossier copiés
     */
    private List<File> copierDossier(File file)
    {
        List<File> lstFiles = new ArrayList<File>();

        if (file.isDirectory())
        {
            for (File f : file.listFiles())
                lstFiles.addAll(this.copierDossier(f));
        }

        lstFiles.add(file);

        return lstFiles;
    }

    /**
     * Permet de supprimer un dossier et son contenu
     * @param file le dossier à supprimer
     */
    private void supprimerDossier(File file)
    {
        if (file.isDirectory())
        {
            for (File f : file.listFiles())
                this.supprimerDossier(f);
        }

        file.delete();
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

        this.copyPath.setBackground(backGeneralColor);
        this.copyPath.setForeground(foreGeneralColor);

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
        this.copyPath   .setText(PopUpMenuLangage.get("copyPath"));
        this.cut        .setText(PopUpMenuLangage.get("cut"));
        this.paste      .setText(PopUpMenuLangage.get("paste"));
        this.properties .setText(PopUpMenuLangage.get("properties"));
    }
}