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
import java.util.List;
import java.awt.event.InputEvent;
import java.awt.event.MouseListener.*;

import controleur.Controleur;


public class MenuBarre extends JMenuBar implements ActionListener 
{
	private Controleur ctrl;

	/* Frame Créée thème */
	private FrameCreerTheme frameCreerTheme;

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


	public MenuBarre(Controleur ctrl) 
	{
		this.ctrl = ctrl;

		this.frameCreerTheme = null;

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
		this.menuiPreferencesThemesClair .addActionListener(this);
		this.menuiPreferencesThemesSombre.addActionListener(this);
		this.menuiPreferencesThemesDark  .addActionListener(this);

		for (int i = 0; i < this.lstMenuiPreferencesThemesPerso.size(); i++)
			this.lstMenuiPreferencesThemesPerso.get(i).addActionListener(this);
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
				// TODO : faire la fenêtre de suppression des thèmes
				//if (this.frameCreerTheme == null)
				//	this.frameSuppTheme = new FrameCreerTheme(this.ctrl);
				//else
				//	this.frameSuppTheme.setVisible(true);
			}

			/* tout les thèmes personnalisé */
			for (int i = 2; i < this.lstMenuiPreferencesThemesPerso.size(); i++)
				if (e.getSource() == this.lstMenuiPreferencesThemesPerso.get(i))
					this.ctrl.changerTheme(this.lstMenuiPreferencesThemesPerso.get(i).getText().replace(" ", "_"));
		}
	}


	/**
     * Permet de fermer la fenêtre de création de thème
     */
    public void disposeFrameCreerTheme() { this.frameCreerTheme.dispose(); this.frameCreerTheme = null; }


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


		/*------*/
		/* Aide */
		/*------*/
		this.menuAide.setBackground(backGeneralColor);
		this.menuAide.setForeground(foreGeneralColor);
	}
}


