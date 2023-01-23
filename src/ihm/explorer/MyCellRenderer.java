package ihm.explorer;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import controleur.Controleur;

import java.awt.Color;
import java.awt.Component;


public class MyCellRenderer extends DefaultTreeCellRenderer
{
    private static final String PATH_THEME_X    = "./bin/donnees/themes/theme_";
    private static final String PATH_THEME_SAVE = "./bin/donnees/themes/theme_sauvegarde.xml";

    private String themeUsed;

    private Color backgroundSelection    = null;
    private Color backgroundNonSelection = null;
    private Color foreground             = null;


    public MyCellRenderer() { super(); }

    public MyCellRenderer(Controleur ctrl)
    {
        super();

        this.chargerThemes();
    }

    @Override
    public Color getBackgroundSelectionColor()
    {
        if (this.backgroundSelection == null)
            this.chargerThemes();
        
        return this.backgroundSelection;
    }

    @Override
    public Color getBackgroundNonSelectionColor()
    {
        if (this.backgroundNonSelection == null)
            this.chargerThemes();
        
        return this.backgroundNonSelection;
    }

    @Override
    public Color getBackground()
    {
        if (this.backgroundNonSelection == null)
            this.chargerThemes();
        
        return this.backgroundNonSelection;
    }

    @Override
    public Color getForeground()
    {
        if (this.foreground == null)
            this.chargerThemes();
        
        return this.foreground;
    }


    @Override
    public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus)
    {
        final Component ret = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        final DefaultMutableTreeNode node = ((DefaultMutableTreeNode) (value));
        this.setText(value.toString());
        return ret;
    }



    /**
     * Récupère le thème utilisé dans le fichier xml de sauvegarde
     * @return String : thème à utilisé
     */
    public void getThemeUsed()
    {
        SAXBuilder sxb = new SAXBuilder();
        try
        {
            this.themeUsed = sxb.build(MyCellRenderer.PATH_THEME_SAVE).getRootElement().getText();
        }
        catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de la lecture du fichier XML du themes utilisé"); }
    }


    /**
	 * Charge les couleurs du thème choisi par l'utilisateur dans la HashMap
	 * @param theme : thème à charger
	 * @return HashMap contenant les couleurs du thème
	 */
	public void chargerThemes()
	{
        this.getThemeUsed();

		SAXBuilder sxb = new SAXBuilder();
		try
		{
			Element racine = sxb.build(MyCellRenderer.PATH_THEME_X + this.themeUsed + ".xml").getRootElement();

			/*----------------------------------------------*/
			/* Récupération des couleurs de chaque éléments */
			/*----------------------------------------------*/
            Element cg = racine.getChild("titlesBackground");
            this.backgroundSelection = new Color( Integer.parseInt(cg.getAttributeValue("red")), Integer.parseInt(cg.getAttributeValue("green")), Integer.parseInt(cg.getAttributeValue("blue")), Integer.parseInt(cg.getAttributeValue("alpha")));
            
            cg = racine.getChild("background");
            this.backgroundNonSelection = new Color( Integer.parseInt(cg.getAttributeValue("red")), Integer.parseInt(cg.getAttributeValue("green")), Integer.parseInt(cg.getAttributeValue("blue")), Integer.parseInt(cg.getAttributeValue("alpha")));
            
            cg = racine.getChild("foreground");
            this.foreground = new Color( Integer.parseInt(cg.getAttributeValue("red")), Integer.parseInt(cg.getAttributeValue("green")), Integer.parseInt(cg.getAttributeValue("blue")), Integer.parseInt(cg.getAttributeValue("alpha")));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Erreur lors de la lecture du fichier XML des informations du theme");
		}
	}


    /**
     * Permet d'appliquer le thème à chaque élément du panel
     */
    public void appliquerTheme()
    {
        this.chargerThemes();
    }
}
