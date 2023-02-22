package ihm.explorer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import controleur.Controleur;
import path.Path;

import java.awt.Color;
import java.awt.Component;
import java.io.File;


public class MyCellRenderer extends DefaultTreeCellRenderer
{
    private static final Icon FOLDER_ICON = new ImageIcon(Path.PATH_FOLDER_ICON);
    private static final Icon FILE_ICON   = new ImageIcon(Path.PATH_FILE_ICON);

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
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        String filePath = "";
        for(TreeNode t : ((DefaultMutableTreeNode) value).getPath())
            filePath += t.toString() + File.separator;

        filePath = filePath.substring(0, filePath.length() - 1);

        if (new File(filePath).isDirectory())
            this.setIcon(MyCellRenderer.FOLDER_ICON);
        else
            this.setIcon(MyCellRenderer.FILE_ICON);

        
        return this;
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
            this.themeUsed = sxb.build(Path.PATH_THEME_SAVE).getRootElement().getText();
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
			Element racine = sxb.build(Path.PATH_THEME_X + this.themeUsed + ".xml").getRootElement();

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
