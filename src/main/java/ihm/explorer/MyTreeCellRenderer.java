package ihm.explorer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import ihm.explorer.MyMutableTreeNode;
import controleur.Controleur;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import path.Path;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipFile;


/*
 * Ce fichier fait partie du projet All'OScp
 * Copyright (C) 2024 Floris Robart <florobart.github@gmail.com>
 */
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

    private boolean hovering;


    public MyTreeCellRenderer()
    {
        super();
        this.hovering = false;
    }

    public MyTreeCellRenderer(Controleur ctrl)
    {
        super();
        this.ctrl = ctrl;
        this.hovering = false;
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
    public Color getBackgroundNonSelectionColor()
    {
        
        
        return this.backgroundNonSelection;
    }

    @Override
    public Color getTextSelectionColor() { return this.foreground; }

    @Override
    public Color getTextNonSelectionColor() { return this.foreground; }

    @Override
    public Color getBorderSelectionColor() { return this.ctrl.getTheme().get("enableColor"); }

    @Override
    public void setBackgroundSelectionColor   (Color color) { this.backgroundSelection    = color; }

    @Override
    public void setBackgroundNonSelectionColor(Color color) { this.backgroundNonSelection = color; }

    @Override
    public void setTextSelectionColor         (Color color) { this.foreground = color; }

    @Override
    public void setTextNonSelectionColor      (Color color) { this.foreground = color; }

    @Override
    public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean selectioned, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus)
    {
        super.getTreeCellRendererComponent(tree, value, selectioned, expanded, leaf, row, hasFocus);
        MyMutableTreeNode node = (MyMutableTreeNode) value;

        try
        {
            if (this.ctrl.getHovering() == node)
                this.setBackgroundNonSelectionColor(this.ctrl.getTheme().get("saisiesPlaceholder"));
            else
                this.setBackgroundNonSelectionColor(this.ctrl.getTheme().get("background"));
        } catch (NullPointerException e) { this.setBackgroundNonSelectionColor(this.ctrl.getTheme().get("background")); }

        File file = this.ctrl.treeNodeToFile(node.getPath());
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

    public void setHovering(boolean hovering)
    {
        this.hovering = hovering;
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
