package ihm.menu;

import java.awt.Color;

import javax.swing.JFrame;

import controleur.Controleur;


/*
 * Ce fichier fait partie du projet All'OScp
 * Copyright (C) 2024 Floris Robart <florobart.github@gmail.com>
 */
public class FrameSuppTheme extends JFrame
{
    private Controleur ctrl;

    private PanelSuppTheme panelSuppTheme;

    public FrameSuppTheme(Controleur ctrl)
    {
        this.ctrl = ctrl;

        this.setTitle(this.ctrl.getLangage().get("supprimerTheme").get("titre"));
        if (this.ctrl.getNbThemesPerso() == 1)
            this.setSize(500, 200);
        else
            this.setSize(500, 50 * this.ctrl.getNbThemesPerso() + 150);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.panelSuppTheme = new PanelSuppTheme(this.ctrl);
        this.add(this.panelSuppTheme);


        this.appliquerTheme();
        this.appliquerLangage();
        this.setVisible(true);
    }


    /**
     * Applique le thème à tout les composants du panel
     */
    public void appliquerTheme()
    {
        Color backGeneralColor = this.ctrl.getTheme().get("background");
        Color foreGeneralColor = this.ctrl.getTheme().get("foreground");


        /*--------------------*/
        /* La Frame elle même */
        /*--------------------*/
        this.setBackground(backGeneralColor);
        this.setForeground(foreGeneralColor);


        /*-------*/
        /* Panel */
        /*-------*/
        this.panelSuppTheme.appliquerTheme();
    }


    /**
     * Applique la langue
     */
    public void appliquerLangage()
    {
        this.setTitle(this.ctrl.getLangage().get("supprimerTheme").get("titre"));

        this.panelSuppTheme.appliquerLangage();
    }
}
