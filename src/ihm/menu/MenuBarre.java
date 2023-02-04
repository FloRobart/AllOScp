package ihm.menu;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.InputEvent;
import java.awt.event.MouseListener.*;

import controleur.Controleur;


public class MenuBarre extends JMenuBar implements ActionListener 
{
	private Controleur ctrl;

	/* Frame Créée thème */
	private FrameCreerTheme frameCreerTheme;
	private FrameSuppTheme  frameSuppTheme;

	/* Menus */
	private JMenu menuOnglets;
	private JMenu menuPreferences;
	private JMenu menuAide;

	/* Onglets */
	private JMenuItem menuiOngletsNewOnglet;
	private JMenuItem menuiOngletsSupprOnglet;
	private JMenuItem menuiOngletsOngletPrecedent;
	private JMenuItem menuiOngletsOngletSuivant;

	/* Préférences */
	private JMenu     menuiPreferencesThemes;
	private JMenuItem menuiPreferencesThemesClair;
	private JMenuItem menuiPreferencesThemesSombre;
	private JMenuItem menuiPreferencesThemesDark;
	private JMenu     menuiPreferencesThemesPerso;

	private List<JMenuItem> lstMenuiPreferencesThemesPerso;

	/* Langages */
	private JMenu     menuiPreferencesLangages;
	private JMenuItem menuiPreferencesLangagesFrancais;
	private JMenuItem menuiPreferencesLangagesAnglais;


