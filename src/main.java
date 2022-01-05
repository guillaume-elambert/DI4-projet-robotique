import Jama.Matrix;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class main {

    public static final String ROBOTS_DIRECTORY = "./JSONRobots";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Robot robot = null;
        ArrayList<Articulation> articulations;
        Position objectif;
        String json;
        FileWriter fw;

        int choix, nbArticulations, i;
        double x, y, z;
        String pathToJSON = null;
        double[] resultat;

        do {
            System.out.println("Actions possibles :\n" +
                    "1) Utiliser un robot existant\n" +
                    "2) Utiliser votre propre robot (θ et α en radians)\n");

            do {
                System.out.print("Saisir votre choix : ");
                choix = sc.nextInt();
                System.out.println();
            } while(choix <= 0 || choix > 2);

            switch (choix){

                case 1:
                    System.out.println("\n");
                    File robotsDirectory = new File(ROBOTS_DIRECTORY);
                    File[] filesInDirectory = robotsDirectory.listFiles();

                    if(filesInDirectory.length == 0){
                        System.out.println("Le dossier est vide.");
                        break;
                    }

                    System.out.println("Liste des fichiers disponibles :");
                    i = 0;
                    for(File file : filesInDirectory){
                        System.out.println(++i+") "+file.getName());
                    }
                    System.out.println();

                    do {
                        System.out.print("Saisir votre choix : ");
                        choix = sc.nextInt();
                        System.out.println();
                    } while (choix <= 0 || choix > i);

                    pathToJSON = filesInDirectory[choix-1].getPath();
                    break;

                case 2:
                    System.out.println("Saisir le chemin vers le fichier JSON de votre robot : ");
                    pathToJSON = sc.next();
                    break;
            }

            robot = RobotParser.parseRobot(pathToJSON);

            if(robot != null){
                System.out.print("Saisir la position objectif\nx : ");
                x = sc.nextDouble();

                System.out.print("y : ");
                y = sc.nextDouble();

                System.out.print("z : ");
                z = sc.nextDouble();

                resultat = robot.resoudreVariablesArticulaires(new Position(x, y, z), 500);
                articulations = robot.getArticulations();
                nbArticulations = articulations.size();

                System.out.println("\nRésulat obtenu :");
                for(i = 0; i < nbArticulations; ++i){
                    System.out.print(switch (articulations.get(i).getType()) {
                        case ROTATION -> "θ";
                        case TRANSLATION -> "d";
                    }+(i+1)+":"+resultat[i]+"\t");
                }

                System.out.println("\n\nSauvegarder un robot avec les nouvelles variables ? (Y)es/(N)o : ");

                //On arrête cette itération si l'utilisateur ne saisie pas "Y"
                if(sc.next().toUpperCase().charAt(0) != 'Y' ) continue;

                //On met à jour les articulations
                robot.setVariablesArticulaires(resultat);

                json = new Gson().toJson(robot);

                System.out.println("\nSaisir le chemin vers le fichier où sauvegarder votre robot au format JSON (sans l'extension) : ");
                pathToJSON = sc.next();
                if(!pathToJSON.matches(".*\\.json")) pathToJSON += ".json";

                try {
                    fw = new FileWriter(pathToJSON);
                    fw.write(json);
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }

            System.out.println("\n\n");
        } while(true);
    }
}
