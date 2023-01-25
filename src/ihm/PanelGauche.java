package ihm;

import javax.swing.CellRendererPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import java.awt.BorderLayout;

import java.awt.Color;

import controleur.Controleur;
import ihm.explorer.MyCellRenderer;


public class PanelGauche extends JPanel
{
    private Controleur ctrl;

    private JScrollPane scrollPane;

    private JTree       arborescence;

    private MyCellRenderer mycellRenderer;

    public PanelGauche(Controleur ctrl)
    {
        this.ctrl = ctrl;

        /*--------------------------*/
        /* Créations des composants */
        /*--------------------------*/
        /* Ce Panel */
        this.setLayout(new BorderLayout());

        /* ScrollPane */
        this.scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.setSize(200, 400);

        /* Arborescence */
        this.arborescence = new JTree();
        this.arborescence.setRootVisible(true);
        this.arborescence.setShowsRootHandles(false);

        this.mycellRenderer = new MyCellRenderer(ctrl);
        this.arborescence.setCellRenderer(this.mycellRenderer);


        /*-----------------------*/
        /* Ajouts des composants */
        /*-----------------------*/
        /* ScrollPane */
        this.scrollPane.setViewportView(this.arborescence);

        /* Ce Panel */
        this.add(this.scrollPane);


        /*---------------------------*/
        /* Activtions des composants */
        /*---------------------------*/
    }


    /**
     * Permet d'appliquer le thème à chaque élément du panel
     */
    public void appliquerTheme()
    {
        Color backGeneralColor = this.ctrl.getTheme().get("background");
        Color foreGeneralColor = this.ctrl.getTheme().get("foreground");

        this.setBackground(backGeneralColor);
        this.setForeground(foreGeneralColor);

        /* ScrollPane */
        this.scrollPane.setBackground(backGeneralColor);
        this.scrollPane.setForeground(foreGeneralColor);

        this.scrollPane.getViewport().setBackground(backGeneralColor);
        this.scrollPane.getViewport().setForeground(foreGeneralColor);

        this.scrollPane.getHorizontalScrollBar().setBackground(backGeneralColor);
        this.scrollPane.getVerticalScrollBar().setBackground(backGeneralColor);

        /* Arborescence */
        this.arborescence.setBackground(backGeneralColor);
        this.arborescence.setForeground(foreGeneralColor);
        this.mycellRenderer.appliquerTheme();
    }
}