	public MenuBarre(Controleur ctrl) 
	{
		this.ctrl = ctrl;

		this.frameCreerTheme = null;
		this.frameSuppTheme  = null;

		/*=========================*/
		/* Création des composants */
		/*=========================*/
		/*----------*/
		/* Fichiers */
		/*----------*/



		/*---------*/
		/* Onglets */
		/*---------*/
		this.menuOnglets = new JMenu("Onglets");
		this.menuOnglets.setMnemonic('O');

		/* Nouvel onglet */
		this.menuiOngletsNewOnglet = new JMenuItem("Nouvel onglet");
		this.menuiOngletsNewOnglet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK)); // pour CTRL+T

		/* Suppression d'un onglet */
		this.menuiOngletsSupprOnglet = new JMenuItem("Supprimer l'onglet");
		this.menuiOngletsSupprOnglet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK)); // pour CTRL+W

		/* Panel précédent */
		this.menuiOngletsOngletPrecedent = new JMenuItem("Onglet précédent");
		this.menuiOngletsOngletPrecedent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK)); // pour CTRL+MAJ+TABULATION

		/* Panel suivant */
		this.menuiOngletsOngletSuivant = new JMenuItem("Onglet suivant");
		this.menuiOngletsOngletSuivant.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK)); // pour CTRL+TABULATION


		/*-------------*/
		/* Préférences */
		/*-------------*/
		this.menuPreferences = new JMenu("Préférences");
		this.menuPreferences.setMnemonic('P');

		/* Thèmes */
		this.menuiPreferencesThemes       = new JMenu    ("Thèmes ");

		/* Clair, Sombre, Dark */
		this.menuiPreferencesThemesClair  = new JMenuItem("Clair" );
		this.menuiPreferencesThemesSombre = new JMenuItem("Sombre");
		this.menuiPreferencesThemesDark   = new JMenuItem("Dark"  );

		/* Personnalisé */
		this.menuiPreferencesThemesPerso  = new JMenu    ("Personnalisé ");

		this.lstMenuiPreferencesThemesPerso = new ArrayList<JMenuItem>();
		this.lstMenuiPreferencesThemesPerso.add(new JMenuItem("Nouveau"));
		this.lstMenuiPreferencesThemesPerso.add(new JMenuItem("supprimer"));
		for (int i = 0; i < this.ctrl.getLstNameThemesPerso().size(); i++)
			this.lstMenuiPreferencesThemesPerso.add(new JMenuItem(this.ctrl.getLstNameThemesPerso().get(i)));


		/* Langages */
		this.menuiPreferencesLangages = new JMenu("Langages");

		/* Français, Anglais */
		this.menuiPreferencesLangagesFrancais = new JMenuItem("Français");
		this.menuiPreferencesLangagesAnglais  = new JMenuItem("Anglais" );


		/*------*/
		/* Aide */
		/*------*/
		this.menuAide = new JMenu("Aide");
		this.menuAide.setMnemonic('A');


		/*=======================*/
		/* Ajouts des composants */
		/*=======================*/
		/*---------*/
		/* Onglets */
		/*---------*/
		this.menuOnglets.add(this.menuiOngletsNewOnglet);
		this.menuOnglets.add(this.menuiOngletsSupprOnglet);
		this.menuOnglets.addSeparator();
		this.menuOnglets.add(this.menuiOngletsOngletPrecedent);
		this.menuOnglets.add(this.menuiOngletsOngletSuivant);
		this.menuOnglets.addSeparator();
		this.add(menuOnglets);

		/*------------*/
		/* Préférence */
		/*------------*/
		/* Thèmes prédéfinie */
		this.menuiPreferencesThemes.add(this.menuiPreferencesThemesClair);
		this.menuiPreferencesThemes.add(this.menuiPreferencesThemesSombre);
		this.menuiPreferencesThemes.add(this.menuiPreferencesThemesDark);
		this.menuiPreferencesThemes.add(this.menuiPreferencesThemesPerso);

		/* Thèmes personnalisés */
		for (int i = 0; i < this.lstMenuiPreferencesThemesPerso.size(); i++)
		{
			this.menuiPreferencesThemesPerso.add(this.lstMenuiPreferencesThemesPerso.get(i));
			if (i == 1)
				this.menuiPreferencesThemesPerso.addSeparator();
		}

		this.menuPreferences.add(this.menuiPreferencesThemes);

		/* Langages */
		this.menuiPreferencesLangages.add(this.menuiPreferencesLangagesFrancais);
		this.menuiPreferencesLangages.add(this.menuiPreferencesLangagesAnglais);
		
		this.menuPreferences.add(this.menuiPreferencesLangages);

		/* Ajout de tout à la JMenuBar */
		this.add(menuPreferences);


		/*------*/
		/* Aide */
		/*------*/
		this.add(menuAide);


		/*============================*/
		/* Activations des composants */
		/*============================*/
		/*---------*/
		/* Onglets */
		/*---------*/
		/* add and supp Onglets */
		this.menuiOngletsNewOnglet.addActionListener(this);
		this.menuiOngletsSupprOnglet.addActionListener(this);

		/* suivant and précédent Onglets */
		this.menuiOngletsOngletPrecedent.addActionListener(this);
		this.menuiOngletsOngletSuivant.addActionListener(this);

		/*-------------*/
		/* Préférences */
		/*-------------*/
		/* Thèmes */
		this.menuiPreferencesThemesClair .addActionListener(this);
		this.menuiPreferencesThemesSombre.addActionListener(this);
		this.menuiPreferencesThemesDark  .addActionListener(this);

		for (int i = 0; i < this.lstMenuiPreferencesThemesPerso.size(); i++)
			this.lstMenuiPreferencesThemesPerso.get(i).addActionListener(this);

		/* Langages */
		this.menuiPreferencesLangagesFrancais.addActionListener(this);
		this.menuiPreferencesLangagesAnglais .addActionListener(this);
	}


	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() instanceof JMenuItem)
		{
			/* Onglets */
			if (e.getSource() == this.menuiOngletsNewOnglet)
				this.ctrl.ajouterOnglet();
			
			if (e.getSource() == this.menuiOngletsSupprOnglet)
				this.ctrl.supprimerOnglet();

			if (e.getSource() == this.menuiOngletsOngletPrecedent)
				this.ctrl.ongletPrecedent();

			if (e.getSource() == this.menuiOngletsOngletSuivant)
				this.ctrl.ongletSuivant();

			/* Préférences */
			if (e.getSource() == this.menuiPreferencesThemesClair)
				this.ctrl.changerTheme("clair");
			
			if (e.getSource() == this.menuiPreferencesThemesSombre)
				this.ctrl.changerTheme("sombre");

			if (e.getSource() == this.menuiPreferencesThemesDark)
				this.ctrl.changerTheme("dark");
			
			/* Nouveau */
			if (e.getSource() == this.lstMenuiPreferencesThemesPerso.get(0))
			{
				if (this.frameCreerTheme == null)
					this.frameCreerTheme = new FrameCreerTheme(this.ctrl);
				else
					this.frameCreerTheme.setVisible(true);
			}

			/* Supprimer */
			if (e.getSource() == this.lstMenuiPreferencesThemesPerso.get(1))
			{
				if (this.ctrl.getNbThemesPerso() != 0)
				{
					if (this.frameSuppTheme == null)
						this.frameSuppTheme = new FrameSuppTheme(this.ctrl);
					else
						this.frameSuppTheme.setVisible(true);
				}
			}

			/* tout les thèmes personnalisé */
			for (int i = 2; i < this.lstMenuiPreferencesThemesPerso.size(); i++)
				if (e.getSource() == this.lstMenuiPreferencesThemesPerso.get(i))
					this.ctrl.changerTheme(this.lstMenuiPreferencesThemesPerso.get(i).getText().replace(" ", "_"));


			/* Langages */
			if (e.getSource() == this.menuiPreferencesLangagesFrancais)
				this.ctrl.changerLangage("francais");

			if (e.getSource() == this.menuiPreferencesLangagesAnglais)
				this.ctrl.changerLangage("anglais");
		}
	}


	/**
     * Permet de fermer la fenêtre de création de thème
     */
    public void disposeFrameCreerTheme() { this.frameCreerTheme.dispose(); this.frameCreerTheme = null; }

	/**
     * Permet de fermer la fenêtre de suppression de thème
     */
    public void disposeFrameSuppTheme() { this.frameSuppTheme.dispose(); this.frameSuppTheme = null; }


	/**
	 * Permet d'ajouter un nouveau thème personnalisé à la menuBarre
	 * @param nomTheme : Nom du thème dans la menuBarre
	 */
	public void ajouterThemePersoOnMenuBarre(String nomTheme)
	{
		/* Création du thème + Ajout à la liste des thèmes personnalisé */
		this.lstMenuiPreferencesThemesPerso.add(new JMenuItem(nomTheme));

		/* Ajout du thème à la menuBarre */
		this.menuiPreferencesThemesPerso.add(this.lstMenuiPreferencesThemesPerso.get(this.lstMenuiPreferencesThemesPerso.size() - 1));

		/* Activation du thème */
		this.lstMenuiPreferencesThemesPerso.get(this.lstMenuiPreferencesThemesPerso.size() - 1).addActionListener(this);

		/* Mise à jour de la menuBarre */
		this.appliquerTheme();
	}


	/**
     * Permet de supprimer un thème personnalisé de la menuBarre
     * @param lstNomsThemes : Liste des noms des thèmes à supprimer
     */
    public void supprimerThemePersoOnMenuBarre(List<String> lstNomsThemes)
    {
		/* Suppression du thème de la menuBarre et de l'arrayList */
		for (int i = 0; i < this.lstMenuiPreferencesThemesPerso.size(); i++)
		{
			for (String themeASupp : lstNomsThemes)
			{
				if (this.lstMenuiPreferencesThemesPerso.get(i).getText().replace(" ", "_").equals(themeASupp))
				{
					this.menuiPreferencesThemesPerso.remove(this.lstMenuiPreferencesThemesPerso.get(i));
					this.lstMenuiPreferencesThemesPerso.remove(i).getText();
				}
			}
		}
	}


	
	/**
     * Applique le thème à tout les composants du panel
     */
    public void appliquerTheme()
	{
		Color backGeneralColor = this.ctrl.getTheme().get("background");
		Color foreGeneralColor = this.ctrl.getTheme().get("foreground");

		/*-------------------------*/
		/* La Menu Barre elle même */
		/*-------------------------*/
		this.setBackground(backGeneralColor);
		this.setForeground(foreGeneralColor);

		/*-------*/
		/* Frame */
		/*-------*/
		if (this.frameCreerTheme != null) { this.frameCreerTheme.appliquerTheme(); }
		if (this.frameSuppTheme != null) { this.frameSuppTheme.appliquerTheme(); }

		/*---------*/
		/* Onglets */
		/*---------*/
		/* Onglets */
		this.menuOnglets.setBackground(backGeneralColor);
		this.menuOnglets.setForeground(foreGeneralColor);

		/* Nouvel onglet */
		this.menuiOngletsNewOnglet.setBackground(backGeneralColor);
		this.menuiOngletsNewOnglet.setForeground(foreGeneralColor);

		/* Suppression d'un onglet */
		this.menuiOngletsSupprOnglet.setBackground(backGeneralColor);
		this.menuiOngletsSupprOnglet.setForeground(foreGeneralColor);

		/* Onglet précédent */
		this.menuiOngletsOngletPrecedent.setBackground(backGeneralColor);
		this.menuiOngletsOngletPrecedent.setForeground(foreGeneralColor);

		/* Onglet suivant */
		this.menuiOngletsOngletSuivant.setBackground(backGeneralColor);
		this.menuiOngletsOngletSuivant.setForeground(foreGeneralColor);
		

		/*------------*/
		/* Préférence */
		/*------------*/
		/* Préférence */
		this.menuPreferences.setBackground(backGeneralColor);
		this.menuPreferences.setForeground(foreGeneralColor);

		/* Thèmes */
		this.menuiPreferencesThemes      .setOpaque(true);
		this.menuiPreferencesThemes      .setBackground(backGeneralColor);
		this.menuiPreferencesThemes      .setForeground(foreGeneralColor);

		/* Clair */
		this.menuiPreferencesThemesClair .setBackground(backGeneralColor);
		this.menuiPreferencesThemesClair .setForeground(foreGeneralColor);

		/* Sombre */
		this.menuiPreferencesThemesSombre.setBackground(backGeneralColor);
		this.menuiPreferencesThemesSombre.setForeground(foreGeneralColor);

		/* Dark */
		this.menuiPreferencesThemesDark  .setBackground(backGeneralColor);
		this.menuiPreferencesThemesDark  .setForeground(foreGeneralColor);

		/* Personnalisé */
		this.menuiPreferencesThemesPerso .setOpaque(true);
		this.menuiPreferencesThemesPerso .setBackground(backGeneralColor);
		this.menuiPreferencesThemesPerso .setForeground(foreGeneralColor);


		for (int i = 0; i < this.lstMenuiPreferencesThemesPerso.size(); i++)
		{
			this.lstMenuiPreferencesThemesPerso.get(i).setBackground(backGeneralColor);
			this.lstMenuiPreferencesThemesPerso.get(i).setForeground(foreGeneralColor);
		}

		
		/* Langages */
		this.menuiPreferencesLangages      .setOpaque(true);
		this.menuiPreferencesLangages      .setBackground(backGeneralColor);
		this.menuiPreferencesLangages      .setForeground(foreGeneralColor);

		/* Français */
		this.menuiPreferencesLangagesFrancais .setBackground(backGeneralColor);
		this.menuiPreferencesLangagesFrancais .setForeground(foreGeneralColor);

		/* Anglais */
		this.menuiPreferencesLangagesAnglais .setBackground(backGeneralColor);
		this.menuiPreferencesLangagesAnglais .setForeground(foreGeneralColor);



		/*------*/
		/* Aide */
		/*------*/
		this.menuAide.setBackground(backGeneralColor);
		this.menuAide.setForeground(foreGeneralColor);
	}


	/**
	 * Permet d'appliquer la langue à tout les composants du panel
	 */
	public void appliquerLangage()
	{
		HashMap<String, HashMap<String, String>> langage = this.ctrl.getLangage();

		this.majMnemonic(langage);

		/*-------*/
		/* Frame */
		/*-------*/
		if (this.frameCreerTheme != null) { this.frameCreerTheme.appliquerLangage(); }
		if (this.frameSuppTheme != null) { this.frameSuppTheme.appliquerLangage(); }

		/*---------*/
		/* Onglets */
		/*---------*/
		HashMap<String, String> menuBarreOnglets = langage.get("menuBarreOnglets");
		/* Onglets */
		this.menuOnglets.setText(menuBarreOnglets.get("titre"));

		/* Nouvel onglet */
		this.menuiOngletsNewOnglet.setText(menuBarreOnglets.get("nouvelOnglet"));

		/* Suppression d'un onglet */
		this.menuiOngletsSupprOnglet.setText(menuBarreOnglets.get("fermerOnglet"));

		/* Onglet précédent */
		this.menuiOngletsOngletPrecedent.setText(menuBarreOnglets.get("ongletPrecedent"));

		/* Onglet suivant */
		this.menuiOngletsOngletSuivant.setText(menuBarreOnglets.get("ongletSuivant"));;
		

		/*------------*/
		/* Préférence */
		/*------------*/
		HashMap<String, String> menuBarrePreferences = langage.get("menuBarrePreferences");
		/* Préférence */
		this.menuPreferences.setText(menuBarrePreferences.get("titre"));

		/* Thèmes */
		this.menuiPreferencesThemes      .setText(menuBarrePreferences.get("themes"));

		/* Clair */
		this.menuiPreferencesThemesClair .setText(menuBarrePreferences.get("themeClair"));

		/* Sombre */
		this.menuiPreferencesThemesSombre.setText(menuBarrePreferences.get("themeSombre"));

		/* Dark */
		this.menuiPreferencesThemesDark  .setText(menuBarrePreferences.get("themeDark"));

		/* Personnalisé */
		this.menuiPreferencesThemesPerso .setText(menuBarrePreferences.get("themePerso"));

		this.lstMenuiPreferencesThemesPerso.get(0).setText(menuBarrePreferences.get("themePersoNouveau"));
		this.lstMenuiPreferencesThemesPerso.get(1).setText(menuBarrePreferences.get("themePersoSupprimer"));


		/* Langages */
		this.menuiPreferencesLangages         .setText(menuBarrePreferences.get("langages"));

		/* Français */
		this.menuiPreferencesLangagesFrancais .setText(menuBarrePreferences.get("langageFrancais"));

		/* Anglais */
		this.menuiPreferencesLangagesAnglais  .setText(menuBarrePreferences.get("langageAnglais"));

		/*------*/
		/* Aide */
		/*------*/
		HashMap<String, String> menuBarreAide = langage.get("menuBarreAide");
		this.menuAide.setText(menuBarreAide.get("titre"));
	}


	/**
	 * Met à jour les Mnemonic en fonction du langage
	 */
	private void majMnemonic(HashMap<String, HashMap<String, String>> langage)
	{
		this.menuOnglets    .setMnemonic(langage.get("menuBarreOnglets"    ).get("titre").charAt(0));
		this.menuPreferences.setMnemonic(langage.get("menuBarrePreferences").get("titre").charAt(0));
		this.menuAide       .setMnemonic(langage.get("menuBarreAide"       ).get("titre").charAt(0));
	}
}


