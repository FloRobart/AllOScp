package metier;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import controleur.Controleur;
import ihm.explorer.Explorer;
import path.Path;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipFile;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class Metier
{
    private Controleur ctrl;

	/* Métier local */
	private TreePath tpToCut;
	private boolean cut;

	/* Thèmes */
	private int                     nbThemePerso;
	private List<String>            lstNomThemesPerso;
    private HashMap<String, Color>  hmColorTheme;

	/* Langages */
	private HashMap<String, HashMap<String, String>> hmLangage;


    public Metier(Controleur ctrl)
    {
        this.ctrl = ctrl;

		/* Métier local */
		this.tpToCut = null;
		this.cut = false;

		/* Thèmes */
		this.nbThemePerso      = this.initNbThemePerso();
		this.lstNomThemesPerso = this.initLstNameThemesPerso();
        this.hmColorTheme     = new HashMap<String, Color>();
        this.chargerThemes(this.getThemeUsed());

		/* Langages */
		this.hmLangage = new HashMap<String, HashMap<String, String>>();
		this.chargerLangage(this.getLangageUsed());
    }


	/*======================*/
	/* Metier pour le local */
	/*======================*/
	/*------------------*/
	/* Méthode générale */
	/*------------------*/
	/**
	 * Permet de convertir un TreePath en File
	 * @param tp : TreePath à convertir
	 * @return File : fichier correspondant au TreePath passé en paramètre
	 */
	public File treePathToFile(TreePath tp)
	{
		String filePath = "";
		for (Object o : tp.getPath())
			filePath += o.toString() + File.separator;

		return new File(filePath.substring(0, filePath.length() - 1));
	}

	/**
     * Permet de convertir un tableau de TreeNode en File
     * @param tn : tableau de TreeNode à convertir
     * @return fichier correspondant au tableau de TreeNode passé en paramètre
     */
    public File treeNodeToFile(TreeNode[] tn)
	{
		String filePath = "";
		for (TreeNode t : tn)
			filePath += t.toString() + File.separator;

		return new File(filePath.substring(0, filePath.length() - 1));
	}

	/**
	 * Permet de récupérer l'extension d'un fichier
	 * @param fileToGetExt : fichier dont on veut récupérer l'extension
	 * @return String : extension du fichier passé en paramètre (sans le point)
	 */
	public String getFileExtension(File fileToGetExt)
	{
		return fileToGetExt.getName().substring(fileToGetExt.getName().lastIndexOf(".") + 1);
	}

	/**
	 * Permet de définir la suppression d'un fichier ou d'un dossier après l'avoir copier
	 * @param b : true si on veut supprimer le fichier ou le dossier après l'avoir copier, sinon false
	 */
	public void setCut(boolean b)
	{
		this.cut = b;
	}

	/**
     * Permet d'obtenir la liste des fils d'un noeud parent de type DefaultMutableTreeNode
     * @param nodeToGetChildren : noeud pour lequel on veux obtenir les fils
     * @return la liste des noeuds fils de type DefaultMutableTreeNode
     */
    public List<DefaultMutableTreeNode> getChildrenNodes(TreeNode nodeToGetChildren)
    {
        if (nodeToGetChildren == null) throw new NullPointerException("node == null");

        List<DefaultMutableTreeNode> children = new ArrayList<DefaultMutableTreeNode>(nodeToGetChildren.getChildCount());
        for (Enumeration<?> enumeration = nodeToGetChildren.children(); enumeration.hasMoreElements();)
        {
            Object nextElement = enumeration.nextElement();
            if (nextElement instanceof DefaultMutableTreeNode)
                children.add((DefaultMutableTreeNode) nextElement);
        }

        return children;
    }


	/*-------------------------------*/
	/* Méthode panel fonction global */
	/*-------------------------------*/
	/**
	 * Permet de comparer des fichiers ou des dossiers.
	 * @param fileGauche : fichier ou dossier provenant du panel gauche
	 * @param fileDroite : fichier ou dossier provenant du panel droit
	 */
	public String comparer(File fileGauche, File fileDroite)
	{
		String sRet = "";
		if (fileGauche.isDirectory() && fileDroite.isDirectory())
		{
			File[] lstFileGauche = fileGauche.listFiles();
			File[] lstFileDroite = fileDroite.listFiles();

			for (int i = 0; i < lstFileGauche.length; i++)
				for (int j = 0; j < lstFileDroite.length; j++)
					sRet += this.comparer(lstFileGauche[i], lstFileDroite[j]) + "\n";
		}
		else if (fileGauche.isFile() && fileDroite.isFile())
		{
			if (this.comparerFichier(fileGauche, fileDroite))
				sRet += ("Les fichiers '" + fileGauche.getName() + "' et '" + fileDroite.getName() + "' sont identiques\n");
			else
				sRet += ("Les fichiers '" + fileGauche.getName() + "' et '" + fileDroite.getName() + "' sont différents\n");
		}
		else
		{
			sRet += ("Un dossier ne peut pas être comparer avec un fichier\n");
		}

		System.out.print(sRet);
		return sRet;
	}

	/**
	 * Permet de comparer deux fichiers byte par byte.
	 * @param fileGauche : fichier provenant du panel gauche
	 * @param fileDroite : fichier provenant du panel droit
	 * @return boolean : true si les fichiers sont identiques, sinon false
	 */
	private boolean comparerFichier(File fileGauche, File fileDroite)
	{
		try
		{
			byte[] ensByteGauche = Files.readAllBytes(fileGauche.toPath());
			byte[] ensByteDroite = Files.readAllBytes(fileDroite.toPath());

			if (ensByteGauche.length == ensByteDroite.length)
			{
				boolean identique = true;

				for (int i = 0; i < ensByteGauche.length; i++)
					if (ensByteGauche[i] != ensByteDroite[i]) { identique = false; break; }

				return identique;
			}
		} catch (IOException ex) { ex.printStackTrace(); }

		return false;
	}

	/*----------------*/
	/* FolderListener */
	/*----------------*/
	/**
     * Permet de supprimer les écouteurs d'évènements d'un dossier
     * @param fileToListen : chemin absolut du dossier à écouter
     */
	public synchronized void addFolderListener(File fileToListen)
	{
		
	}

	/**
     * Permet de supprimer les écouteurs d'évènements d'un dossier
     * @param fileToListen : chemin absolut du dossier à écouter
     */
    public synchronized void removeFolderListener(File fileToListen)
	{
		
	}

	/*--------------------*/
	/* Option click droit */
	/*--------------------*/
	/**
     * Permet de changer la racine de l'arborescence
     * @param arborescence : arborescence sur le quel changer la racine (le lecteur)
     * @return boolean : true si le changement à réussi, sinon false
     */
    public boolean changeDrive(Explorer arborescence)
	{
		this.cut = false;
		// TODO : changer le driver

		return false;
	}

    /**
     * Permet d'ouvrire le fichier (ou le dossier) passé en paramètre
     * @param arborescence : arborescence dans le quel ouvrir le dossier
     * @param fileToOpen : fichier à ouvrir
     * @return boolean : true si l'ouverture à réussi, sinon false
     */
    public boolean openFile(File fileToOpen)
	{
		this.cut = false;

		try { Desktop.getDesktop().open(fileToOpen); }
        catch (IOException ex) { Logger.getLogger(Explorer.class.getName()).log(Level.SEVERE, "Erreur lors de l'ouverture du fichier '" + fileToOpen.getAbsolutePath() + "'", ex); ex.printStackTrace(); System.out.println("Erreur lors de l'ouverture du fichier '" + fileToOpen.getAbsolutePath() + "'"); return false; }

		return true;
	}

	/**
     * Permet d'ouvrire le fichier (ou le dossier) passé en paramètre avec l'application qu'on veux
     * @param arborescence : arborescence dans le quel ouvrir le dossier
     * @param fileToOpen : fichier à ouvrir
     * @return boolean : true si l'ouverture à réussi, sinon false
     */
    public boolean openFileWith(File fileToOpen)
	{
		this.cut = false;
		// TODO : ouvrir le fichier avec l'application choisie par l'utilisateur

		return false;
	}

	/**
     * Permet de modifier le fichier passé en paramètre
     * @param arborescence : arborescence dans le quel ouvrir le dossier
     * @param fileToEdit : fichier à modifier
     * @return boolean : true si l'ouverture en écriture à réussi, sinon false
     */
    public boolean editFile(File fileToEdit)
	{
		this.cut = false;

		try { Desktop.getDesktop().edit(fileToEdit); }
        catch (IOException ex)
		{
			if (this.getFileExtension(fileToEdit).equals("zip"))
				return this.openFile(fileToEdit); 

			return false;
		}

		return true;
	}

    /**
     * Permet de renommer un fichier ou un dossier
     * @param arborescence : arborescence dans le quel renommer le fichier ou le dossier
     * @param fileToRename : fichier ou dossier à renommer
     * @return boolean : true si le renommage à réussi, sinon false
     */
    public boolean rename(Explorer arborescence, File fileToRename)
	{
		this.cut = false;

		// TODO : renommer le fichier ou le dossier
		//arborescence.startEditingAtPath(arborescence.getSelectionPath());

		return false;
	}

	/**
     * Permet de créer un nouvelle élements (fichier ou dossier) ET d'ajouter l'élement à l'arborescence
     * @param arborescence : arborescence dans le quel créer l'élement
     * @param folderDestination : dossier dans le quel créer l'élement
	 * @param type : 0 pour un fichier, 1 pour un dossier
     * @return boolean : true si la création à réussi, sinon false
     */
    public boolean newElement(Explorer arborescence, File folderDestination, int type)
	{
		String fileName = this.hmLangage.get("popUpMenu").get("newFileName");
		if (type != 0) { fileName = this.hmLangage.get("popUpMenu").get("newFolderName"); }

		TreePath tpParent = arborescence.getSelectionPath();
		File fileToCreate = this.treePathToFile(arborescence.getSelectionPath());
		if (fileToCreate.isDirectory())
			fileToCreate = new File(fileToCreate.getAbsolutePath() + File.separator + fileName);
		else
			{ tpParent = tpParent.getParentPath(); fileToCreate = new File(fileToCreate.getParent() + File.separator + fileName); }
		
		String fileNameCreated;
		if (type == 0)
			fileNameCreated = this.createNewFile(fileToCreate, 0);
		else if (type == 1)
			fileNameCreated = this.createNewFolder(fileToCreate, 0);
		else
			throw new IllegalArgumentException("Le type doit être 0 pour un fichier ou 1 pour un dossier");

		if (fileNameCreated != null)
			arborescence.insertNode(fileNameCreated, tpParent);
		else
			return false;

		return true;
	}

    /**
     * Permet de supprimer un fichier ou un dossier (ainsi que tout son contenu) ET de supprimer l'élement de l'arborescence
     * @param arborescence : arborescence dans le quel se trouve le fichier ou le dossier à supprimer
     * @param fileToDelete : fichier ou dossier à supprimer
     * @return boolean : true si la suppression à réussi, sinon false
     */
    public void deleteElement(Explorer arborescence, File fileToDelete)
	{
		this.deleteFile(fileToDelete);
		arborescence.removeNode((MutableTreeNode) (arborescence.getSelectionPath().getLastPathComponent()));
	}

    /**
     * Permet de copier un fichier ou un dossier (ainsi que tout les dossiers et fichiers qu'il contient).
     * Copie le fichier ou le dossier dans le dossier dans le press-papier, donc il peut être coller dans une autre application.
     * @param fileToCopy : fichier ou dossier à copier
     * @return boolean : true si la copie à réussi, sinon false
     */
    public void copyElement(File fileToCopy, boolean cut)
	{
		List<File> lstFiles = new ArrayList<File>();
		lstFiles.add(fileToCopy);
		this.copyElements(lstFiles, cut);
	}

	/**
     * Permet de copier des fichiers.
     * Copie les fichiers dans le press-papier, donc il peut être coller dans une autre application.
     * @param filesToCopy : fichiers à copier
     * @return boolean : true si la copie à réussi, sinon false
     */
    public void copyElements(List<File> filesToCopy, boolean cut)
	{
		this.cut = cut;
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new FileTransferable(filesToCopy), null);
	}

    /**
     * Permet de copier le chemin absolut d'un fichier ou d'un dossier.
     * @param pathToCopy : chemin absolut du fichier ou du dossier à copier
     * @return boolean : true si la copie à réussi, sinon false
     */
    public void copyPath(String pathToCopy)
	{
		this.cut = false;
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection("\"" + pathToCopy + "\""), null);
	}

    /**
     * Permet de couper un fichier ou un dossier (ainsi que tout les dossiers et fichiers qu'il contient).
     * Couper un fichier ou un dossier revient à le copier puis à le supprimer.
     * @param arborescence : arborescence dans le quel se trouve le fichier ou le dossier à couper
     * @param fileToCut : fichier ou dossier à couper
     * @return boolean : true si le coupage à réussi, sinon false
     */
    public void cutElement(Explorer arborescence, File fileToCut)
	{
		this.cut = true;
		this.tpToCut = arborescence.getSelectionPath();
		this.copyElement(fileToCut, true);
	}

    /**
     * Permet de coller un fichier ou un dossier (ainsi que tout les dossiers et fichiers qu'il contient).
     * Le fichier ou le dossier à coller est celui qui est présent dans le presse-papier donc peux avoir été copier depuis une autre application.
     * @param arborescence : arborescence dans le quel se trouve le dossier de destination
     * @param folderDestination : dossier dans le quel coller le fichier ou le dossier
     * @return boolean : true si le collage à réussi, sinon false
     */
	@SuppressWarnings("unchecked")
    public void pasteElement(Explorer arborescence, File folderDestination)
	{
        try
        {
			TreePath tp = arborescence.getSelectionPath();
			if (!this.treePathToFile(tp).isDirectory()) 
				tp = tp.getParentPath();

            Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
                for (File f : ((List<File>) t.getTransferData(DataFlavor.javaFileListFlavor)))
				{
					if (f.isDirectory()) this.pasteFolderRec(f, new File(folderDestination.getAbsolutePath() + File.separator + f.getName()), 0);
                    else this.pasteFile(f, new File(folderDestination.getAbsolutePath() + File.separator + f.getName()));

					arborescence.insertNode(f.getName(), tp);
					if (this.cut)
					{
						arborescence.removeNode((MutableTreeNode) (this.tpToCut.getLastPathComponent()));
						this.deleteFile(f);
					}
				}
        }
        catch (Exception ex) { ex.printStackTrace(); System.out.println("Erreur lors dde la récupération des fichiers dans le clipboard"); }

		this.cut = false;
	}

    /**
     * Permet d'fficher une fenêtre de dialogue avec tout les propriétés d'un fichier ou d'un dossier.
     * @param fileToGetProperties : fichier ou dossier dont on veut afficher les propriétés
     */
    public void properties(File fileToGetProperties)
	{
		//String properties = "";
		System.out.println("Nom                   : " + fileToGetProperties.getName());
		System.out.println("Chemin                : " + fileToGetProperties.getAbsolutePath());
		System.out.println("Dossier               : " + fileToGetProperties.isDirectory());
		System.out.println("Fichier               : " + fileToGetProperties.isFile());
		//System.out.println("Taille                : " + fileToGetProperties.length());
		//System.out.println("Dernière modification : " + fileToGetProperties.lastModified());
		//System.out.println("Lecture               : " + fileToGetProperties.canRead());
		//System.out.println("Ecriture              : " + fileToGetProperties.canWrite());
		//System.out.println("Exécution             : " + fileToGetProperties.canExecute());
		//System.out.println("Caché                 : " + fileToGetProperties.isHidden());
		//System.out.println("Lien physique         : " + fileToGetProperties.isAbsolute());
		//System.out.println("getUsableSpace        : " + fileToGetProperties.getUsableSpace());
		//System.out.println("getTotalSpace         : " + fileToGetProperties.getTotalSpace());
		//System.out.println("getFreeSpace          : " + fileToGetProperties.getFreeSpace());
		//System.out.println("getParent             : " + fileToGetProperties.getParent());

		//String zipName = "G:\\Mon Drive\\Projet_perso\\Test_Sync\\panelGauche\\fichierZip.zip";
		//String filenameInZip = "fichierInZip.txt";
		//try(FileSystem zip = FileSystems.newFileSystem(Paths.get(zipName)))
		//{
		//	java.nio.file.Path fileInZip = zip.getPath(filenameInZip);
		//	
		//	// et là on utilise fileInZip, un Path qui pointe vers un fichier dans le zip,
		//	// comme on utiliserait un Path qui pointe vers un fichier normal :
		//	try(Stream<String> lines = Files.lines(fileInZip, StandardCharsets.UTF_8))
		//	{
		//		lines.forEach(System.out::println);
		//	} catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de la lecture du fichier ZIP"); }
		//
		//}catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de création du fichier ZIP"); }


		// virifier le type du fichier
		if (this.getFileExtension(fileToGetProperties).equals("zip"))
		{
			try
			{
				ZipFile zipFile = new ZipFile(fileToGetProperties, StandardCharsets.UTF_8);
				zipFile.stream().forEach(System.out::println);

				zipFile.close();
			} catch (IOException e) { e.printStackTrace(); System.out.println("Erreur lors de la création du fichier ZIP");}
		}



		//new FrameProperties(this.ctrl, this.ctrl.getFramePrincipale(), properties);
	}

	/**
     * Permet de mettre à jour l'arborescence à partir du noeud passé en paramètre
     * @param arborescence : arborescence à rafraichir
     * @param tp : chemin du dossier à rafraichir
     */
	public void refresh(Explorer arborescence, TreePath tp)
	{
		File fileSelected = this.treePathToFile(tp);
		TreeNode selectionnedNode = (TreeNode)(tp.getLastPathComponent());
		while(selectionnedNode.getChildCount() != 0)
			arborescence.removeNode(selectionnedNode.getChildAt(0));

		if (fileSelected.list().length != 0)
		{
			for (File f : fileSelected.listFiles())
				arborescence.insertNode(f.getName(), tp); /* Génére les noeuds fils sur un seul étage */

			arborescence.expandPath(tp); /* Ouverture du noeud séléctionnée */
		}
	}

	/**
     * Permet de créer un nouveau fichier 
     * @param fileToCreate : le fichier à créer
	 * @param i : le numéro du fichier à créer (si le fichier existe déjà). Si i = 0, alors le fichier n'aura pas de numéro.
	 * @return String : le nom du fichier créé. En cas d'erreur lors de la création du fichier, la méthode retourne null.
     */
    public String createNewFile(File fileToCreate, int i)
    {
        try
        {
            if (fileToCreate.createNewFile())
                return fileToCreate.getName();
            else
			{
				String name = fileToCreate.getAbsolutePath();
				String extension = name.substring(name.lastIndexOf("."), name.length());

				if (i == 0)
					name = name.substring(0, name.lastIndexOf("."));
				else
					name = name.substring(0, name.lastIndexOf("_("));

				fileToCreate = new File(name + "_(" + (++i) + ")" + extension);
				return this.createNewFile(fileToCreate, i);
			}
        }
        catch (Exception ex) { ex.printStackTrace(); System.out.println("Erreur lors de la création du fichier"); return null; }
    }

	/**
     * Permet de supprimer un dossier et son contenu
     * @param folderToCreate : le dossier à supprimer
	 * @param i : le numéro du dossier à créer (si le dossier existe déjà). Si i = 0, alors le dossier n'aura pas de numéro.
	 * @return String : le nom du dossier créé. En cas d'erreur lors de la création du dossier, la méthode retourne null.
     */
    public String createNewFolder(File folderToCreate, int i)
    {
		try
        {
            if (folderToCreate.mkdir())
                return folderToCreate.getName();
            else
			{
				String name = folderToCreate.getAbsolutePath();

				if (i != 0)
					name = name.substring(0, name.lastIndexOf("_("));

				folderToCreate = new File(name + "_(" + (++i) + ")");
				return this.createNewFolder(folderToCreate, i);
			}
        }
        catch (Exception ex) { ex.printStackTrace(); System.out.println("Erreur lors de la création du dossier"); return null; }
    }

	/**
     * Permet de supprimer un fichier ou un dossier (ainsi que tout son contenu).
     * @param file le dossier (ou fichier) à supprimer
     */
    private void deleteFile(File file)
    {
        if (file.isDirectory())
            for (File f : file.listFiles())
                this.deleteFile(f);

        file.delete();
    }

    /**
     * Permet de coller un dossier et son contenue dans le dossier sélectionné
     * @param folderToPaste : le dossier à coller
     * @param folderDestination : le dossier de destination (le dossier de destination + le nom du dossier à coller)
     */
    private void pasteFolderRec(File folderToPaste, File folderDestination, int i)
    {
        try
        {
            if (folderDestination.mkdir())
                for (File f : folderToPaste.listFiles())
                    if (f.isDirectory())
                        this.pasteFolderRec(f, new File(folderDestination.getAbsolutePath() + File.separator + f.getName()), i);
                    else
						this.pasteFile(f, new File(folderDestination.getAbsolutePath() + File.separator + f.getName()));
            else
				this.pasteFolderRec(folderToPaste, new File(folderDestination.getAbsolutePath() + "_(" + (++i) + ")"), i);
        }
        catch (Exception ex) { ex.printStackTrace(); System.out.println("Erreur lors de la création du dossier"); }
    }

    /**
     * Permet de coller un fichier dans le dossier sélectionné
     * @param fileToPaste : le fichier à coller
     * @param fileDestination : le fichier de destination (dossier de destination + nom du fichier)
     */
    public void pasteFile(File fileToPaste, File fileDestination)
    {
        try
        {
            Files.copy(fileToPaste.toPath(), fileDestination.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
		catch (IOException e) { e.printStackTrace(); System.out.println("Erreur lors du collage du fichier"); }
    }

    /**
     * Permet de copier les fichiers dans un dossier
     * @param folderToCopy : le dossier à copier
     * @return la liste des fichiers copiés
     */
    public List<File> copyFilesInFolder(File folderToCopy)
    {
        List<File> lstFiles = new ArrayList<File>();

        if (folderToCopy.isDirectory())
            for (File f : folderToCopy.listFiles())
                lstFiles.addAll(this.copyFilesInFolder(f));

        lstFiles.add(folderToCopy);

        return lstFiles;
    }



	/*=====*/
	/* IHM */
	/*=====*/
	/**
	 * Permet de sauvegarder la taille ou la localisation de la frame pour la réouverture de l'application.
	 * Seul l'un des deux paramètres doit être différent de null.
	 * @param dim : la taille de la frame
	 */
	public void saveFrameInfo(Dimension dim, Point p)
	{
		String baliseX = "";
		String baliseY = "";
		String x = "";
		String y = "";

		do
		{
			if (dim == null)
			{
				if (p == null)
					return;
				else
				{
					baliseX = "frameX>";
					baliseY = "frameY>";
					x = "" + p.x;
					y = "" + p.y;
					p = null;
				}
			}
			else
			{
				baliseX = "frameWidth>";
				baliseY = "frameHeight>";
				x = "" + dim.width;
				y = "" + dim.height;
				dim = null;
			}

			try
			{
				File file = new File(Path.PATH_DEFAULT_VALUES);
				String line = "";
				String sRet = "";

				Scanner sc = new Scanner(file, "UTF8");
				while (sc.hasNextLine())
				{
					line = sc.nextLine();
					if (line.contains("<" + baliseX))
						sRet += "\t\t<" + baliseX + x  + "</" + baliseX + "\n";
					else
					{
						if (line.contains("<" + baliseY ))
							sRet += "\t\t<" + baliseY + y + "</" + baliseY + "\n";
						else
							sRet += line + "\n";
					}
				}
				sc.close();

				PrintWriter pw = new PrintWriter(file, "UTF8");
				pw.print(sRet);
				pw.close();
			}
			catch (Exception ex) { ex.printStackTrace(); }
		} while (dim != null || p != null);
	}

	/*------------*/
	/* Frame size */
	/*------------*/
	/**
	 * Permet de récupérer la taille de la frame pour la réouverture de l'application
	 * @return Dimension : la taille de la frame
	 */
	public Dimension getFrameSize()
	{
		try
		{
			SAXBuilder sxb = new SAXBuilder();
			Element frameSize = sxb.build(new File(Path.PATH_DEFAULT_VALUES)).getRootElement().getChild("frameSize");
			return new Dimension(Integer.parseInt(frameSize.getChild("frameWidth").getText()), Integer.parseInt(frameSize.getChild("frameHeight").getText()));
		}
		catch (Exception ex) { ex.printStackTrace(); }

		return new Dimension(800, 600);
	}

	/*----------------*/
	/* Frame location */
	/*----------------*/
	/**
	 * Permet de récupérer la position de la frame pour la réouverture de l'application
	 * @return Point : la position de la frame
	 */
	public Point getFrameLocation()
	{
		try
		{
			SAXBuilder sxb = new SAXBuilder();
			Element frameSize = sxb.build(new File(Path.PATH_DEFAULT_VALUES)).getRootElement().getChild("frameLocation");
			return new Point(Integer.parseInt(frameSize.getChild("frameX").getText()), Integer.parseInt(frameSize.getChild("frameY").getText()));
		}
		catch (Exception ex) { ex.printStackTrace(); }

		return new Point(0, 0);
	}

	/*---------*/
	/* onglets */
	/*---------*/
	/**
	 * Permet de sauvegarder un onglet
	 */
	public void saveOnglet(String name, String pathGauche, String pathDroite)
	{
		try
		{
			// TODO : sauvegarder l'onglet dans le fichier de valeur par défaut (pour la réouverture de l'application)
		}
		catch (Exception ex) { ex.printStackTrace(); }
	}



	/*========*/
	/* Thèmes */
	/*========*/
	/**
	 * Permet d'initialiser le nombre de thèmes perso créer par l'utilisateur.
	 * @return int : nombre de thèmes perso.
	 */
	private int initNbThemePerso()
	{
		int nb = 0;

		File dossier = new File(Path.PATH_THEMES);

		for (File fichier : dossier.listFiles())
			if (fichier.getName().startsWith("theme_perso_"))
				nb++;

		return nb;
	}

	/**
	 * Permet de récupérer le nombre de thèmes perso créer par l'utilisateur.
	 * @return int : nombre de thèmes perso.
	 */
	public int getNbThemesPerso() { return this.nbThemePerso; }

	/**
	 * Permet de modifier le nombre de thèmes perso créer par l'utilisateur.
	 */
	public void majNbThemesPerso() { this.nbThemePerso = initNbThemePerso(); }

	/**
	 * Permet de renommer le fichier avec le nom du thème et de changer le nom enregistrer dans le fichier de sauvegarde.
	 * @param nomFichier : nouveau nom du fichier.
	 */
	public void setNomFichier(String nomFichier)
	{
		File fileTheme = new File(Path.PATH_THEME_X + this.getThemeUsed() + ".xml");

		fileTheme.renameTo(new File(Path.PATH_THEME_X + nomFichier + ".xml"));

		try
		{
			PrintWriter pw = new PrintWriter(Path.PATH_THEME_SAVE);
			pw.println("<theme>" + nomFichier + "</theme>");
			pw.close();
		}
		catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de l'écriture du fichier XML du themes utilisé"); }
	}

	/**
	 * Permet de modifier un élément du thème dans le fichier xml.
	 * @param nameElement : nom de l'élément à modifier.
	 * @param color : nouvelle couleur de l'élément.
	 * @return boolean : true si l'élément a été modifié, false sinon.
	 */
	public boolean setElementTheme(String nameElement, Color color)
	{
		if (nameElement == null || color == null)        { return false; }
		if (!this.hmColorTheme.containsKey(nameElement)) { return false; }

		String sRet = "";

		try
		{
			Scanner sc = new Scanner(new File(Path.PATH_THEME_X + this.getThemeUsed() + ".xml"), "UTF8");

			String line = "";
			while(sc.hasNextLine())
			{
				line = sc.nextLine();
				if (line.contains(nameElement))
					line = "\t<" + nameElement + " red=\"" + color.getRed() + "\" green=\"" + color.getGreen() + "\" blue=\"" + color.getBlue() + "\" alpha=\"" + color.getAlpha()  + "\">" + "</" + nameElement + ">";

				sRet += line + "\n";
			}

			sc.close();

		} catch (FileNotFoundException e) { e.printStackTrace(); System.out.println("Erreur dans la méthode setElementTheme(), fichier '" + Path.PATH_THEME_X + this.getThemeUsed() + ".xml" + "' introuvable"); return false; }

		
		try
		{
			PrintWriter pw = new PrintWriter(new File(Path.PATH_THEME_X + this.getThemeUsed() + ".xml"), "UTF8");
			pw.print(sRet);
			pw.close();
		}
		catch (FileNotFoundException        e) { e.printStackTrace(); System.out.println("Erreur dans la méthode setElementTheme()"); return false; }
		catch (UnsupportedEncodingException e) { e.printStackTrace(); System.out.println("Erreur dans la méthode setElementTheme()"); return false; }

		return true;
	}

	/**
	 * Permet de changer le nom du thème en cours d'utilisation.
	 * @param nomTheme : nouveau nom du thème.
	 */
	public void setNomTheme(String nomTheme)
	{
		if (nomTheme != null)
		{
			String sRet = "";
			try
			{
				Scanner sc = new Scanner(new File(Path.PATH_THEME_X + this.getThemeUsed() + ".xml"), "UTF8");

				String line = "";
				while(sc.hasNextLine())
				{
					line = sc.nextLine();
					if (line.contains("name"))
						line = "<theme name=\"" + nomTheme + "\">";

					sRet += line + "\n";
				}

				sc.close();

			} catch (FileNotFoundException e) { e.printStackTrace(); System.out.println("Erreur dans la méthode setElementTheme(), fichier '" + Path.PATH_THEME_X + this.getThemeUsed() + ".xml" + "' introuvable"); }

			
			try
			{
				PrintWriter pw = new PrintWriter(new File(Path.PATH_THEME_X + this.getThemeUsed() + ".xml"), "UTF8");
				pw.print(sRet);
				pw.close();
			}
			catch (FileNotFoundException        e) { e.printStackTrace(); System.out.println("Erreur dans la méthode setNomTheme(String nomTheme)"); }
			catch (UnsupportedEncodingException e) { e.printStackTrace(); System.out.println("Erreur dans la méthode setNomTheme(String nomTheme)"); }
		}
	}

	/**
	 * Permet d'initialiser la liste des noms des thèmes perso créer par l'utilisateur.
	 * @return List : liste des noms des thèmes perso.
	 */
	private List<String> initLstNameThemesPerso()
	{
		List<String> lstNomThemesPerso = new ArrayList<String>();

		File dossier = new File(Path.PATH_THEMES);

		String name = "";
		for (File fichier : dossier.listFiles())
		{
			if (fichier.getName().startsWith("theme_perso_"))
			{
				SAXBuilder sxb = new SAXBuilder();
				try { name = sxb.build(fichier).getRootElement().getAttributeValue("name"); }
				catch (JDOMException e) { e.printStackTrace(); System.out.println("Erreur dans la lecture des noms des thèmes persos");  }
				catch (IOException   e) { e.printStackTrace(); System.out.println("Erreur dans la lecture des noms des thèmes persos"); }

				lstNomThemesPerso.add(name.replace("_", " "));
			}
		}

		return lstNomThemesPerso;
	}

	/**
	 * Permet de vérifier si le nom du thème n'est pas déjà utilisé et que le nom n'est pas null.
	 * @param nomTheme : nom du thème à vérifier.
	 * @return boolean : true si le nom du thème n'est pas déjà utilisé et qu'il n'est pas null, sinon false.
	 */
	public boolean verifNomTheme(String nomTheme)
	{
		if (nomTheme.equals("perso ")) return false;

		boolean nameOnlySpace = true;
		for (int i = 5; i < nomTheme.length(); i++)
			if (nomTheme.charAt(i) != ' ') { nameOnlySpace = false; break; }

		if (nameOnlySpace) return false;


		for (String s : this.lstNomThemesPerso)
			if (s.equals(nomTheme)) return false;

		return true;
	}

	/**
	 * Met à jour la liste des noms des thèmes perso. permet d'ajouter le nouveau thème qui viens d'être créer pour qui le liste sois à jour l'ors du prochaine appelle de la méthode verifNomTheme()
	 */
	public void majLstNomTheme()
	{
		this.lstNomThemesPerso = this.initLstNameThemesPerso();
	}


	/**
	 * Permet de récupérer la liste des noms des thèmes perso créer par l'utilisateur.
	 * @return List : liste des noms des thèmes perso.
	 */
	public List<String> getLstNameThemesPerso() { return this.lstNomThemesPerso; }


    /**
     * Permert de récupérer toute les couleurs de thème charger en mémoire.
     * @return HashMap - liste des couleurs du thème.
     */
    public HashMap<String, Color> getTheme() { return this.hmColorTheme;}


    /**
	 * Récupère le thème utilisé dans le fichier xml de sauvegarde
	 * @return String : thème à utilisé
	 */
	public String getThemeUsed()
	{
		String themeUsed = "";
		SAXBuilder sxb = new SAXBuilder();

		try
		{
			themeUsed = sxb.build(Path.PATH_THEME_SAVE).getRootElement().getText();
		}
		catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de la lecture du fichier XML du themes utilisé"); }

		return themeUsed;
	}


    /**
	 * Sauvegarde le thème selectionné par l'utilisateur dans le fichier xml de sauvegarde.
	 * Charge le thème selectionné dans la HashMap.
	 * Applique le thème selectionné (met à jour l'IHM).
	 * @param theme : thème à sauvegarder
	 */
	public void setThemeUsed(String theme)
	{
		if (!theme.equals(this.getThemeUsed()))
		{
			try
			{
				PrintWriter pw = new PrintWriter(Path.PATH_THEME_SAVE);
				pw.println("<theme>" + theme + "</theme>");
				pw.close();
			}
			catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de l'écriture du fichier XML du themes utilisé"); }

			this.chargerThemes(theme);

			this.ctrl.appliquerTheme();
		}
	}


    /**
	 * Charge les couleurs du thème choisi par l'utilisateur dans la HashMap
	 * @param theme : thème à charger
	 * @return HashMap contenant les couleurs du thème
	 */
	public void chargerThemes(String theme)
	{
		SAXBuilder sxb = new SAXBuilder();

		try
		{
			Element racine = sxb.build(Path.PATH_THEME_X + theme + ".xml").getRootElement();

			/*----------------------------------------------*/
			/* Récupération des couleurs de chaque éléments */
			/*----------------------------------------------*/
			for (Element e : racine.getChildren())
			{
				Color color = new Color( Integer.parseInt(e.getAttributeValue("red")), Integer.parseInt(e.getAttributeValue("green")), Integer.parseInt(e.getAttributeValue("blue")), Integer.parseInt(e.getAttributeValue("alpha")));

				this.hmColorTheme.put(e.getName(), color);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Erreur lors de la lecture du fichier XML des informations du theme");
		}
	}

	/**
	 * Permet de supprimer les fichiers xml des thèmes perso dont les noms sont dans la liste passé en paramètre.
	 * Met à jour le nombre de thèmes perso.
	 * Met à jour la liste des noms des thèmes perso.
	 * @param lstNomsThemes
	 */
	public void supprimerThemePerso(List<String> lstNomsThemes)
	{
		for (String theme : lstNomsThemes)
		{
			File f = new File(Path.PATH_THEME_X + theme + ".xml");
			f.delete();
		}

		this.majNbThemesPerso();
		this.majLstNomTheme();
	}

	/**
	 * Permet de récupérer la liste des clées de la HashMap des thèmes.
	 * @return List : liste des clées.
	 */
	public String[] getEnsClesThemes()
	{
		List<String> lstCles = new ArrayList<String>();

		try
		{
			SAXBuilder sxb = new SAXBuilder();
			for (Element e : sxb.build(Path.PATH_THEME_X + "clair.xml").getRootElement().getChildren())
				lstCles.add(e.getName());
		}
		catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de la lecture du fichier XML pour récupérer les clée de la HashMap des thème."); }

		return lstCles.toArray(new String[lstCles.size()]);
	}


	/*==========*/
	/* Langages */
	/*==========*/
	/**
     * Permert de récupérer toute les couleurs de thème charger en mémoire.
     * @return HashMap - liste des couleurs du thème.
     * 
     * object possible dans la hashmap : 
     * 
     * list.get(0) = couleur de fond.
     * list.get(1) = couleur du texte.
     * list.get(2) = couleur de hint / placeHolder (n'existe pas toujours).
     */
    public HashMap<String, HashMap<String, String>> getLangage() { return this.hmLangage;}

	/**
	 * Récupère le nom du langage utilisé dans le fichier xml de sauvegarde
	 * @return String : langage utilisé
	 */
	public String getLangageUsed()
	{
		String langageUsed = "";
		SAXBuilder sxb = new SAXBuilder();

		try
		{
			langageUsed = sxb.build(Path.PATH_LANGAGE_SAVE).getRootElement().getText();
		}
		catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de la lecture du fichier XML du langage utilisé"); }

		return langageUsed;
	}


    /**
	 * Sauvegarde le langage selectionné par l'utilisateur dans le fichier xml de sauvegarde.
	 * Charge le langage selectionné dans la HashMap.
	 * Applique le langage selectionné (met à jour l'IHM).
	 * @param theme : thème à sauvegarder
	 */
	public void setLangageUsed(String theme)
	{
		if (!theme.equals(this.getLangageUsed()))
		{
			try
			{
				PrintWriter pw = new PrintWriter(Path.PATH_LANGAGE_SAVE);
				pw.println("<langage>" + theme + "</langage>");
				pw.close();
			}
			catch (Exception e) { e.printStackTrace(); System.out.println("Erreur lors de l'écriture du fichier XML du langage utilisé"); }

			this.chargerLangage(theme);

			this.ctrl.appliquerLangage();
		}
	}

	/**
	 * Charge le texte du langage choisi par l'utilisateur dans la HashMap
	 * @param langage : langage à charger
	 * @return HashMap contenant le texte du langage
	 */
	public void chargerLangage(String langage)
	{
		SAXBuilder sxb = new SAXBuilder();
		try
		{
			Element racine = sxb.build(Path.PATH_LANGAGE_X + langage + ".xml").getRootElement();

			/*----------------------------------------------*/
			/* Récupération des couleurs de chaque éléments */
			/*----------------------------------------------*/
			for (Element e : racine.getChildren())
			{
				HashMap<String, String> hmTemp = new HashMap<String, String>();
				for (Element eEnfant : e.getChildren())
					hmTemp.put(eEnfant.getName(), eEnfant.getText());

				this.hmLangage.put(e.getName(), hmTemp);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Erreur lors de la lecture du fichier XML des informations du theme");
		}
	}
}
