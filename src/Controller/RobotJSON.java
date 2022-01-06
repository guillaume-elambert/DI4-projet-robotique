package Controller;

import Model.Robot;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe pour gérer le passage de Robot => JSON ou JSON => Robot.
 */
public class RobotJSON {

    /**
     * Méthode qui convertit un @ref Model.Robot "robot" en JSON et l'écrit dans un fichier.
     *
     * @param pathToJSONOutputFile Le chemin vers le fichier (avec ou sans l'extension).
     * @param robot                L'objet @ref Model.Robot "robot" à convertir en JSON.
     * @return true si réussi, false sinon.
     */
    public static boolean writeRobotToJSONFile(String pathToJSONOutputFile, Robot robot) {
        String json = new Gson().toJson(robot);
        FileWriter fw;

        if (!pathToJSONOutputFile.matches(".*\\.json")) pathToJSONOutputFile += ".json";
        if (json == null || json.isEmpty()) return false;

        try {
            fw = new FileWriter(pathToJSONOutputFile);
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    /**
     * Méthode qui retourne un @ref Model.Robot "robot" contenu au format JSON dans un fichier.
     * <br/>
     * Le Fichier doit être formé comme suit :
     * <pre>
     * {
     *     "base": {
     *         "x": 0.0,
     *         "y": 0.0,
     *         "z": 0.0
     *     },
     *     "articulations": [
     *         {
     *             "denavit": {
     *                 "theta": 8.031161252724507,
     *                 "d": 0.0,
     *                 "alpha": 0.0,
     *                 "a": 1.0
     *             },
     *             "type": "ROTATION" OU "TRANSLATION"
     *         }, {
     *                 .    .    .
     *                 .    .    .
     *                 .    .    .
     *         }
     *     ]
     * }
     * </pre>
     *
     * @param pathToJSONFile Le chemin vers un fichier.
     * @return L'objet @ref Model.Robot "robot".
     */
    public static Robot parseRobotFromFile(String pathToJSONFile) {
        FileReader fr;
        try {
            fr = new FileReader(pathToJSONFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return new Gson().fromJson(fr, Robot.class);
    }


    /**
     * Méthode qui retourne un @ref Model.Robot "robot" contenu au format JSON dans une chaîne de caractères.
     *
     * @param robotJSONString La chaîne de caractères contenant le @ref Model.Robot "robot" en JSON.
     * @return L'objet @ref Model.Robot "robot"
     */
    public static Robot parseRobotFromString(String robotJSONString) {
        FileReader fr;
        return new Gson().fromJson(robotJSONString, Robot.class);
    }


    /**
     * Méthode qui convertit un object @ref Model.Robot "robot" en JSON.
     *
     * @param robot L'objet @ref Model.Robot "robot" à convertir en JSON.
     * @return La chaîne de caractères contenant le @ref Model.Robot "robot" en JSON.
     */
    public static String robotToJSON(Robot robot) {
        return new Gson().toJson(robot);
    }
}
