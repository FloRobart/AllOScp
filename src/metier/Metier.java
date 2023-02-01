package metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import controleur.Controleur;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class Metier
{
	/* Thèmes */
	private static final String[] TAB_CLES        = new String[] {"background", "foreground", "disableColor", "enableColor", "titlesBackground", "saisiesBackground", "saisiesPlaceholder", "buttonsBackground"};
	private static final String   PATH_THEMES     = "./bin/donnees/themes/";
    private static final String   PATH_THEME_X    = "./bin/donnees/themes/theme_";
    private static final String   PATH_THEME_SAVE = "./bin/donnees/themes/theme_sauvegarde.xml";

	/* Langages */
	private static final String PATH_LANGAGES     = "./bin/donnees/langages/";
	private static final String PATH_LANGAGE_X    = "./bin/donnees/langages/langage_";
	private static final String PATH_LANGAGE_SAVE = "./bin/donnees/langages/langage_sauvegarde.xml";



    private Controleur ctrl;

	/* Thèmes */
	private int                     nbThemePerso;
	private List<String>            lstNomThemesPerso;
    private HashMap<String, Color>  hmColorTheme;

	/* Langages */
	private HashMap<String, HashMap<String, String>> hmLangage;


    public Metier(Controleur ctrl)
    {
        this.ctrl = ctrl;

		/* Thèmes */
		this.nbThemePerso      = this.initNbThemePerso();
		this.lstNomThemesPerso = this.initLstNameThemesPerso();
        this.hmColorTheme     = new HashMap<String, Color>();
        this.chargerThemes(this.getThemeUsed());

		/* Langages */
		this.hmLangage = new HashMap<String, HashMap<String, String>>();
		this.chargerLangage(this.getLangageUsed());
    }


	/*========*/
	/* Thèmes */
	/*========*/
	/**
	 * Permet d'initialiser le nombre de thèmes perso créer par l'utilisateur.
	 * @return int : nombre de thèmes perso.
	 */
	private int initNbThemePerso()
	{
		int nb = 0;

		File dossier = new File(Metier.PATH_THEMES);

		for (File fichier : dossier.listFiles())
			if (fichier.getName().startsWith("theme_perso_"))
				nb++;

		return nb;
	}

	/**
	 * Permet de récupérer le nombre de thèmes perso créer par l'utilisateur.
	 * @return int : nombre de thèmes perso.
	 */
	public int getNbThemesPerso() { return this.nbThemePerso; }

	/**
	 * Permet de modifier le nombre de thèmes perso créer par l'utilisateur.
	 */
	public void setNbThemesPerso(int i) { this.nbThemePerso = i; }

	/**
	 * Permet de renommer le fichier avec le nom du thème et de changer le nom enregistrer dans le fichier de sauvegarde.
	 * @param nomFichier : nouveau nom du fichier.
	 */
	public void setNomFichier(String nomFichier)
	{
		File fileTheme = new File(Metier.PATH_THEME_X + this.getThemeUsed() + ".xml");

		fileTheme.renameTo(new File(Metier.PATH_THEME_X + nomFichier + ".xml"));

		try
		{
			PrintWriter pw = new PrintWriter(Metier.PATH_THEME_SAVE);
			pw.println("<theme>" + nomFichier + "</theme>");
			pw.close();
		}
		catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de l'écriture du fichier XML du themes utilisé"); }
	}

	/**
	 * Permet de modifier un élément du thème dans le fichier xml et dans la mémoire.
	 * @param nameElement : nom de l'élément à modifier.
	 * @param color : nouvelle couleur de l'élément.
	 * @return boolean : true si l'élément a été modifié, false sinon.
	 */
	public boolean setElementTheme(String nameElement, Color color)
	{
		if (nameElement == null || color == null)         { return false; }
		if (!this.hmColorTheme.containsKey(nameElement)) { return false; }

		String sRet = "";

		try
		{
			Scanner sc = new Scanner(new File(Metier.PATH_THEME_X + this.getThemeUsed() + ".xml"), "UTF8");

			String line = "";
			while(sc.hasNextLine())
			{
				line = sc.nextLine();
				if (line.contains(nameElement))
					line = "\t<" + nameElement + " red=\"" + color.getRed() + "\" green=\"" + color.getGreen() + "\" blue=\"" + color.getBlue() + "\" alpha=\"" + color.getAlpha()  + "\">" + "</" + nameElement + ">";

				sRet += line + "\n";
			}

			sc.close();

		} catch (FileNotFoundException e) { e.printStackTrace(); System.out.println("Erreur dans la méthode setElementTheme(), fichier '" + Metier.PATH_THEME_X + this.getThemeUsed() + ".xml" + "' introuvable"); return false; }

		
		try
		{
			PrintWriter pw = new PrintWriter(new File(Metier.PATH_THEME_X + this.getThemeUsed() + ".xml"), "UTF8");
			pw.print(sRet);
			pw.close();
		}
		catch (FileNotFoundException        e) { e.printStackTrace(); System.out.println("Erreur dans la méthode setElementTheme()"); return false; }
		catch (UnsupportedEncodingException e) { e.printStackTrace(); System.out.println("Erreur dans la méthode setElementTheme()"); return false; }

		return true;
	}

	/**
	 * Permet de changer le nom du thème en cours d'utilisation.
	 * @param nomTheme : nouveau nom du thème.
	 */
	public void setNomTheme(String nomTheme)
	{
		if (nomTheme != null)
		{
			String sRet = "";
			try
			{
				Scanner sc = new Scanner(new File(Metier.PATH_THEME_X + this.getThemeUsed() + ".xml"), "UTF8");

				String line = "";
				while(sc.hasNextLine())
				{
					line = sc.nextLine();
					if (line.contains("name"))
						line = "<theme name=\"" + nomTheme + "\">";

					sRet += line + "\n";
				}

				sc.close();

			} catch (FileNotFoundException e) { e.printStackTrace(); System.out.println("Erreur dans la méthode setElementTheme(), fichier '" + Metier.PATH_THEME_X + this.getThemeUsed() + ".xml" + "' introuvable"); }

			
			try
			{
				PrintWriter pw = new PrintWriter(new File(Metier.PATH_THEME_X + this.getThemeUsed() + ".xml"), "UTF8");
				pw.print(sRet);
				pw.close();
			}
			catch (FileNotFoundException        e) { e.printStackTrace(); System.out.println("Erreur dans la méthode setNomTheme(String nomTheme)"); }
			catch (UnsupportedEncodingException e) { e.printStackTrace(); System.out.println("Erreur dans la méthode setNomTheme(String nomTheme)"); }
		}
	}

	/**
	 * Permet d'initialiser la liste des noms des thèmes perso créer par l'utilisateur.
	 * @return List : liste des noms des thèmes perso.
	 */
	private List<String> initLstNameThemesPerso()
	{
		List<String> lstNomThemesPerso = new ArrayList<String>();

		File dossier = new File(Metier.PATH_THEMES);

		String name = "";
		for (File fichier : dossier.listFiles())
		{
			if (fichier.getName().startsWith("theme_perso_"))
			{
				SAXBuilder sxb = new SAXBuilder();
				try { name = sxb.build(fichier).getRootElement().getAttributeValue("name"); }
				catch (JDOMException e) { e.printStackTrace(); System.out.println("Erreur dans la lecture des noms des thèmes persos");  }
				catch (IOException   e) { e.printStackTrace(); System.out.println("Erreur dans la lecture des noms des thèmes persos"); }

				lstNomThemesPerso.add(name.replace("_", " "));
			}
		}

		return lstNomThemesPerso;
	}

	/**
	 * Permet de vérifier si le nom du thème n'est pas déjà utilisé et que le nom n'est pas null.
	 * @param nomTheme : nom du thème à vérifier.
	 * @return boolean : true si le nom du thème n'est pas déjà utilisé et qu'il n'est pas null, sinon false.
	 */
	public boolean verifNomTheme(String nomTheme)
	{
		if (nomTheme.equals("perso ")) return false;

		boolean nameOnlySpace = true;
		for (int i = 5; i < nomTheme.length(); i++)
			if (nomTheme.charAt(i) != ' ') { nameOnlySpace = false; break; }

		if (nameOnlySpace) return false;


		for (String s : this.lstNomThemesPerso)
			if (s.equals(nomTheme)) return false;

		return true;
	}

	/**
	 * Met à jour la liste des noms des thèmes perso. permet d'ajouter le nouveau thème qui viens d'être créer pour qui le liste sois à jour l'ors du prochaine appelle de la méthode verifNomTheme()
	 */
	public void majLstNomTheme()
	{
		this.lstNomThemesPerso = this.initLstNameThemesPerso();
	}


	/**
	 * Permet de récupérer la liste des noms des thèmes perso créer par l'utilisateur.
	 * @return List : liste des noms des thèmes perso.
	 */
	public List<String> getLstNameThemesPerso() { return this.lstNomThemesPerso; }


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
    public HashMap<String, Color> getTheme() { return this.hmColorTheme;}


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
	 * Sauvegarde le thème selectionné par l'utilisateur dans le fichier xml de sauvegarde.
	 * Charge le thème selectionné dans la HashMap.
	 * Applique le thème selectionné (met à jour l'IHM).
	 * @param theme : thème à sauvegarder
	 */
	public void setThemeUsed(String theme)
	{
		if (!theme.equals(this.getThemeUsed()))
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
			for (int i = 0; i < Metier.TAB_CLES.length; i++)
			{
				Element cg = racine.getChild(Metier.TAB_CLES[i]);

                Color color = new Color( Integer.parseInt(cg.getAttributeValue("red")), Integer.parseInt(cg.getAttributeValue("green")), Integer.parseInt(cg.getAttributeValue("blue")), Integer.parseInt(cg.getAttributeValue("alpha")));

				this.hmColorTheme.put(Metier.TAB_CLES[i], color);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Erreur lors de la lecture du fichier XML des informations du theme");
		}
	}


	/*==========*/
	/* Langages */
	/*==========*/
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
    public HashMap<String, HashMap<String, String>> getLangage() { return this.hmLangage;}

	/**
	 * Récupère le nom du langage utilisé dans le fichier xml de sauvegarde
	 * @return String : langage utilisé
	 */
	public String getLangageUsed()
	{
		String langageUsed = "";
		SAXBuilder sxb = new SAXBuilder();

		try
		{
			langageUsed = sxb.build(Metier.PATH_LANGAGE_SAVE).getRootElement().getText();
		}
		catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de la lecture du fichier XML du langage utilisé"); }

		return langageUsed;
	}


    /**
	 * Sauvegarde le langage selectionné par l'utilisateur dans le fichier xml de sauvegarde.
	 * Charge le langage selectionné dans la HashMap.
	 * Applique le langage selectionné (met à jour l'IHM).
	 * @param theme : thème à sauvegarder
	 */
	public void setLangageUsed(String theme)
	{
		if (!theme.equals(this.getLangageUsed()))
		{
			try
			{
				PrintWriter pw = new PrintWriter(Metier.PATH_LANGAGE_SAVE);
				pw.println("<langage>" + theme + "</langage>");
				pw.close();
			}
			catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de l'écriture du fichier XML du langage utilisé"); }

			this.chargerLangage(theme);

			this.ctrl.appliquerLangage();
		}
	}

	/**
	 * Charge le texte du langage choisi par l'utilisateur dans la HashMap
	 * @param langage : langage à charger
	 * @return HashMap contenant le texte du langage
	 */
	public void chargerLangage(String langage)
	{
		SAXBuilder sxb = new SAXBuilder();
		try
		{
			Element racine = sxb.build(Metier.PATH_LANGAGE_X + langage + ".xml").getRootElement();

			/*----------------------------------------------*/
			/* Récupération des couleurs de chaque éléments */
			/*----------------------------------------------*/
			for (Element e : racine.getChildren())
			{
				HashMap<String, String> hmTemp = new HashMap<String, String>();
				for (Element eEnfant : e.getChildren())
					hmTemp.put(eEnfant.getName(), eEnfant.getText());

				this.hmLangage.put(e.getName(), hmTemp);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Erreur lors de la lecture du fichier XML des informations du theme");
		}
	}
}
