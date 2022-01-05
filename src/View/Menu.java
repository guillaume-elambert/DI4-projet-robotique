package View;

import Controller.Cinematique;
import Controller.RobotJSON;
import Model.Articulation;
import Model.Position;
import Model.Robot;

import java.io.File;
import java.util.ArrayList;

import static Controller.RobotJSON.writeRobotToJSONFile;
import static View.AffichageSimple.afficherErreur;
import static View.AffichageSimple.afficherMessage;
import static View.SaisiesClavier.*;

/**
 * Classe pour afficher les menus.
 */
public class Menu {

    /** Liste des actions possibles dans le menu principal. */
    public static final String[] ACTIONS_POSSIBLES = new String[]{
            "Utiliser un robot existant",
            "Utiliser votre propre robot (θ et α en degrés)"
    };

    /** Nombre d'actions possibles dans le menu principal. */
    public static final int NB_ACTIONS_POSSIBLES = ACTIONS_POSSIBLES.length;

    /** Le chemin où sont stockés les fichiers JSON par défaut. */
    public static final String ROBOTS_DIRECTORY = "./JSONRobots";


    /**
     * Méthode qui gère l'affichage et les fonctionnalités du menu principal.
     */
    public static void menuPrincipal() {

        String message = "Actions possibles :\n", pathToJSON, saisie;
        int choix, i = 0, nbArticulations;

        Robot robot = null;
        ArrayList<Articulation> articulations;
        Cinematique cinematique;
        Position objectif;
        String json, toPrint;
        double x, y, z;
        double[] resultat;


        for (String msgAction : ACTIONS_POSSIBLES) {
            message += (++i) + ") " + msgAction + "\n";
        }

        afficherMessage(message);
        choix = saisirInt("Saisir votre choix : ", 0, 1, NB_ACTIONS_POSSIBLES);
        afficherMessage("");

        switch (choix) {

            case 1:
                pathToJSON = menuChoixFichiersParDefaut();
                break;

            case 2:
                pathToJSON = saisirString("Saisir le chemin vers le fichier JSON de votre robot :\n");
                afficherMessage("");
                break;
            default:
                return;
        }

        robot = RobotJSON.parseRobotFromFile(pathToJSON);

        if (robot != null) {
            afficherMessage("Saisir la position objectif");
            x = saisirDouble("x : ", 0);
            y = saisirDouble("y : ", 0);
            z = saisirDouble("z : ", 0);

            cinematique = new Cinematique(robot);
            resultat = cinematique.resoudreVariablesArticulaires(new Position(x, y, z), 500);
            articulations = robot.getArticulations();
            nbArticulations = articulations.size();

            toPrint = "\nRésulat obtenu :";
            for (i = 0; i < nbArticulations; ++i) {
                toPrint += switch (articulations.get(i).getType()) {
                    case ROTATION -> "θ";
                    case TRANSLATION -> "d";
                } + (i + 1) + ":" + resultat[i] + "\t";
            }
            afficherMessage(toPrint);

            saisie = saisirString("Sauvegarder un robot avec les nouvelles variables ? (Y)es/(N)o : ");

            //On arrête cette itération si l'utilisateur ne saisie pas "Y"
            if (saisie.toUpperCase().charAt(0) != 'Y') return;

            //On met à jour les articulations
            robot.setVariablesArticulaires(resultat);


            pathToJSON = saisirString("Saisir le chemin vers le fichier où sauvegarder votre robot au format JSON :\n");
            afficherMessage("");

            writeRobotToJSONFile(pathToJSON, robot);

        }
    }


    /**
     * Méthode pour afficher le menu de choix d'un fichier par défaut.
     *
     * @return Le chemin vers le fichier choisit.
     */
    public static String menuChoixFichiersParDefaut(){
        String toPrint;
        int i = 0, choix;
        File robotsDirectory = new File(ROBOTS_DIRECTORY);
        File[] filesInDirectory = robotsDirectory.listFiles();

        if (filesInDirectory == null || filesInDirectory.length == 0) {
            afficherErreur("Le dossier est vide.");
            return null;
        }

        toPrint = "Liste des fichiers disponibles :\n";
        for (File file : filesInDirectory) {
            toPrint += ++i + ") " + file.getName() + "\n";
        }
        afficherMessage(toPrint);

        choix = saisirInt("Saisir votre choix : ", 0, 1, i);
        afficherMessage("");
        --choix;

        return filesInDirectory[choix].getPath();
    }
}
