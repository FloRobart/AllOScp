package ihm.resultat;

import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class FrameOption implements ActionListener
{
    private static final int TYPE_INFORMATION = 0;
    private static final int TYPE_QUESTION    = 1;
    private static final int TYPE_ERREUR      = 2;
    private static final int TYPE_INPUT       = 3;

    private JFrame frame;

    private JScrollPane scrollPane;
    private JLabel lblMessage;
    private JButton btnOk;
    private JButton btnSave;


    private FrameOption() {}

    private static void generateFrame(String message, String titre, int typeFrame)
    {
        FrameOption frameOption = new FrameOption();

        switch (typeFrame)
        {
            case FrameOption.TYPE_INFORMATION : { frameOption.FrameInformation(message, titre); break; }
            case FrameOption.TYPE_QUESTION    : { frameOption.FrameQuestion   (message, titre); break; }
            case FrameOption.TYPE_ERREUR      : { frameOption.FrameErreur     (message, titre); break; }
            case FrameOption.TYPE_INPUT       : { frameOption.FrameInput      (message, titre); break; }
            default : break;
        }
    }

    private void FrameInformation(String message, String titre)
    {
        this.frame = new JFrame(titre);
        
        Dimension dimScreen = this.frame.getToolkit().getScreenSize();

        this.frame.setTitle(titre);
        this.frame.setLocation(dimScreen.width / 2, dimScreen.height / 2);
        this.frame.setSize(dimScreen.width / 2, dimScreen.height / 3);
        this.frame.setLayout(new BorderLayout());
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        /* Création des composants */
        this.lblMessage = new JLabel(message);
        this.scrollPane = new JScrollPane(this.lblMessage, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.btnOk = new JButton("Ok");


        /* Ajout des composants */
        this.frame.add(this.scrollPane, BorderLayout.CENTER);
        this.frame.add(this.btnOk, BorderLayout.SOUTH);


        /* Activation des composants */
        this.btnOk.addActionListener(this);



        this.frame.setVisible(true);
    }


    private void FrameErreur(String message, String titre)
    {
        this.frame = new JFrame(titre);
        
        Dimension dimScreen = this.frame.getToolkit().getScreenSize();

        this.frame.setTitle(titre);
        this.frame.setLocation(dimScreen.width / 2, dimScreen.height / 2);
        this.frame.setLayout(new BorderLayout());
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        /* Création des composants */
        this.lblMessage = new JLabel(message);
        this.scrollPane = new JScrollPane(this.lblMessage, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.btnOk = new JButton("Ok");


        /* Ajout des composants */
        this.frame.add(this.scrollPane, BorderLayout.CENTER);
        this.frame.add(this.btnOk, BorderLayout.SOUTH);


        /* Activation des composants */
        this.btnOk.addActionListener(this);


        this.frame.pack();
        this.frame.setVisible(true);
    }

    private void FrameQuestion(String message, String titre)
    {
        
    }

    private void FrameInput(String message, String titre)
    {
        
    }

    public static void showResultatComparaison(String message, String titre)
    {
        FrameOption.generateFrame(message, titre, FrameOption.TYPE_INFORMATION);
    }





    @Override
    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource() == this.btnOk)
        {
            this.frame.dispose();
        }

        if (ae.getSource() == this.btnSave)
        {
            File defaultFolder = new File(System.getProperties().getProperty("user.home") + "\\Downloads\\ResultatComparaison");
            defaultFolder.mkdirs();

            try
            {
                PrintWriter pw = new PrintWriter(new FileWriter(defaultFolder.getPath() + "\\" + defaultFolder.listFiles().length + ".txt"));
                pw.write(this.lblMessage.getText());
                pw.close();
            }
            catch (IOException e) { e.printStackTrace(); System.out.println("Erreur lors de la création du fichier du résultat de la comparaison"); }
        }
    }


    public void appliquerTheme()
    {
        // TODO : Appliquer le thème
    }

    public void appliquerLangue()
    {
        // TODO : Appliquer la langue
    }
}
