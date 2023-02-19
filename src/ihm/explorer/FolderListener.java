package ihm.explorer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FolderListener implements Runnable
{
    private Path path = null;


    /**
     * Constructor
     * @param pathname String dossier à surveiller
     */
    public FolderListener(String pathname)
    {
        this.path = Paths.get(pathname);
        System.out.println("ecoute du dossier : " + pathname);
    }

    public void run()
    {
        WatchService watchService = null;

        try
        {
            watchService = this.path.getFileSystem().newWatchService();

            /* Enregistrement des opérations à surveiller */
            this.path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

            WatchKey watchKey = null;

            /* Boucle qui permet de détecter les évènement du système */
            while (!Thread.interrupted())
            {
                watchKey = watchService.take();
 
                /* Traitement des évènements */
                for (WatchEvent<?> event : watchKey.pollEvents())
                {
                    String fileName = event.context().toString();

                    if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind()))
                    {
                        System.out.println("new file create " + fileName);
                    }
                    else if (StandardWatchEventKinds.ENTRY_MODIFY.equals(event.kind()))
                    {
                        System.out.println(fileName + " has been modified");
                    }
                    else if (StandardWatchEventKinds.ENTRY_DELETE.equals(event.kind()))
                    {
                        System.out.println(fileName + " has been deleted");
                    }
                    else if (StandardWatchEventKinds.OVERFLOW.equals(event.kind()))
                    {
                        System.out.println("Strange event");
                        continue;
                    }
                }

                /* se place en attente de message */
                watchKey.reset();
            }
        }
        catch (InterruptedException ex)
        {
            try
            {
                if (watchService != null) watchService.close();
                System.out.println("FolderListener stoper");
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
    }
}
