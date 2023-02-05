package path;

import org.jdom2.input.SAXBuilder;


public interface Path
{
    public static final String PATH_ALL_PATHS = "./bin/donnees/ALL_PATHS.xml";

    /* Thèmes */
	public static final String PATH_THEMES       = Path.initPaths("pathThemes"     );
    public static final String PATH_THEME_X      = Path.initPaths("pathThemeX"     );
    public static final String PATH_THEME_SAVE   = Path.initPaths("pathThemeSave"  );

	/* Langages */
	public static final String PATH_LANGAGES     = Path.initPaths("pathLangages"   );
	public static final String PATH_LANGAGE_X    = Path.initPaths("pathLangageX"   );
	public static final String PATH_LANGAGE_SAVE = Path.initPaths("pathLangageSave");


    /**
     * Permet d'initialiser les chemins des fichiers XML
     * @param nom : Nom du chemin à initialiser
     * @return Chemin du fichier XML
     */
    private static String initPaths(String nom)
    {
        String path = "";

		try
		{
			SAXBuilder sxb = new SAXBuilder();
            path = sxb.build(Path.PATH_ALL_PATHS).getRootElement().getChild(nom).getText();
		}
		catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de la lecture du fichier XML 'ALL_PATH.xml'"); System.exit(1); }

        return path;
    }
}
