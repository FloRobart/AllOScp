package ihm.menu;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

import controleur.Controleur;


public class MenuBarre extends JMenuBar implements ActionListener 
{
	private Controleur ctrl;

	/* Menus */
	private JMenu menuOptions;
	private JMenu menuPreferences;
	private JMenu menuAide;

	/* Options */
	private JMenuItem menuiOptionsNewOnglet;
	private JMenuItem menuiOptionsSupprOnglet;
	private JMenuItem menuiOptionsOngletPrecedent;
	private JMenuItem menuiOptionsOngletSuivant;

	/* Préférences */
	private JMenuItem menuiPreferencesThemes;
	private JMenuItem menuiPreferencesThemesClair;
	private JMenuItem menuiPreferencesThemesSombre;
	private JMenuItem menuiPreferencesThemesDark;


	public MenuBarre(Controleur ctrl) 
	{
		this.ctrl = ctrl;

		/*=========================*/
		/* Création des composants */
		/*=========================*/
		/*---------*/
		/* Options */
		/*---------*/
		this.menuOptions = new JMenu("Options");
		this.menuOptions.setMnemonic('O');

		/* Nouvel onglet */
		this.menuiOptionsNewOnglet = new JMenuItem("Nouvel onglet");
		this.menuiOptionsNewOnglet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK)); // pour CTRL+T

		/* Suppression d'un onglet */
		this.menuiOptionsSupprOnglet = new JMenuItem("Supprimer l'onglet");
		this.menuiOptionsSupprOnglet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK)); // pour CTRL+W

		/* Panel précédent */
		this.menuiOptionsOngletPrecedent = new JMenuItem("Panel précédent");
		this.menuiOptionsOngletPrecedent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK)); // pour CTRL+MAJ+TABULATION

		/* Panel suivant */
		this.menuiOptionsOngletSuivant = new JMenuItem("Panel suivant");
		this.menuiOptionsOngletSuivant.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK)); // pour CTRL+TABULATION


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


		/*------*/
		/* Aide */
		/*------*/
		this.menuAide = new JMenu("Aide");
		this.menuAide.setMnemonic('A');


		/*=======================*/
		/* Ajouts des composants */
		/*=======================*/
		/*---------*/
		/* Options */
		/*---------*/
		this.menuOptions.add(this.menuiOptionsNewOnglet);
		this.menuOptions.add(this.menuiOptionsSupprOnglet);
		this.menuOptions.addSeparator();
		this.menuOptions.add(this.menuiOptionsOngletPrecedent);
		this.menuOptions.add(this.menuiOptionsOngletSuivant);
		this.menuOptions.addSeparator();
		this.add(menuOptions);

		/*------------*/
		/* Préférence */
		/*------------*/
		this.menuiPreferencesThemes.add(this.menuiPreferencesThemesClair);
		this.menuiPreferencesThemes.add(this.menuiPreferencesThemesSombre);
		this.menuiPreferencesThemes.add(this.menuiPreferencesThemesDark);
		this.menuPreferences       .add(this.menuiPreferencesThemes);
		this.add(menuPreferences);

		/*------*/
		/* Aide */
		/*------*/
		this.add(menuAide);


		/*============================*/
		/* Activations des composants */
		/*============================*/
		/*---------*/
		/* Options */
		/*---------*/
		/* add and supp Onglets */
		this.menuiOptionsNewOnglet.addActionListener(this);
		this.menuiOptionsSupprOnglet.addActionListener(this);

		/* suivant and précédent Onglets */
		this.menuiOptionsOngletPrecedent.addActionListener(this);
		this.menuiOptionsOngletSuivant.addActionListener(this);

		/*-------------*/
		/* Préférences */
		/*-------------*/
		this.menuiPreferencesThemesClair .addActionListener(this);
		this.menuiPreferencesThemesSombre.addActionListener(this);
		this.menuiPreferencesThemesDark  .addActionListener(this);
	}


	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() instanceof JMenuItem)
		{
			/* Options */
			if (e.getSource() == this.menuiOptionsNewOnglet)
				this.ctrl.ajouterOnglet();
			
			if (e.getSource() == this.menuiOptionsSupprOnglet)
				this.ctrl.supprimerOnglet();

			if (e.getSource() == this.menuiOptionsOngletPrecedent)
				this.ctrl.ongletPrecedent();

			if (e.getSource() == this.menuiOptionsOngletSuivant)
				this.ctrl.ongletSuivant();

			/* Préférences */
			if (e.getSource() == this.menuiPreferencesThemesClair)
				this.ctrl.changerTheme("clair");
			
			if (e.getSource() == this.menuiPreferencesThemesSombre)
				this.ctrl.changerTheme("sombre");

			if (e.getSource() == this.menuiPreferencesThemesDark)
				this.ctrl.changerTheme("dark");
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

		/*---------*/
		/* Options */
		/*---------*/
		/* Options */
		this.menuOptions.setBackground(backGeneralColor);
		this.menuOptions.setForeground(foreGeneralColor);

		/* Nouvel onglet */
		this.menuiOptionsNewOnglet.setBackground(backGeneralColor);
		this.menuiOptionsNewOnglet.setForeground(foreGeneralColor);

		/* Suppression d'un onglet */
		this.menuiOptionsSupprOnglet.setBackground(backGeneralColor);
		this.menuiOptionsSupprOnglet.setForeground(foreGeneralColor);

		/* Onglet précédent */
		this.menuiOptionsOngletPrecedent.setBackground(backGeneralColor);
		this.menuiOptionsOngletPrecedent.setForeground(foreGeneralColor);

		/* Onglet suivant */
		this.menuiOptionsOngletSuivant.setBackground(backGeneralColor);
		this.menuiOptionsOngletSuivant.setForeground(foreGeneralColor);
		

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


		/*------*/
		/* Aide */
		/*------*/
		this.menuAide.setBackground(backGeneralColor);
		this.menuAide.setForeground(foreGeneralColor);
	}
}