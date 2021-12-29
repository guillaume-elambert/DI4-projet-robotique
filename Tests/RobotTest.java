import Jama.Matrix;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RobotTest {

    static int nbTests = 2;
    static Position base = new Position(0, 0, 0);
    static ArrayList<Articulation>[] articulations = new ArrayList[nbTests];
    static Robot[] robots = new Robot[nbTests];

    static ArrayList<double[][]> attendus = new ArrayList<>();

    static {

        //1ère architecture de robot à tester
        articulations[0] = new ArrayList<>();
        articulations[0].add(new Articulation(
                new ParametresDenavit(45, 0, 0, 1),
                new Position(),
                Articulation.Type.ROTATION
        ));

        articulations[0].add(new Articulation(
                new ParametresDenavit(90, 0, 0, 1),
                new Position(),
                Articulation.Type.ROTATION
        ));

        robots[0] = new Robot(base, articulations[0]);




        //2ème architecture de robot à tester
        articulations[1] = new ArrayList<>();
        articulations[1].add(new Articulation(
                new ParametresDenavit(0, 1, 0, 0),
                new Position(),
                Articulation.Type.ROTATION
        ));

        articulations[1].add(new Articulation(
                new ParametresDenavit(135, 0, 90, -0.2),
                new Position(),
                Articulation.Type.ROTATION
        ));

        articulations[1].add(new Articulation(
                new ParametresDenavit(0, 1.5, 90, 0),
                new Position(),
                Articulation.Type.TRANSLATION
        ));

        robots[1] = new Robot(base, articulations[1]);
    }




    @Test
    void getPositionOrganeTerminal() {
        attendus.clear();

        //Le résultat attendu pour la 1ère architecture de robot
        attendus.add(new double[][]{
                {1.1102230246251565e-16},
                {1.414213562373095},
                {0}
        });

        //Le résultat attendu pour la 2ème architecture de robot
        attendus.add(new double[][]{
                {1.2020815280171309},
                {0.9192388155425116},
                {1}
        });

        Position result;
        for(int i = 0; i < nbTests; ++i){
            result = robots[i].getPositionOrganeTerminal(null);
            assertArrayEquals(attendus.get(i), result.getAsArray());
        }

    }


    @Test
    void getOrientationOrganeTerminal() {
        attendus.clear();
    }


    @Test
    void getMatriceJacobienne() {
        /*attendus.clear();

        attendus.add(new double[][]{});

        attendus.add(new double[][]{
                {-0.016043744111016167, -0.016043744111016167, 0.707106781128175},
                {0.020980280512894467, 0.020980280512894467, 0.707106781128175},
                {0, 0, 0}
        });

        //On parcourt les architectures de robots et on vérifie que la copie est identique à l'originale
        for(int i = nbTests -1; i > 0; --i) {
            assertArrayEquals(attendus.get(i), robots[i].getMatriceJacobienne(null).getArray());
        }*/
    }


    @Test
    void calculerMatricesTransformation() {
        attendus.clear();

        //Le résultat attendu pour la 1ère architecture de robot
        attendus.add(new double[][]{
                {-0.7071067811865475, -0.7071067811865476, 0, 1.1102230246251565e-16},
                {0.7071067811865476, -0.7071067811865475, 0, 1.414213562373095},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        });


        //Le résultat attendu pour la 2ème architecture de robot
        attendus.add(new double[][]{
                {-0.7071067811865475, 0.7071067811865476, 8.659560562354934e-17, 1.2020815280171309},
                {0.7071067811865476, 0.7071067811865475, 8.659560562354932e-17, 0.9192388155425116},
                {0, 1.2246467991473532e-16, -1, 1},
                {0, 0, 0, 1}
        });

        Matrix[] results;

        //On parcourt les architectures de robots et on vérifie qu'on obtient bien la même chose que ce qui est attendu.
        for (int i = 0; i < nbTests; ++i) {
            results = robots[i].calculerMatricesTransformation(null);
            assertArrayEquals(attendus.get(i), results[articulations[i].size() - 1].getArray());
        }
    }


    @Test
    void copy() {
        attendus.clear();
        Robot robotCopy;

        //On parcourt les architectures de robots et on vérifie que la copie est identique à l'originale
        for(int i = 0; i < nbTests; ++i) {
            robotCopy = robots[i].copy();
            assertArrayEquals(robots[i].getArticulations().toArray(), robotCopy.getArticulations().toArray());
        }
    }


    @Test
    void getVariablesArticulaires() {
        attendus.clear();

        attendus.add(new double[][]{
                {45, 90}
        });

        attendus.add(new double[][]{
                {0, 135, 1.5}
        });

        //On parcourt les architectures de robots et on vérifie que la copie est identique à l'originale
        for(int i = 0; i < nbTests; ++i) {
            assertArrayEquals(attendus.get(i)[0], robots[i].getVariablesArticulaires());
        }
    }
}