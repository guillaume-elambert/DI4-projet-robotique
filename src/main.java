import static View.AffichageSimple.clearScreen;
import static View.Menu.menuPrincipal;
import static View.SaisiesClavier.saisirString;

/**
 * Classe principale.
 */
public class main {

    /**
     * Fonction principale.
     * @param args Les arguments.
     */
    public static void main(String[] args) {
        String saisie;
        do {
            menuPrincipal();
            saisie = saisirString("Continuer ? (Y)es/(N)o ");
            clearScreen();

        } while(saisie.toUpperCase().charAt(0) == 'Y');
    }
}
