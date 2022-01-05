package View;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Classe pour l'affichage de messages simples.
 */
public class AffichageSimple {

    /**
     * L'objet où écrire les messages.
     */
    private static final PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    /**
     * L'objet où écrire les messages d'erreur.
     */
    private static final PrintStream err = new PrintStream(System.err, true, StandardCharsets.UTF_8);


    /**
     * Méthode pour afficher un message d'erreur.
     *
     * @param message Le message à afficher (respectant le format de java.lang.System.err.format()).
     */
    public static void afficherErreur(String message) {
        afficherErreur(message, 1);
    }


    /**
     * Méthode pour afficher un message d'erreur.
     *
     * @param message    Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param lineBreaks Le nombre de lignes à sauter après l'affichage.
     */
    public static void afficherErreur(String message, int lineBreaks) {
        afficherErreur(message, new Object[0], lineBreaks);
    }


    /**
     * Méthode pour afficher un message d'erreur.
     *
     * @param message Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param obj     Les objets à afficher dans le message.
     */
    public static void afficherErreur(String message, Object[] obj) {
        afficherErreur(message, obj, 1);
    }


    /**
     * Méthode pour afficher un message d'erreur.
     *
     * @param message    Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param obj        Les objets à afficher dans le message.
     * @param lineBreaks Le nombre de lignes à sauter après l'affichage.
     */
    public static void afficherErreur(String message, Object[] obj, int lineBreaks) {

        if (lineBreaks > 0) {
            message += new String(new char[lineBreaks]).replaceAll("\0", "%n");
        }

        err.format(message, obj);
    }


    /**
     * Méthode pour afficher un message.
     *
     * @param message Le message à afficher (respectant le format de java.lang.System.err.format()).
     */
    public static void afficherMessage(String message) {
        afficherMessage(message, 1);
    }


    /**
     * Méthode pour afficher un message.
     *
     * @param message    Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param lineBreaks Le nombre de lignes à sauter après l'affichage.
     */
    public static void afficherMessage(String message, int lineBreaks) {
        afficherMessage(message, new Object[0], lineBreaks);
    }


    /**
     * Méthode pour afficher un message.
     *
     * @param message Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param obj     Les objets à afficher dans le message.
     */
    public static void afficherMessage(String message, Object[] obj) {
        afficherMessage(message, obj, 1);
    }


    /**
     * Méthode pour afficher un message.
     *
     * @param message    Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param obj        Les objets à afficher dans le message.
     * @param lineBreaks Le nombre de lignes à sauter après l'affichage.
     */
    public static void afficherMessage(String message, Object[] obj, int lineBreaks) {

        if (lineBreaks > 0) {
            message += new String(new char[lineBreaks]).replaceAll("\0", "%n");
        }

        out.format(message, obj);
    }


    /**
     * Méthode pour effacer le contenu de la console.
     */
    public static void clearScreen() {
        System.out.println("\n\n");
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            out.print("\033[H\033[2J");
            out.flush();
        }
    }
}
