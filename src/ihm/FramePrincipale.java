package ihm;

import javax.swing.JFrame;

import controleur.Controleur;


public class FramePrincipale extends JFrame
{
    private Controleur ctrl;


    /**
     * Constructeur de la frame principale
     * @param ctrl : Controleur qui fait le lien avec le metier
     */
    public FramePrincipale(Controleur ctrl)
    {
        this.ctrl = ctrl;

        this.setTitle("Frame principale");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        
        this.setVisible(true);
    }


    /**
     * Permet d'appliquer le thème à chaque élément de l'ihm qui en à besoins
     */
    public void appliquerTheme()
    {
        System.out.println("Theme appliqué");

        System.out.println(this.ctrl.getTheme().get("background"  ).get(0));
        System.out.println(this.ctrl.getTheme().get("foreground"  ).get(0));
        System.out.println(this.ctrl.getTheme().get("disableColor").get(0));
        System.out.println(this.ctrl.getTheme().get("enableColor" ).get(0));
        System.out.println(this.ctrl.getTheme().get("titles"      ).get(0));
        System.out.println(this.ctrl.getTheme().get("saisies"     ).get(0));
        System.out.println(this.ctrl.getTheme().get("saisies"     ).get(1));
        System.out.println(this.ctrl.getTheme().get("buttons"     ).get(0));

    }
}
