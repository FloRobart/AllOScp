package ihm.explorer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import controleur.Controleur;
import path.Path;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipFile;


public class MyCellRenderer extends DefaultTreeCellRenderer
{
    private static final Icon FOLDER_ICON       = MyCellRenderer.initIcon(Path.PATH_FOLDER_ICON);
    private static final Icon EMPTY_FOLDER_ICON = MyCellRenderer.initIcon(Path.PATH_EMPTY_FOLDER_ICON);
    private static final Icon FILE_ICON         = MyCellRenderer.initIcon(Path.PATH_FILE_ICON);
    private static final Icon ZIP_FILE          = MyCellRenderer.initIcon(Path.PATH_ZIP_FILE);
    private static final Icon EMPTY_ZIP_FILE    = MyCellRenderer.initIcon(Path.PATH_EMPTY_ZIP_FILE);

    private Controleur ctrl;

    private Color backgroundSelection    = null;
    private Color backgroundNonSelection = null;
    private Color foreground             = null;


    public MyCellRenderer() { super(); }

    public MyCellRenderer(Controleur ctrl)
    {
        super();
        this.ctrl = ctrl;
    }

    /**
     * Permet d'initialiser l'icon avec une résolution de 512x512 à la bonne taille pour l'affichage
     * @param pathIcon : chemin de l'icon à initialiser
     * @return Icon : icon initialisé avec la bonne taille
     */
    private static Icon initIcon(String pathIcon)
    {
        // TODO : initialiser l'icon avec une résolution de 512x512 à la bonne taille pour l'affichage
        return new ImageIcon(pathIcon);
    }

    @Override
    public Color getBackgroundSelectionColor() { return this.backgroundSelection; }

    @Override
    public Color getBackgroundNonSelectionColor() { return this.backgroundNonSelection; }

    @Override
    public Color getBackground() { return this.backgroundNonSelection; }

    @Override
    public Color getForeground() { return this.foreground; }

    @Override
    public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean selectioned, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus)
    {
        super.getTreeCellRendererComponent(tree, value, selectioned, expanded, leaf, row, hasFocus);

        File file = this.ctrl.treeNodeToFile(((DefaultMutableTreeNode) value).getPath());
        if (file.isDirectory())
            if (file.list().length == 0)
                this.setIcon(MyCellRenderer.EMPTY_FOLDER_ICON);
            else
                this.setIcon(MyCellRenderer.FOLDER_ICON);
        else
            if (file.getName().endsWith(".zip"))
            {
                try
                {
                    ZipFile zipFile = new ZipFile(file, StandardCharsets.UTF_8);
                    if (zipFile.stream().count() == 0)
                        this.setIcon(MyCellRenderer.EMPTY_ZIP_FILE);
                    else
                        this.setIcon(MyCellRenderer.ZIP_FILE);

                    zipFile.close();
                } catch (IOException e) { e.printStackTrace(); System.out.println("Erreur lors de la création du fichier ZIP");}
            }
            else
                this.setIcon(MyCellRenderer.FILE_ICON);

        return this;
    }


    /**
     * Permet d'appliquer le thème à chaque élément du panel
     */
    public void appliquerTheme()
    {
        this.backgroundNonSelection = this.ctrl.getTheme().get("background");
        this.backgroundSelection    = this.ctrl.getTheme().get("titlesBackground");
        this.foreground             = this.ctrl.getTheme().get("foreground");
    }
}
