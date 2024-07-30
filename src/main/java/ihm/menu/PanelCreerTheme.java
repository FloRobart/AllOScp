package ihm.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdom2.input.SAXBuilder;

import controleur.Controleur;

import org.jdom2.Element;
import org.jdom2.JDOMException;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import path.Path;


/*
 * Ce fichier fait partie du projet All'OScp
 * Copyright (C) 2024 Floris Robart <florobart.github@gmail.com>
 */
public class PanelCreerTheme extends JPanel implements ActionListener
{
    private final String[] TAB_CLES;

    private Controleur ctrl;

    private String nomAncienTheme;
    private File fileTheme;
    
    private JPanel pnlNomTheme;
    private JLabel lblNomTheme;
    private JTextField txtNomTheme;

    private JPanel pnlColor;
    private List<JLabel> lstLbl;
    private List<JButton> lstBtn;

    private JPanel panelSud;
    private JButton btnValider;
    private JButton btnAnnuler;

    private HashMap<String, Color> hmColorThemes;


    public PanelCreerTheme(Controleur ctrl)
    {
        this.ctrl = ctrl;
        this.setLayout(new BorderLayout(20, 20));

        /* Récupération des clés du thème */
        this.TAB_CLES = this.ctrl.getEnsClesThemes();

        /* Enregistrement du nom du thème actuel */
        this.nomAncienTheme = this.ctrl.getThemeUsed();

        /* Récupération des couleurs du thème actuel */
        this.hmColorThemes = this.ctrl.getTheme();

        /* Création du fichier du thème personnalisé */
        int nbThemePerso = this.ctrl.getNbThemesPerso() + 1;

        this.fileTheme = new File(Path.PATH_THEME_X + "perso_" + nbThemePerso + ".xml");
        try { this.fileTheme.createNewFile(); } catch (IOException e) { e.printStackTrace(); System.out.println("ERREUR lors de la création du fichier " + "theme_perso_" + nbThemePerso + ".xml"); }


        /* Copie du thème utilisé dans le thème en cours de personnalisation */
        File fileThemeUsed  = new File(Path.PATH_THEME_X + this.ctrl.getThemeUsed() + ".xml"); // origine
        File fileThemePerso = new File(Path.PATH_THEME_X + "perso_" + nbThemePerso  + ".xml"); // destination
        
        try { Files.copy(fileThemeUsed.toPath(), fileThemePerso.toPath(), StandardCopyOption.REPLACE_EXISTING); } catch (IOException e) { e.printStackTrace(); System.out.println("ERREUR lors de la copie du fichier " + "theme_" + this.ctrl.getThemeUsed() + ".xml"); }

        /* Mise à jour du nombre de thème perso */
        this.ctrl.majNbThemesPerso();

        /* changement du théme pour appliquer le thème en cours de création */
        this.ctrl.changerTheme("perso_" + nbThemePerso);


        /* Changement du nom du thème */
        this.ctrl.setNomTheme("perso " + nbThemePerso);



        /*-------------------------*/
        /* Création des composants */
        /*-------------------------*/
        /* Label nom du thème */
        this.pnlNomTheme = new JPanel();
        this.lblNomTheme = new JLabel();
        this.lblNomTheme.setFont(new Font("Liberation Sans", 0, 24));

        /* TexteField nom du thème */
        this.txtNomTheme = new JTextField("" + nbThemePerso);
        this.txtNomTheme.setFont(new Font("Liberation Sans", 0, 24));
        this.txtNomTheme.setPreferredSize(new Dimension(200, 24));


        /* Panel des couleurs */
        this.pnlColor = new JPanel(new GridLayout(this.TAB_CLES.length, 2, 0, 5));

        this.lstLbl = new ArrayList<JLabel>();
        this.lstBtn = new ArrayList<JButton>();
        for (int i = 0; i < this.TAB_CLES.length; i++)
        {
            this.lstLbl.add(new JLabel ());
            this.lstBtn.add(new JButton());
        }


        /* Panel du bas */
        this.panelSud = new JPanel();

        /* Bouton valider */
        this.btnValider = new JButton();
        this.btnValider.setPreferredSize(new Dimension(100, 30));

        /* Bouton annuler */
        this.btnAnnuler = new JButton();
        this.btnAnnuler.setPreferredSize(new Dimension(100, 30));



        /*----------------------*/
        /* Ajout des composants */
        /*----------------------*/
        /* Panel Nom du thème */
        this.pnlNomTheme.add(this.lblNomTheme);
        this.pnlNomTheme.add(this.txtNomTheme);

        /* Panel des couleurs, Ajout des labels et boutons des couleurs */
        for (int i = 0; i < this.TAB_CLES.length; i++)
        {
            this.pnlColor.add(this.lstLbl.get(i));
            this.pnlColor.add(this.lstBtn.get(i));
        }

        /* Panel du bas */
        this.panelSud.add(this.btnAnnuler);
        this.panelSud.add(this.btnValider);

        /* Ajout des panels */
        this.add(this.pnlNomTheme, BorderLayout.NORTH);
        this.add(this.pnlColor   , BorderLayout.CENTER);
        this.add(this.panelSud   , BorderLayout.SOUTH);


        /*---------------------*/
        /* Ajout des listeners */
        /*---------------------*/
        /* Boutons valider et annuler */
        this.btnValider.addActionListener(this);
        this.btnAnnuler.addActionListener(this);

        /* Boutons des couleurs */
        for (int i = 0; i < this.lstBtn.size(); i++)
            this.lstBtn.get(i).addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        /* Validé */
        if (e.getSource() == this.btnValider)
        {
            String nomTheme = "perso " + this.txtNomTheme.getText().replace("_", " ");
            if (this.ctrl.verifNomTheme(nomTheme))
            {
                /* Changement du nom du thème */
                this.ctrl.setNomTheme(nomTheme);
                this.ctrl.setNomFichier(nomTheme.replace(" ", "_"));

                /* Ajout du thème à la menuBarre */
                this.ctrl.ajouterThemePersoOnMenuBarre(nomTheme);

                /* Met à jour la liste dans le métier */
                this.ctrl.majLstNomTheme();

                /* Ferme la fenêtre */
                this.ctrl.disposeFrameCreerTheme();
            }
            else
            {
                JOptionPane.showMessageDialog(this, this.ctrl.getLangage().get("erreur").get("nomThemeInvalide"), this.ctrl.getLangage().get("erreur").get("titre"), JOptionPane.ERROR_MESSAGE);
            }
        }

        /* Annulé */
        if (e.getSource() == this.btnAnnuler)
        {
            this.cancelThemeCreation();
            this.ctrl.disposeFrameCreerTheme();
        }


        /* Boutons des couleurs */
        for (int i = 0; i < this.lstBtn.size(); i++)
        {
            if (e.getSource() == this.lstBtn.get(i))
            {
                Color color = JColorChooser.showDialog(this, this.ctrl.getLangage().get("creerTheme").get("titreColorChosser"), this.hmColorThemes.get(this.TAB_CLES[i]));
                if (color != null)
                {
                    this.hmColorThemes.put(this.TAB_CLES[i], color);
                    this.lstBtn.get(i).setBackground(color);

                    if (this.ctrl.setElementTheme(this.TAB_CLES[i], color));
                        this.ctrl.appliquerTheme();
                }
            }
        }
    }

