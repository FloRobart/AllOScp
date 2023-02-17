package ihm;

import javax.swing.*;
import javax.swing.tree.TreePath;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.File;
import java.awt.Color;

import controleur.Controleur;


public class PanelFonctionGlobal extends JPanel implements ActionListener
{
    private Controleur ctrl;

    private boolean arborescenceOuverte;

    private JPanel  panelFonctionGlobal;

    private JButton btnOuvrirArbo;
    private JButton btnComparerSelection;
    private JButton btnComparerTout;

    private JButton btnSyncSelectionAB;
    private JButton btnSyncSelectionBA;
    private JButton btnSyncSelectionABBA;

    private JButton btnSyncAllAB;
    private JButton btnSyncAllBA;
    private JButton btnSyncAllABBA;

    private JButton btnSyncAllInBackTask;


    public PanelFonctionGlobal(Controleur ctrl, int indexOnglet)
    {
        this.ctrl = ctrl;
        this.arborescenceOuverte = false;

        /*-------------------------*/
        /* Création des composants */
        /*-------------------------*/
        /* Ce Panel */
        this.setLayout(new BorderLayout(5, 5));

        this.panelFonctionGlobal = new JPanel(new GridLayout(2, 5, 5, 5));

        /* Boutons */
        this.btnOuvrirArbo        = new JButton("Ouvrir l'arborescence");
        this.btnComparerSelection = new JButton("Comparer la sélection");
        this.btnComparerTout      = new JButton("Comparer tout");

        this.btnSyncSelectionAB   = new JButton("Synchroniser la sélection A vers B");
        this.btnSyncSelectionBA   = new JButton("Synchroniser la sélection B vers A");
        this.btnSyncSelectionABBA = new JButton("Synchroniser la sélection A vers B et B vers A");

        this.btnSyncAllAB         = new JButton("Synchroniser tout A vers B");
        this.btnSyncAllBA         = new JButton("Synchroniser tout B vers A");
        this.btnSyncAllABBA       = new JButton("Synchroniser tout A vers B et B vers A");

        this.btnSyncAllInBackTask = new JButton("Synchroniser tout en tâche de fond");


        /*----------------------*/
        /* Ajout des composants */
        /*----------------------*/
        /* Ce Panel */
        this.panelFonctionGlobal.add(this.btnOuvrirArbo);
        this.panelFonctionGlobal.add(this.btnComparerSelection);
        this.panelFonctionGlobal.add(this.btnComparerTout);
        
        this.panelFonctionGlobal.add(this.btnSyncSelectionAB);
        this.panelFonctionGlobal.add(this.btnSyncSelectionBA);
        this.panelFonctionGlobal.add(this.btnSyncSelectionABBA);

        this.panelFonctionGlobal.add(this.btnSyncAllAB);
        this.panelFonctionGlobal.add(this.btnSyncAllBA);
        this.panelFonctionGlobal.add(this.btnSyncAllABBA);

        this.panelFonctionGlobal.add(this.btnSyncAllInBackTask);

        this.add(this.panelFonctionGlobal, BorderLayout.CENTER);


        /*---------------------------*/
        /* Activation des composants */
        /*---------------------------*/
        this.btnOuvrirArbo.addActionListener(this);
        this.btnComparerSelection.addActionListener(this);
        this.btnComparerTout.addActionListener(this);

        this.btnSyncSelectionAB.addActionListener(this);
        this.btnSyncSelectionBA.addActionListener(this);
        this.btnSyncSelectionABBA.addActionListener(this);

        this.btnSyncAllAB.addActionListener(this);
        this.btnSyncAllBA.addActionListener(this);
        this.btnSyncAllABBA.addActionListener(this);

        this.btnSyncAllInBackTask.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        /* Ouverture et fermeture de l'arborescence */
        if (e.getSource() == this.btnOuvrirArbo)
        {
            if (!this.arborescenceOuverte)
            {
                this.ctrl.getArborescence("gauche").ouvrirArborescence();
                this.ctrl.getArborescence("droite").ouvrirArborescence();

                this.btnOuvrirArbo.setText("Fermer l'arborescence");
                this.arborescenceOuverte = true;
            }
            else
            {
                this.ctrl.getArborescence("gauche").fermerArborescence();
                this.ctrl.getArborescence("droite").fermerArborescence();

                this.btnOuvrirArbo.setText("Ouvrir l'arborescence");
                this.arborescenceOuverte = false;
            }
        }

        /* Comparaison des éléments sélectionnées */
        if (e.getSource() == this.btnComparerSelection)
        {
            TreePath[] tpSelectionGauche = this.ctrl.getArborescence("gauche").getSelectionPaths();
            TreePath[] tpSelectionDroite = this.ctrl.getArborescence("droite").getSelectionPaths();

            if (tpSelectionGauche != null && tpSelectionDroite != null && tpSelectionGauche.length == tpSelectionDroite.length)
            {
                for (int i = 0; i < tpSelectionDroite.length; i++)
                {
                    String pathFileGauche = "";
                    String pathFileDroite = "";

                    for (Object o : tpSelectionGauche[i].getPath())
                        pathFileGauche += o.toString() + "\\";

                    for (Object o : tpSelectionDroite[i].getPath())
                        pathFileDroite += o.toString() + "\\";

                    pathFileGauche = pathFileGauche.substring(0, pathFileGauche.length() - 1);
                    pathFileDroite = pathFileDroite.substring(0, pathFileDroite.length() - 1);

                    File fileGauche = new File(pathFileGauche);
                    File fileDroite = new File(pathFileDroite);

                    this.ctrl.comparer(fileGauche, fileDroite);
                }
            }
        }
    }


