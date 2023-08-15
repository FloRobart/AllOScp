package ihm.explorer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import ihm.explorer.MyMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import controleur.Controleur;
import path.Path;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipFile;


public class MyTreeCellRenderer extends DefaultTreeCellRenderer
{
    private static final Icon FOLDER_ICON       = MyTreeCellRenderer.initIcon(Path.PATH_FOLDER_ICON, 1);
    private static final Icon EMPTY_FOLDER_ICON = MyTreeCellRenderer.initIcon(Path.PATH_EMPTY_FOLDER_ICON, 1);
    private static final Icon FILE_ICON         = MyTreeCellRenderer.initIcon(Path.PATH_FILE_ICON, 1);
    private static final Icon ZIP_FILE          = MyTreeCellRenderer.initIcon(Path.PATH_ZIP_FILE, 1);
    private static final Icon EMPTY_ZIP_FILE    = MyTreeCellRenderer.initIcon(Path.PATH_EMPTY_ZIP_FILE, 1);

    private Controleur ctrl;

    private Color backgroundSelection   ;
    private Color backgroundNonSelection;
    private Color foreground            ;


    public MyTreeCellRenderer()
    {
        super();
    }

    public MyTreeCellRenderer(Controleur ctrl)
    {
        super();
        this.ctrl = ctrl;
    }

    /**
     * Permet d'initialiser l'icon avec une résolution de 512x512 à la bonne taille pour l'affichage
     * @param pathIcon : chemin de l'icon à initialiser
     * @param zoomFactor le facteur de zoom à utiliser pour redimensionner les icônes
     * @return Icon : icon initialisé avec la bonne taille
     */
    private static Icon initIcon(String pathIcon, double zoomFactor)
    {
        Image image = new ImageIcon(pathIcon).getImage();
        return new ImageIcon(image.getScaledInstance((int)(image.getWidth(null) * zoomFactor), (int)(image.getHeight(null) * zoomFactor), Image.SCALE_SMOOTH));
    }

    @Override
    public Color getBackgroundSelectionColor() { return this.backgroundSelection; }

    @Override
    public Color getBackgroundNonSelectionColor() { return this.backgroundNonSelection; }

    @Override
    public Color getTextSelectionColor() { return this.foreground; }

    @Override
    public Color getTextNonSelectionColor() { return this.foreground; }


    public void setBackgroundSelectionColor   (Color color) { this.backgroundSelection    = color; }
    public void setBackgroundNonSelectionColor(Color color) { this.backgroundNonSelection = color; }
    public void setTextSelectionColor         (Color color) { this.foreground = color; }
    public void setTextNonSelectionColor      (Color color) { this.foreground = color; }
    

    @Override
    public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean selectioned, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus)
    {
        super.getTreeCellRendererComponent(tree, value, selectioned, expanded, leaf, row, hasFocus);

        File file = this.ctrl.treeNodeToFile(((MyMutableTreeNode) value).getPath());
        if (file.isDirectory())
        {
            if (file.list().length == 0)
                this.setIcon(MyTreeCellRenderer.EMPTY_FOLDER_ICON);
            else
                this.setIcon(MyTreeCellRenderer.FOLDER_ICON);
        }
        else
        {
            if (file.getName().endsWith(".zip"))
            {
                try
                {
                    ZipFile zipFile = new ZipFile(file, StandardCharsets.UTF_8);
                    if (zipFile.stream().count() == 0)
                        this.setIcon(MyTreeCellRenderer.EMPTY_ZIP_FILE);
                    else
                        this.setIcon(MyTreeCellRenderer.ZIP_FILE);

                    zipFile.close();
                } catch (IOException e) { e.printStackTrace(); System.out.println("Erreur lors de la création du fichier ZIP");}
            }
            else
                this.setIcon(MyTreeCellRenderer.FILE_ICON);
        }

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