    /**
     * Permet d'annuler la création du thème
     */
    public void cancelThemeCreation()
    {
        this.fileTheme.delete();
        this.ctrl.changerTheme(this.nomAncienTheme);
    }


    /**
     * Applique le thème à tout les composants du panel
     */
    public void appliquerTheme()
    {
        Color backGeneralColor = this.hmColorThemes.get("background");
        Color foreGeneralColor = this.hmColorThemes.get("foreground");
        Color backBtnColor     = this.hmColorThemes.get("buttonsBackground");


        /*-------------------*/
        /* Le Panel lui même */
        /*-------------------*/
        this.setBackground(backGeneralColor);
        this.setForeground(foreGeneralColor);

        /*--------------*/
        /* Nom du thème */
        /*--------------*/
        this.pnlNomTheme.setBackground(backGeneralColor);
        this.pnlNomTheme.setForeground(foreGeneralColor);

        this.lblNomTheme.setBackground(backGeneralColor);
        this.lblNomTheme.setForeground(foreGeneralColor);

        this.txtNomTheme.setBackground(backGeneralColor);
        this.txtNomTheme.setForeground(foreGeneralColor);


        /*--------------------*/
        /* Choix des couleurs */
        /*--------------------*/
        this.pnlColor.setBackground(backGeneralColor);
        this.pnlColor.setForeground(foreGeneralColor);

        for (int i = 0; i < this.TAB_CLES.length; i++)
        {
            this.lstLbl.get(i).setBackground(backGeneralColor);
            this.lstLbl.get(i).setForeground(foreGeneralColor);

            this.lstBtn.get(i).setBackground(this.hmColorThemes.get(this.TAB_CLES[i]));
            this.lstBtn.get(i).setForeground(foreGeneralColor);
        }


        /*---------------------------*/
        /* Boutons valider et annulé */
        /*---------------------------*/
        this.panelSud.setBackground(backGeneralColor);
        this.panelSud.setForeground(foreGeneralColor);

        this.btnValider.setBackground(backBtnColor);
        this.btnValider.setForeground(foreGeneralColor);

        this.btnAnnuler.setBackground(backBtnColor);
        this.btnAnnuler.setForeground(foreGeneralColor);
    }

    /**
     * Applique le langage à tout les composants du panel
     */
    public void appliquerLangage()
    {
        HashMap<String, String> hmLangageCreerTheme = this.ctrl.getLangage().get("creerTheme");

        /*--------------*/
        /* Nom du thème */
        /*--------------*/
        this.lblNomTheme.setText(hmLangageCreerTheme.get("lblNomTheme"));


        /*--------------------*/
        /* Choix des couleurs */
        /*--------------------*/
        try
        {
            String lblBtn = hmLangageCreerTheme.get("btnCouleur");
            SAXBuilder sxb = new SAXBuilder();
            List<Element> elements;

            elements = sxb.build(new File(Path.PATH_LANGAGES + "langage_" + this.ctrl.getLangageUsed() + ".xml")).getRootElement().getChild("creerTheme").getChildren();

            for (int i = 0; i < this.lstLbl.size(); i++)
            {
                this.lstLbl.get(i).setText(elements.get(i + 4).getText());
                this.lstBtn.get(i).setText(lblBtn);
            }
        }
        catch (JDOMException | IOException e) { e.printStackTrace(); }



        /*---------------------------*/
        /* Boutons valider et annulé */
        /*---------------------------*/
        this.btnValider.setText(hmLangageCreerTheme.get("btnValider"));

        this.btnAnnuler.setText(hmLangageCreerTheme.get("btnAnnuler"));
    }
}