    /**
     * Permet d'appliquer le thème à chaque élément du panel
     */
    public void appliquerTheme()
    {
        Color backGeneralColor = this.ctrl.getTheme().get("background");
        Color foreGeneralColor = this.ctrl.getTheme().get("foreground");
        Color btnBackColor     = this.ctrl.getTheme().get("buttonsBackground");

        this.setBackground(backGeneralColor);
        this.setForeground(foreGeneralColor);


        this.panelFonctionGlobal.setBackground(backGeneralColor);
        this.panelFonctionGlobal.setForeground(foreGeneralColor);

        this.btnOuvrirArbo.setBackground(btnBackColor    );
        this.btnOuvrirArbo.setForeground(foreGeneralColor);

        this.btnComparerSelection.setBackground(btnBackColor    );
        this.btnComparerSelection.setForeground(foreGeneralColor);

        this.btnComparerTout.setBackground(btnBackColor    );
        this.btnComparerTout.setForeground(foreGeneralColor);

        this.btnSyncSelectionAB.setBackground(btnBackColor    );
        this.btnSyncSelectionAB.setForeground(foreGeneralColor);

        this.btnSyncSelectionBA.setBackground(btnBackColor    );
        this.btnSyncSelectionBA.setForeground(foreGeneralColor);

        this.btnSyncSelectionABBA.setBackground(btnBackColor    );
        this.btnSyncSelectionABBA.setForeground(foreGeneralColor);

        this.btnSyncAllAB.setBackground(btnBackColor    );
        this.btnSyncAllAB.setForeground(foreGeneralColor);

        this.btnSyncAllBA.setBackground(btnBackColor    );
        this.btnSyncAllBA.setForeground(foreGeneralColor);

        this.btnSyncAllABBA.setBackground(btnBackColor    );
        this.btnSyncAllABBA.setForeground(foreGeneralColor);

        this.btnSyncAllInBackTask.setBackground(btnBackColor    );
        this.btnSyncAllInBackTask.setForeground(foreGeneralColor);
    }

    /**
     * Permet d'appliquer le langage à chaque élément du panel
     */
    public void appliquerLangage()
    {
        
    }
}
