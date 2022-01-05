package Controller;

import Jama.Matrix;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GrevilleTest {

    static ArrayList<double[][]> attendus = new ArrayList<>();
    static ArrayList<Matrix> matrices = new ArrayList<>();

    static {


        matrices.add(new Matrix(new double[][]{
                {1, 0, 0},
                {0, 2, 0},
                {0, 0, 4}
        }));

        matrices.add(new Matrix(new double[][]{
                {1, 4, 2},
                {3, 1, 7}
        }));

        matrices.add(new Matrix(new double[][]{
                {1, 1, -2},
                {2, 4, -2},
                {-1, 3, 6},
                {4, 7, -5}
        }));
    }


    @Test
    void calculPseudoInverse() {
        attendus.clear();

        attendus.add(new double[][]{
                {1, 0, 0},
                {0, (double)1/2, 0},
                {0, 0, (double)1/4}
        });

        attendus.add(new double[][]{
                {(double)-4/798, (double)42/798}, {(double)215/798, (double)-63/798},
                {(double)-29/798, (double)105/798}
        });

        attendus.add(new double[][]{
                {(double)23/2717, (double)4/247, (double)-27/2717, (double)89/2717},
                {(double)3/5434, (double)11/247, (double)469/5434, (double)183/2717},
                {(double)-135/5434, (double)-1/247, (double)631/5434, (double)-84/2717}
        });


        int nbLignes, nbColonnes, nbTests = matrices.size();
        double[][] resultat, attendu;

        for(int i = 0; i < nbTests; ++i) {
            resultat = Greville.calculerPseudoInverse(matrices.get(i)).getArray();
            attendu = attendus.get(i);
            nbLignes = resultat.length;
            nbColonnes = resultat[0].length;



            for(int j = 0; j < nbLignes; ++j){
                for(int k = 0; k < nbColonnes; ++k){
                    try {
                        assertTrue(Math.abs(attendu[j][k] - resultat[j][k]) < 1e-10);
                    } catch (AssertionError e){
                        System.err.println("Erreur lors du test n°" + (i+1) + "\n" +
                                "Attendu (± 1e-10)\t: " + attendu[j][k] + "\n" +
                                "Obtenu\t\t\t\t: " + resultat[j][k]);
                        throw e;
                    }
                }
            }
        }
    }
}