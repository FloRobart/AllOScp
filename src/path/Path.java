package path;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import org.jdom2.input.SAXBuilder;


public interface Path
{
    public static final String PATH_ALL_PATHS = Path.initPathAllPaths();

    /* Thèmes */
	public static final String PATH_THEMES       = Path.initPaths("pathThemes"     );
    public static final String PATH_THEME_X      = Path.initPaths("pathThemeX"     );
    public static final String PATH_THEME_SAVE   = Path.initPaths("pathThemeSave"  );

	/* Langages */
	public static final String PATH_LANGAGES     = Path.initPaths("pathLangages"   );
	public static final String PATH_LANGAGE_X    = Path.initPaths("pathLangageX"   );
	public static final String PATH_LANGAGE_SAVE = Path.initPaths("pathLangageSave");



    /**
     * Permet d'initialiser le chemin du fichier xml qui répertorie tous les chemins
     * @return Chemin du fichier xml qui répertorie tous les chemins
     */
    private static String initPathAllPaths()
    {
        String pathFile_AllPaths = "./bin/donnees/ALL_PATHS.xml";


        File file = new File(pathFile_AllPaths);
        if (!file.exists())
        {
            try
            {
                /* Création du fichier */
                file.createNewFile();
                
                /* Ecriture du fichier */
                Path.writeFile_AllPath(pathFile_AllPaths);
            }
            catch (IOException e) { e.printStackTrace(); JOptionPane.showMessageDialog(null,  e + "\n\nError when creating the XML file 'ALL_PATHS.xml'.\nWithout this file the application cannot be launched", "Error", JOptionPane.ERROR_MESSAGE); System.exit(1); }
            
        }

        return pathFile_AllPaths;
    }



    /**
     * Permet d'initialiser les chemins des fichiers XML
     * @param nom : Nom du chemin à initialiser
     * @return Chemin du fichier XML
     */
    public static String initPaths(String nom)
    {
        String path = "";

        /* Lecture du fichier XML */
		try
		{
			SAXBuilder sxb = new SAXBuilder();
            path = sxb.build(Path.PATH_ALL_PATHS).getRootElement().getChild(nom).getText();
		}
		catch (Exception e) { Path.writeFile_AllPath(Path.PATH_ALL_PATHS); }

        /* Vérification du résultat de la lecture du fichier XML */
        int cpt = 0;
        while (path == "" && cpt < 3)
        {
            Path.writeFile_AllPath(Path.PATH_ALL_PATHS);
            try
            {
                SAXBuilder sxb = new SAXBuilder();
                path = sxb.build(Path.PATH_ALL_PATHS).getRootElement().getChild(nom).getText();
            }
            catch (Exception e) { e.printStackTrace(); JOptionPane.showMessageDialog(null,  e + "\n\nError reading XML file 'ALL_PATHS.xml'.\nWithout this file the application cannot be launched", "Error", JOptionPane.ERROR_MESSAGE); System.exit(1); }

            cpt ++;
        }

        /* Vérification de la vérification */
        if (path == "")
        {
            JOptionPane.showMessageDialog(null, "Error reading XML file 'ALL_PATHS.xml'.\nWithout this file the application cannot be launched", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        else
        {
            File file = new File(path);
            if (!file.exists())
            {
                Path.writeFile_AllPath(Path.PATH_ALL_PATHS);

                try
                {
                    SAXBuilder sxb = new SAXBuilder();
                    path = sxb.build(Path.PATH_ALL_PATHS).getRootElement().getChild(nom).getText();
                }
                catch (Exception e) { Path.writeFile_AllPath(Path.PATH_ALL_PATHS); }
            }
        }

        return path;
    }



    /**
     * Permet d'écrire le contenu du fichier XML 'ALL_PATHS.xml'
     * @param file_AllPath : Fichier XML 'ALL_PATHS.xml'
     */
    private static void writeFile_AllPath(String file_AllPath)
    {
        try
        {
            /* Ecriture du fichier */
            String sRet =   "<path>"                                                                               + "\n"   +
                            "    <pathThemes>./bin/donnees/themes</pathThemes>"                                    + "\n"   +
                            "    <pathThemeX>./bin/donnees/themes/theme_</pathThemeX>"                             + "\n"   +
                            "    <pathThemeSave>./bin/donnees/themes/theme_sauvegarde.xml</pathThemeSave>"         + "\n\n" +
                            "    <pathLangages>./bin/donnees/langages/</pathLangages>"                             + "\n"   +
                            "    <pathLangageX>./bin/donnees/langages/langage_</pathLangageX>"                     + "\n"   +
                            "    <pathLangageSave>./bin/donnees/langages/langage_sauvegarde.xml</pathLangageSave>" + "\n"   +
                            "</path>";

            PrintWriter pw = new PrintWriter(file_AllPath);
            pw.print(sRet);
            pw.close();
        }
        catch (FileNotFoundException e) { e.printStackTrace(); System.out.println("Error when writing the XML file 'ALL_PATHS.xml'.\nWithout this file the application cannot be launched"); System.exit(1); }
    }
}
