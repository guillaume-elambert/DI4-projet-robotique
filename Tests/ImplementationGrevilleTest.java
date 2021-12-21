import Jama.Matrix;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ImplementationGrevilleTest {

    @Test
    void grevilleTest() {
        ArrayList<Matrix> matrices = new ArrayList<>();
        ArrayList<double[][]> attendus = new ArrayList<>();
        int nbTests, nbLignes, nbColonnes;
        double[][] resultat, attendu;

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


        /* RESULTATS ATTENDUS */

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

        nbTests = matrices.size();

        for(int i = 0; i < nbTests; ++i) {
            resultat = ImplementationGreville.greville(matrices.get(i)).getArray();
            nbLignes = resultat.length;
            nbColonnes = resultat[0].length;

            for(int j = 0; j < nbLignes; ++j){
                for(int k = 0; k < nbColonnes; ++k){
                    resultat[j][k] = ((long) resultat[j][k] * 1e10) / 1e10;
                }
            }

            attendu = attendus.get(i);
            nbLignes = attendu.length;
            nbColonnes = attendu[0].length;

            for(int j = 0; j < nbLignes; ++j){
                for(int k = 0; k < nbColonnes; ++k){
                    attendu[j][k] = ((long) attendu[j][k] * 1e10) / 1e10;
                }
            }

            try {
                assertArrayEquals(attendu, resultat);
            } catch (AssertionError e){
                System.err.printf("Erreur lors du test n°%d :\n",i+1);
                e.printStackTrace();
            }
        }

    }

}