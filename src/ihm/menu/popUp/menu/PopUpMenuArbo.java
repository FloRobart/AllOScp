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
    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.changeDrive)
        {
            System.out.println("Changer de lecteur");
        }
        else if (e.getSource() == this.open)
        {
            System.out.println("Ouvrir");

            String filePath = "";
            for (Object o : this.arborescence.getSelectionPath().getPath())
                filePath += o.toString() + File.separator;

            File file = new File(filePath.substring(0, filePath.length() - 1));
            if (file.isDirectory())
            {
                this.arborescence.expandPath(this.arborescence.getSelectionPath());
            }
            else if (file.exists())
            {
                try { Desktop.getDesktop().open(file); }
                catch (IOException ex) { Logger.getLogger(Explorer.class.getName()).log(Level.SEVERE, "Erreur lors de l'ouverture du fichier '" + file.getAbsolutePath() + "'", ex); ex.printStackTrace(); System.out.println("Erreur lors de l'ouverture du fichier '" + file.getAbsolutePath() + "'"); }
            }
        }
        else if (e.getSource() == this.rename)
        {
            System.out.println("Renommer");

            this.arborescence.startEditingAtPath(this.arborescence.getSelectionPath());


            //this.arborescence.getSelectionPath().
        }
        else if (e.getSource() == this.newFile)
        {
            String filePath = "";
            for (Object o : this.arborescence.getSelectionPath().getPath())
                filePath += o.toString() + File.separator;

            File file = new File(filePath.substring(0, filePath.length() - 1));

            String fileName   = "Nouveau_fichier.txt";
            TreePath tpParent = this.arborescence.getSelectionPath();
            File fileToCreate = null;
            if (file.isDirectory())
                fileToCreate = new File(file.getAbsolutePath() + File.separator + fileName);
            else
                { tpParent = tpParent.getParentPath(); fileToCreate = new File(file.getParent() + File.separator + fileName); }
            
            this.arborescence.addNode(fileName, tpParent);
            this.createNewFile(fileToCreate);
            this.arborescence.expandPath(this.arborescence.getSelectionPath());
        }
        else if (e.getSource() == this.newFolder)
        {
            System.out.println("Nouveau dossier");

            String filePath = "";
            for (Object o : this.arborescence.getSelectionPath().getPath())
                filePath += o.toString() + File.separator;

            File file = new File(filePath.substring(0, filePath.length() - 1));

            String folderName   = "Nouveau_dossier";
            TreePath tpParent   = this.arborescence.getSelectionPath();
            File folderToCreate = null;
            if (file.isDirectory())
                folderToCreate = new File(file.getAbsolutePath() + File.separator + folderName);
            else
                { tpParent = tpParent.getParentPath(); folderToCreate = new File(file.getParent() + File.separator + folderName); }

            this.arborescence.addNode(folderName, tpParent);
            this.createNewFolder(folderToCreate);
            this.arborescence.expandPath(this.arborescence.getSelectionPath());
        }
        else if (e.getSource() == this.delete)
        {
            System.out.println("Supprimer");

            String filePath = "";
            for(Object o : this.arborescence.getSelectionPath().getPath())
                filePath += o.toString() + File.separator;

            File file = new File(filePath.substring(0, filePath.length()-1));
            this.supprimerDossier(file);

            this.arborescence.removeNode(this.arborescence.getSelectionPath());
        }
        else if (e.getSource() == this.copy)
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
        else if (e.getSource() == this.copyPath)
        {
            System.out.println("Copier le chemin");

            String filePath = "";
            for(Object o : this.arborescence.getSelectionPath().getPath())
                filePath += o.toString() + File.separator;

            filePath = filePath.substring(0, filePath.length()-1);

            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection("\"" + filePath + "\""), null);
        }
        else if (e.getSource() == this.cut)
        {
            System.out.println("Couper");
        }
        else if (e.getSource() == this.paste)
        {
            System.out.println("Coller");

            String filePath = "";
            for (Object o : this.arborescence.getSelectionPath().getPath())
                filePath += o.toString() + File.separator;

            File fileSelectioned = new File(filePath.substring(0, filePath.length() - 1));
            if (fileSelectioned.isDirectory())
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
                                this.pasteFolder(f, new File(fileSelectioned.getAbsolutePath() + File.separator + f.getName()));
                            else
                                this.pasteFile(f, new File(fileSelectioned.getAbsolutePath() + File.separator + f.getName()));
                        }
                    }
                }
                catch (Exception ex) { ex.printStackTrace(); System.out.println("Erreur lors du collage des fichiers"); }
            }
            else
            {
                String parentPath = fileSelectioned.getParent();

                Transferable contents =  Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
                if ((contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor))
                {
                    try
                    {
                        System.out.println("content = '" + contents.getTransferData(DataFlavor.stringFlavor) + "'");
                    }
                    catch (Exception ex) { System.out.println(ex); ex.printStackTrace(); }
                }
                else { System.out.println("Pas de contenu"); }
            }

            this.arborescence.expandPath(this.arborescence.getSelectionPath());
        }
        else if (e.getSource() == this.properties)
        {
            System.out.println("Propriétés");
        }
    }

    /**
     * Permet de coller un dossier et son contenue dans le dossier sélectionné
     * @param folderToPaste : le dossier à coller
     */
    private void pasteFolder(File folderToPaste, File folderDestination)
    {

    }

    /**
     * Permet de coller un fichier dans le dossier sélectionné
     * @param fileToPaste : le fichier à coller
     */
    private void pasteFile(File fileToPaste, File fileDestination)
    {
        
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