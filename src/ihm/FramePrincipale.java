package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controleur.Controleur;
import ihm.menu.MenuBarre;
import ihm.menu.popUp.listener.PopClickListenerOnglets;


public class FramePrincipale extends JFrame
{
    private static final String PATH_LANGAGES = "./bin/donnees/langages/";

    private Controleur ctrl;

    private MenuBarre menuBarre;

    private JTabbedPane onglets;

    private List<PanelGlobal> lstPanelGlobal;
    private JPanel panelFond;

    private PopClickListenerOnglets popClickListener;


    /**
     * Constructeur de la frame principale
     * @param ctrl : Controleur qui fait le lien avec le metier
     */
    public FramePrincipale(Controleur ctrl)
    {
        this.ctrl = ctrl;


        /*--------------------------*/
        /* Créations des composants */
        /*--------------------------*/
        /* Frame */
        this.setTitle("Frame principale");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.popClickListener = new PopClickListenerOnglets(ctrl);


        /* Onglets */
        this.onglets = new JTabbedPane(JTabbedPane.TOP);
        this.onglets.setFocusTraversalKeysEnabled(false);

        /* Panels */
        this.lstPanelGlobal = new ArrayList<PanelGlobal>();
        this.lstPanelGlobal.add(new PanelGlobal(this.ctrl, this.getWidth()));
        this.panelFond = new JPanel(new BorderLayout());

        /* MenuBar */
        this.menuBarre = new MenuBarre(this.ctrl);


        /*-----------------------*/
        /* Ajouts des composants */
        /*-----------------------*/
        /* Onglets */
        this.add(this.panelFond);

        /* Panels */
        this.onglets.addTab("Onglet 1", this.lstPanelGlobal.get(0));
        this.panelFond.add(this.onglets, BorderLayout.CENTER);

        /* MenuBar */
        this.setJMenuBar(this.menuBarre);


        /*----------------------------*/
        /* Activations des composants */
        /*----------------------------*/
        /* Frame */
        this.appliquerTheme();
        this.appliquerLangage();
        this.setVisible(true);

        /* Onglets */
        this.onglets.setForegroundAt(0, Color.BLACK);
        this.onglets.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                onglets.setForegroundAt(onglets.getSelectedIndex(), Color.BLACK);
                for (int i = 0; i < lstPanelGlobal.size(); i++)
                    if (i != onglets.getSelectedIndex())
                        onglets.setForegroundAt(i, ctrl.getTheme().get("foreground"));
            }
        });

        this.onglets.addMouseListener(this.popClickListener);
    }


    /**
     * Permet d'ajouter un onglet à l'ihm
     */
    public void ajouterOnglet()
    {
        PanelGlobal pg = new PanelGlobal(this.ctrl, this.getWidth());
        this.lstPanelGlobal.add(pg);
        this.onglets.addTab("Onglet " + (this.onglets.getTabCount()+1), pg);

        // Séléctionne le nouvel onglet
        this.onglets.setSelectedIndex(this.onglets.getTabCount() - 1);

        this.appliquerTheme();
        this.appliquerLangage();
    }


    /**
     * Permet de supprimer l'onglet sélectionné
     */
    public void supprimerOnglet()
    {
        if (this.lstPanelGlobal.size() > 1)
        {
            int index = this.onglets.getSelectedIndex();
            this.lstPanelGlobal.remove(index);
            this.onglets.remove(index);
        }
        else
        {
            this.ctrl.closeParent();
        }
    }


    /**
     * Permet de sélectionner l'onglet précédent. si il n'y a pas d'onglet précédent, on vas sur le dernier onglet
     */
    public void ongletPrecedent()
    {
        int index = this.onglets.getSelectedIndex();

        if (index != -1 && this.onglets.getTabCount() > 1)
        {
            if (index == 0)
                index = this.onglets.getTabCount();

            this.onglets.setSelectedIndex(index - 1);
        }
    }


    /**
     * Permet de sélectionner l'onglet suivant. si il n'y a pas d'onglet suivant, on vas sur le premier onglet
     */
    public void ongletSuivant()
    {
        int index = this.onglets.getSelectedIndex();

        if (index != -1 && this.onglets.getTabCount() > 1)
        {
            if (index == this.onglets.getTabCount() - 1)
                index = -1;

            this.onglets.setSelectedIndex(index + 1);
        }
    }


    /**
     * Permet de fermer la fenêtre de création de thème
     */
    public void disposeFrameCreerTheme() { this.menuBarre.disposeFrameCreerTheme(); }


    /**
	 * Permet d'ajouter un nouveau thème personnalisé à la menuBarre
     * @param nomTheme : Nom du thème dans la menuBarre
	 */
	public void ajouterThemePersoOnMenuBarre(String nomTheme)
	{
		this.menuBarre.ajouterThemePersoOnMenuBarre(nomTheme);
	}

    /**
     * Permet de renommer l'onglet sélectionné
     * @param nom
     */
    public void renameOnglet(String nom)
    {
        this.onglets.setTitleAt(this.onglets.getSelectedIndex(), nom);
    }

    /**
     * Permet de dupliquer l'onglet sélectionné avec toutes ses caractéristiques
     */
    public void dupliquerOnglet() { /* TODO : A compléter */ }


    /**
     * Permet d'appliquer le thème à chaque élément de l'ihm qui en à besoins
     */
    public void appliquerTheme()
    {
        Color backGeneralColor = this.ctrl.getTheme().get("background");
        Color foreGeneralColor = this.ctrl.getTheme().get("foreground");

        /* Frame */
        this.setBackground(backGeneralColor);
        this.setForeground(foreGeneralColor);

        this.popClickListener.appliquerTheme();

        /* MenuBar */
        this.menuBarre.appliquerTheme();

        /* Onglets */
        this.onglets.setBackground(backGeneralColor);
        this.onglets.setForeground(foreGeneralColor);

        this.onglets.setForegroundAt(this.onglets.getSelectedIndex(), Color.BLACK);
        for (int i = 0; i < this.lstPanelGlobal.size(); i++)
            if (i != this.onglets.getSelectedIndex())
                this.onglets.setForegroundAt(i, foreGeneralColor);

        /* Panels */
        this.panelFond.setBackground(backGeneralColor);
        for (PanelGlobal pg : this.lstPanelGlobal)
            pg.appliquerTheme();
    }


    /**
     * Permet d'appliquer le langage à chaque élément de l'ihm qui en à besoins
     */
    public void appliquerLangage()
    {
        this.popClickListener.appliquerLangage();
        this.menuBarre.appliquerLangage();

        for (int i = 0; i < lstPanelGlobal.size(); i++)
            if (!this.VerifOngletRenommer(this.onglets.getTitleAt(i), i))
                this.onglets.setTitleAt(i, this.ctrl.getLangage().get("onglets").get("defaultTitre") + (i+1));

        for (PanelGlobal pg : this.lstPanelGlobal)
            pg.appliquerLangage();
    }


    /**
     * Permet de savoir si un onglet à été renommer
     * @param nomOnglet non de l'onglet à vérifier
     * @return true si l'onglet à été renommer, sinon false
     */
    private boolean VerifOngletRenommer(String nomOnglet, int i)
    {
        File dossier = new File(FramePrincipale.PATH_LANGAGES);
		for (File fichier : dossier.listFiles())
        {
			if (!fichier.getName().equals("langage_sauvegarde.xml"))
            {
                SAXBuilder sxb = new SAXBuilder();
                try
                {
                    System.out.println(nomOnglet + " : " + sxb.build(fichier).getRootElement().getChild("onglets").getChild("defaultTitre").getText()+(i+1));
                    if (nomOnglet.equals(sxb.build(fichier).getRootElement().getChild("onglets").getChild("defaultTitre").getText()+(i+1)))
                        return false;

                } catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de la verification du ronnomage de l'onglet"); }
            }
        }

        return true;
    }
}
