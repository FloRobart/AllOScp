package metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import controleur.Controleur;

import java.awt.Color;
import java.io.PrintWriter;


public class Metier
{
    private static final String PATH_THEME_X          = "./bin/donnees/themes/theme_";
    private static final String PATH_THEME_SAVE       = "./bin/donnees/themes/theme_sauvegarde.xml";
	private static final String PATH_THEME_PERSO_INFO = "./bin/donnees/themes/theme_perso_info.xml";


    private Controleur ctrl;

    private HashMap<String, Color>  hmColorThemes;


    public Metier(Controleur ctrl)
    {
        this.ctrl = ctrl;

        this.hmColorThemes = new HashMap<String, Color>();
        this.chargerThemes(this.getThemeUsed());
    }


    /**
     * Permert de récupérer toute les couleurs de thème charger en mémoire.
     * @return HashMap - liste des couleurs du thème.
     * 
     * object possible dans la hashmap : 
     * 
     * list.get(0) = couleur de fond.
     * list.get(1) = couleur du texte.
     * list.get(2) = couleur de hint / placeHolder (n'existe pas toujours).
     */
    public HashMap<String, Color> getTheme() { return this.hmColorThemes;}


    /**
	 * Récupère le thème utilisé dans le fichier xml de sauvegarde
	 * @return String : thème à utilisé
	 */
	public String getThemeUsed()
	{
		String themeUsed = "";
		SAXBuilder sxb = new SAXBuilder();

		try
		{
			themeUsed = sxb.build(Metier.PATH_THEME_SAVE).getRootElement().getText();
		}
		catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de la lecture du fichier XML du themes utilisé"); }

		return themeUsed;
	}


    /**
	 * Sauvegarde le thème selectionné par l'utilisateur dans le fichier xml de sauvegarde
	 * @param theme : thème à sauvegarder
	 */
	public void setThemeUsed(String theme)
	{
		try
		{
			PrintWriter pw = new PrintWriter(Metier.PATH_THEME_SAVE);
			pw.println("<theme>" + theme + "</theme>");
			pw.close();
		}
		catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de l'écriture du fichier XML du themes utilisé"); }

		this.chargerThemes(theme);

		this.ctrl.appliquerTheme();
	}


    /**
	 * Charge les couleurs du thème choisi par l'utilisateur dans la HashMap
	 * @param theme : thème à charger
	 * @return HashMap contenant les couleurs du thème
	 */
	public void chargerThemes(String theme)
	{
		SAXBuilder sxb = new SAXBuilder();

		try
		{
			Element racine = sxb.build(Metier.PATH_THEME_X + theme + ".xml").getRootElement();

			/*----------------------------------------------*/
			/* Récupération des couleurs de chaque éléments */
			/*----------------------------------------------*/
			String lstCles[] = new String[] {"background", "foreground", "disableColor", "enableColor", "titlesBackground", "saisiesBackground", "saisiesPlaceholder", "buttonsBackground"};

			for (int i = 0; i < lstCles.length; i++)
			{
				Element cg = racine.getChild(lstCles[i]);

                Color color = new Color( Integer.parseInt(cg.getAttributeValue("red")), Integer.parseInt(cg.getAttributeValue("green")), Integer.parseInt(cg.getAttributeValue("blue")), Integer.parseInt(cg.getAttributeValue("alpha")));

				this.hmColorThemes.put(lstCles[i], color);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Erreur lors de la lecture du fichier XML des informations du theme");
		}
	}


	/**
	 * Permet d'écrire dans un fichier xml les informations du thème personnalisé contenue dans la hashMap
	 * @param nom : nom du thème
	 * @param HashMap : liste des couleurs du thème associé à leur nom
	 */
	public void ajouterThemePerso(String nom,  HashMap<String, Color> theme)
	{
		// TODO : Ecrire dans le fichier XML les informations du thème
	}


	/**
	 * Permet de récupérer le nombre de thèmes personnalisés
	 * @return int : nombre de thèmes personnalisés
	 */
	public int getNbThemePerso()
	{
		int nbThemePerso = 0;

		SAXBuilder sxb = new SAXBuilder();

		try
		{
			nbThemePerso = Integer.parseInt(sxb.build(Metier.PATH_THEME_PERSO_INFO).getRootElement().getChildText("nombre"));
		}
		catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de la lecture du fichier XML du themes utilisé"); }

		//return nbThemePerso;
		return 2;
	}


	/**
	 * Permet de récupérer la liste des noms des thèmes personnalisés
	 * @return List : liste des noms des thèmes personnalisés
	 */
	public List<String> getLstNomThemePerso()
	{
		List<String> lstNomThemePerso = null;

		SAXBuilder sxb = new SAXBuilder();

		try
		{
			// TODO : récupérer les noms des thèmes personnalisés
		}
		catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de la lecture du fichier XML du themes utilisé"); }

		//return lstNomThemePerso;
		lstNomThemePerso = new ArrayList<String>();
		lstNomThemePerso.add("perso 1");
		lstNomThemePerso.add("perso 2");

		return lstNomThemePerso;
	}
}
