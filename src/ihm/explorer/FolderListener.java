package ihm.explorer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.tree.DefaultMutableTreeNode;

import controleur.Controleur;


public class FolderListener implements Runnable
{
    private Controleur ctrl;

    private Path folderPath = null;


    /**
     * Constructor
     * @param pathname String dossier à surveiller
     */
    public FolderListener(String pathname, Controleur ctrl)
    {
        this.ctrl = ctrl;
        this.folderPath = Paths.get(pathname);
    }


    @Override
    public void run()
    {
        WatchService watchService = null;

        try
        {
            watchService = this.folderPath.getFileSystem().newWatchService();

            /* Enregistrement des opérations à surveiller */
            this.folderPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

            WatchKey watchKey = null;

            System.out.println("ecoute du dossier : " + this.folderPath.toString());
            /* Boucle qui permet de détecter les évènement du système */
            while (!Thread.interrupted())
            {
                watchKey = watchService.take();
 
                /* Traitement des évènements */
                for (WatchEvent<?> event : watchKey.pollEvents())
                {
                    String fileName = event.context().toString();

                    if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) /* Création d'un élément */
                    {
                        //System.out.println(String.format("%-20s", fileName) + " CREER     dans " + this.folderPath.toString());
                        File file = new File(this.folderPath.toString() + File.separator + fileName);
                        if (file.isDirectory())
                            this.ctrl.addFolderListener(file.getAbsolutePath());

                        this.ctrl.addNode(new DefaultMutableTreeNode(fileName), file.getAbsolutePath());

                        System.out.println("=============================================================================================================================================================");
                    }
                    else if (StandardWatchEventKinds.ENTRY_DELETE.equals(event.kind())) /* Suppression d'un élément */
                    {
                        //System.out.println(String.format("%-20s", fileName) + " SUPPRIMER dans " + this.folderPath.toString());
                        this.ctrl.removeFolderListener(this.folderPath.toString() + File.separator + fileName);
                        System.out.println("=============================================================================================================================================================");
                        //this.ctrl.removeNode(, fileName);
                    }
                    else if (StandardWatchEventKinds.ENTRY_MODIFY.equals(event.kind())) /* Modification d'un élément */
                    {
                        //System.out.println(String.format("%-20s", fileName) + " MODIFIER  dans " + this.folderPath.toString());
                    }
                    else if (StandardWatchEventKinds.OVERFLOW.equals(event.kind())) /* Evènement inconnu */
                    {
                        System.out.println("Evènement inconnu");
                        continue;
                    }
                }

                /* se place en attente de message */
                watchKey.reset();
            }

            System.out.println("FolderListener on '" + this.folderPath.toString() + "' stoper (thread interrompu)");
        }
        catch (InterruptedException ex)
        {
            try
            {
                if (watchService != null) watchService.close();

                System.out.println("FolderListener on '" + this.folderPath.toString() + "' stoper (watchService fermer)");
            }
            catch (IOException ex1)
            {
                Logger.getLogger(FolderListener.class.getName()).log(Level.SEVERE, null, ex1);
                
                ex1.printStackTrace();
                System.out.println("Erreur lors de l'arret du watchService FolderListener");
            }
        }
        catch (Exception ex)
        {
            Logger.getLogger(FolderListener.class.getName()).log(Level.SEVERE, null, ex);

            ex.printStackTrace();
            System.out.println("Erreur lors de la mise en place du FolderListener");
        }

        System.out.println("sortie du run FolderListener");
    }


    @Override
    public String toString()
    {
        return this.folderPath.toString();
    }
}
