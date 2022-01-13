package Controller;

import Model.Articulation;
import Model.ParametresDenavit;
import Model.Position;
import Model.Robot;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RobotJSONTest {

    static int nbTests = 2;
    static Position base = new Position(0, 0, 0);
    static ArrayList<Articulation>[] articulations = new ArrayList[nbTests];
    static Robot[] robots = new Robot[nbTests];

    static String dossierFichiers = "./JSONRobots/";
    static String[] cheminsFichiers = {
            dossierFichiers+"Robot1.json",
            dossierFichiers+"Robot2.json"
    };

    static String[] attendus = new String[]{
            "{\"base\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"articulations\":[{\"denavit\":{\"theta\":45.0,\"d\":0.0,\"alpha\":0.0,\"a\":1.0},\"type\":\"ROTATION\"},{\"denavit\":{\"theta\":90.0,\"d\":0.0,\"alpha\":0.0,\"a\":1.0},\"type\":\"ROTATION\"}]}",
            "{\"base\":{\"x\":0.0,\"y\":0.0,\"z\":0.0},\"articulations\":[{\"denavit\":{\"theta\":0.0,\"d\":1.0,\"alpha\":0.0,\"a\":0.0},\"type\":\"ROTATION\"},{\"denavit\":{\"theta\":135.0,\"d\":0.0,\"alpha\":90.0,\"a\":-0.2},\"type\":\"ROTATION\"},{\"denavit\":{\"theta\":0.0,\"d\":1.5,\"alpha\":90.0,\"a\":0.0},\"type\":\"TRANSLATION\"}]}"
    };

    static {

        //1ère architecture de robot à tester
        articulations[0] = new ArrayList<>();
        articulations[0].add(new Articulation(
                new ParametresDenavit(45, 0, 0, 1),
                Articulation.Type.ROTATION
        ));

        articulations[0].add(new Articulation(
                new ParametresDenavit(90, 0, 0, 1),
                Articulation.Type.ROTATION
        ));

        robots[0] = new Robot(base, articulations[0]);



        //2ème architecture de robot à tester
        articulations[1] = new ArrayList<>();
        articulations[1].add(new Articulation(
                new ParametresDenavit(0, 1, 0, 0),
                Articulation.Type.ROTATION
        ));

        articulations[1].add(new Articulation(
                new ParametresDenavit(135, 0, 90, -0.2),
                Articulation.Type.ROTATION
        ));

        articulations[1].add(new Articulation(
                new ParametresDenavit(0, 1.5, 90, 0),
                Articulation.Type.TRANSLATION
        ));

        robots[1] = new Robot(base, articulations[1]);
    }

    @Test
    void writeRobotToJSONFile() {
        for(int i = 0; i < nbTests; ++i){
            assertTrue(RobotJSON.writeRobotToJSONFile(cheminsFichiers[i], robots[i]));
        }
    }

    @Test
    void parseRobotFromFile() {
        for(int i = 0; i < nbTests; ++i){
            assertEquals(RobotJSON.parseRobotFromFile(cheminsFichiers[i]), robots[i]);
        }
    }

    @Test
    void parseRobotFromString() {
        for(int i = 0; i < nbTests; ++i){
            assertEquals(RobotJSON.parseRobotFromString(attendus[i]), robots[i]);
        }
    }

    @Test
    void robotToJSON() {
        for(int i = 0; i < nbTests; ++i){
            assertEquals(RobotJSON.robotToJSON(robots[i]), attendus[i]);
        }
    }
}