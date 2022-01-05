package View;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;

import static View.AffichageSimple.afficherErreur;
import static View.AffichageSimple.afficherMessage;

/**
 * Classe pour les saisies clavier.
 */
public class SaisiesClavier {

    /**
     * L'objet pour lire les saisies clavier.
     */
    private static final Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);

    /**
     * Les erreurs.
     */
    private static final HashMap<String, String> ERRORS = new HashMap<>() {
        {
            put("INT", "Un problème est survenu lors de la saisie de l'entier.");
            put("STRING", "Un problème est survenu lors de la saisie de la chaîne de caractère.");
            put("DOUBLE", "Un problème est survenu lors de la saisie du double.");
        }
    };

    /**
     * Les messages.
     */
    private static final HashMap<String, String> MESSAGES = new HashMap<>() {
        {
            put("INT_RANGE", "Veuillez saisir un chiffre entre %d et %d : ");
            put("DOUBLE_RANGE", "Veuillez saisir un chiffre entre %f et %f : ");
        }
    };


    /* ***************************************** *
     *              SAISIE D'ENTIER              *
     * ***************************************** */

    /**
     * Méthode pour saisir un entier.
     *
     * @return L'entier saisi.
     */
    public static int saisirInt() {
        return saisirInt("");
    }


    /**
     * Méthode pour saisir un entier avec l'affichage d'un message.
     *
     * @param message Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @return L'entier saisi.
     */
    public static int saisirInt(String message) {
        return saisirInt(message, 1);
    }


    /**
     * Méthode pour saisir un entier avec l'affichage d'un message.
     *
     * @param message          Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param lineBreaksBefore Le nombre de lignes à sauter avec l'affichage.
     * @return L'entier saisi.
     */
    public static int saisirInt(String message, int lineBreaksBefore) {
        return saisirInt(message, lineBreaksBefore, new Object[0]);
    }


    /**
     * Méthode pour saisir un entier compris entre 2 nombres et afficher un message.
     *
     * @param message  Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param rangeMin La borne inférieure.
     * @param rangeMax La borne supérieure.
     * @return L'entier saisi.
     */
    public static int saisirInt(String message, int rangeMin, int rangeMax) {
        return saisirInt(message, 1, new Object[0], rangeMin, rangeMax);
    }


    /**
     * Méthode pour saisir un entier compris entre 2 nombres et afficher un message.
     *
     * @param message          Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param lineBreaksBefore Le nombre de lignes à sauter avec l'affichage.
     * @param rangeMin         La borne inférieure.
     * @param rangeMax         La borne supérieure.
     * @return L'entier saisi.
     */
    public static int saisirInt(String message, int lineBreaksBefore, int rangeMin, int rangeMax) {
        return saisirInt(message, lineBreaksBefore, new Object[0], rangeMin, rangeMax);
    }


    /**
     * Méthode pour saisir un entier compris entre 2 nombres et afficher un message.
     *
     * @param message          Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param lineBreaksBefore Le nombre de lignes à sauter avec l'affichage.
     * @param obj              Les objets à afficher dans le message.
     * @param rangeMin         La borne inférieure.
     * @param rangeMax         La borne supérieure.
     * @return L'entier saisi.
     */
    public static int saisirInt(String message, int lineBreaksBefore, Object[] obj, int rangeMin, int rangeMax) {
        int toReturn = saisirInt(message, lineBreaksBefore, obj);

        while (toReturn < rangeMin || toReturn > rangeMax) {
            toReturn = saisirInt(MESSAGES.get("INT_RANGE"), lineBreaksBefore, new Object[]{rangeMin, rangeMax});
        }

        return toReturn;
    }

    /**
     * Méthode pour saisir un entier et afficher un message.
     *
     * @param message          Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param lineBreaksBefore Le nombre de lignes à sauter avec l'affichage.
     * @param obj              Les objets à afficher dans le message.
     * @return L'entier saisi.
     */
    public static int saisirInt(String message, int lineBreaksBefore, Object[] obj) {
        int toReturn = -1;

        if (lineBreaksBefore > 0) {
            message = new String(new char[lineBreaksBefore]).replaceAll("\0", "%n") + message;
        }

        afficherMessage(message, obj, 0);

        if (sc.hasNextInt()) {
            toReturn = sc.nextInt();

        } else {
            afficherErreur(ERRORS.get("INT"));
        }

        return toReturn;
    }


    /* **************************************** *
     *      SAISIE DE CHAÎNE DE CARACTÈRES      *
     * **************************************** */

    /**
     * Méthode pour saisir une chaîne de caractères.
     *
     * @return La chaîne de caractères saisie.
     */
    public static String saisirString() {
        return saisirString("");
    }


    /**
     * Méthode pour saisir une chaîne de caractères et afficher un message.
     *
     * @param message Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @return La chaîne de caractères saisie.
     */
    public static String saisirString(String message) {
        return saisirString(message, 1);
    }


    /**
     * Méthode pour saisir une chaîne de caractères et afficher un message.
     *
     * @param message          Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param lineBreaksBefore Le nombre de lignes à sauter avec l'affichage.
     * @return La chaîne de caractères saisie.
     */
    public static String saisirString(String message, int lineBreaksBefore) {
        return saisirString(message, ".*", lineBreaksBefore);
    }


    /**
     * Méthode pour saisir une chaîne de caractères qui respecte une certaine expression régulière et afficher un message.
     *
     * @param message Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param pattern L'expression régulière que doit respecter la chaîne saisie.
     * @return La chaîne de caractères saisie.
     */
    public static String saisirString(String message, String pattern) {
        return saisirString(message, pattern, 1);
    }


    /**
     * Méthode pour saisir une chaîne de caractères qui respecte une certaine expression régulière et afficher un message.
     *
     * @param message Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param obj     Les objets à afficher dans le message.
     * @return La chaîne de caractères saisie.
     */
    public static String saisirString(String message, Object[] obj) {
        return saisirString(message, ".*", 1, obj);
    }


    /**
     * Méthode pour saisir une chaîne de caractères qui respecte une certaine expression régulière et afficher un message.
     *
     * @param message          Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param pattern          L'expression régulière que doit respecter la chaîne saisie.
     * @param lineBreaksBefore Le nombre de lignes à sauter avant d'afficher le message.
     * @return La chaîne de caractères saisie.
     */
    public static String saisirString(String message, String pattern, int lineBreaksBefore) {
        return saisirString(message, pattern, lineBreaksBefore, new Object[0]);
    }


    /**
     * Méthode pour saisir une chaîne de caractères qui respecte une certaine expression régulière et afficher un message.
     *
     * @param message          Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param pattern          L'expression régulière que doit respecter la chaîne saisie.
     * @param lineBreaksBefore Le nombre de lignes à sauter avant d'afficher le message.
     * @param obj              Les objets à afficher dans le message.
     * @return La chaîne de caractères saisie.
     */
    public static String saisirString(String message, String pattern, int lineBreaksBefore, Object[] obj) {

        String toReturn = null;

        if (lineBreaksBefore > 0) {
            message = new String(new char[lineBreaksBefore]).replaceAll("\0", "%n") + message;
        }

        while (toReturn == null || !toReturn.matches(pattern)) {
            afficherMessage(message, obj, 0);

            if (sc.hasNext()) {
                toReturn = sc.next();
            } else {
                afficherErreur(ERRORS.get("STRING"));
                return "";
            }
        }

        return toReturn;
    }


    /* ***************************************** *
     *              SAISIE DE DOUBLE             *
     * ***************************************** */

    /**
     * Méthode pour saisir un double.
     *
     * @return L'entier saisi.
     */
    public static double saisirDouble() {
        return saisirDouble("");
    }


    /**
     * Méthode pour saisir un double avec l'affichage d'un message.
     *
     * @param message Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @return L'entier saisi.
     */
    public static double saisirDouble(String message) {
        return saisirDouble(message, 1);
    }


    /**
     * Méthode pour saisir un double avec l'affichage d'un message.
     *
     * @param message          Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param lineBreaksBefore Le nombre de lignes à sauter avec l'affichage.
     * @return L'entier saisi.
     */
    public static double saisirDouble(String message, int lineBreaksBefore) {
        return saisirDouble(message, lineBreaksBefore, new Object[0]);
    }


    /**
     * Méthode pour saisir un double compris entre 2 nombres et afficher un message.
     *
     * @param message  Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param rangeMin La borne inférieure.
     * @param rangeMax La borne supérieure.
     * @return L'entier saisi.
     */
    public static double saisirDouble(String message, double rangeMin, double rangeMax) {
        return saisirDouble(message, 1, new Object[0], rangeMin, rangeMax);
    }


    /**
     * Méthode pour saisir un double compris entre 2 nombres et afficher un message.
     *
     * @param message          Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param lineBreaksBefore Le nombre de lignes à sauter avec l'affichage.
     * @param rangeMin         La borne inférieure.
     * @param rangeMax         La borne supérieure.
     * @return L'entier saisi.
     */
    public static double saisirDouble(String message, int lineBreaksBefore, double rangeMin, double rangeMax) {
        return saisirDouble(message, lineBreaksBefore, new Object[0], rangeMin, rangeMax);
    }


    /**
     * Méthode pour saisir un double compris entre 2 nombres et afficher un message.
     *
     * @param message          Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param lineBreaksBefore Le nombre de lignes à sauter avec l'affichage.
     * @param obj              Les objets à afficher dans le message.
     * @param rangeMin         La borne inférieure.
     * @param rangeMax         La borne supérieure.
     * @return L'entier saisi.
     */
    public static double saisirDouble(String message, int lineBreaksBefore, Object[] obj, double rangeMin, double rangeMax) {
        double toReturn = saisirDouble(message, lineBreaksBefore, obj);

        while (toReturn < rangeMin || toReturn > rangeMax) {
            toReturn = saisirDouble(MESSAGES.get("DOUBLE_RANGE"), lineBreaksBefore, new Object[]{rangeMin, rangeMax});
        }

        return toReturn;
    }

    /**
     * Méthode pour saisir un double et afficher un message.
     *
     * @param message          Le message à afficher (respectant le format de java.lang.System.err.format()).
     * @param lineBreaksBefore Le nombre de lignes à sauter avec l'affichage.
     * @param obj              Les objets à afficher dans le message.
     * @return L'entier saisi.
     */
    public static double saisirDouble(String message, int lineBreaksBefore, Object[] obj) {
        double toReturn = -1.0;

        if (lineBreaksBefore > 0) {
            message = new String(new char[lineBreaksBefore]).replaceAll("\0", "%n") + message;
        }

        afficherMessage(message, obj, 0);

        if (sc.hasNextDouble()) {
            toReturn = sc.nextDouble();

        } else {
            afficherErreur(ERRORS.get("DOUBLE"));
        }

        return toReturn;
    }
}
