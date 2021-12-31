import Jama.Matrix;


public class ImplementationGreville {

    static Matrix matA, resultat;

    public static void main(String[] args) {

        matA = new Matrix(new double[][]{
                {1, 0, 0},
                {0, 2, 0},
                {0, 0, 4}
        });

        resultat = greville(matA);
        System.out.println("Résultat final :");
        resultat.print(5, 5);


        matA = new Matrix(new double[][]{
                {1, 4, 2},
                {3, 1, 7}
        });

        resultat = greville(matA);
        System.out.println("Résultat final :");
        resultat.print(5, 5);
    }


    static Matrix greville(Matrix matA) {


        Matrix matAPlus, matAk, matAPlusk, transpose, a, b, c, d;
        int n, m, rowMatAPlusk;


        n = matA.getRowDimension();
        m = matA.getColumnDimension();

        matAPlus = new Matrix(1, n);


        /*======== 1ere iteration ========*/

        //On récupère la 1ère colonne
        a = matA.getMatrix(0, n - 1, 0, 0);

        if (!a.equals(new Matrix(n, 1))) {
            transpose = a.transpose();
            matAPlusk = transpose.times(a).inverse().times(transpose);

            rowMatAPlusk = matAPlusk.getColumnDimension();
            matAPlus.setMatrix(0, 0, 0, rowMatAPlusk - 1, matAPlusk);

        }

        double dDouble;

        for (int k = 1; k < m; ++k) {
            matAk = matA.getMatrix(0, n - 1, 0, k - 1);
            matAPlusk = matAPlus.getMatrix(0, k - 1, 0, n - 1);


            a = matA.getMatrix(0, n - 1, k, k);

            d = matAPlusk.times(a);

            //Entrée : d est un chiffre et non une matrice
            if (d.getRowDimension() == 1 && d.getColumnDimension() == 1) {
                dDouble = d.get(0, 0);
                c = a.minus(matAk.times(dDouble));
            } else {
                c = a.minus(matAk.times(d));
            }

            //On vérifie si la matrice est nulle
            int notNullCount = 0, cRow = c.getRowDimension(), cCol = c.getColumnDimension();
            double laValeur;
            for (int i = 0; i < cRow && notNullCount == 0; ++i) {
                for (int j = 0; j < cCol && notNullCount == 0; ++j) {
                    laValeur = c.get(i, j);
                    if (laValeur != 0.0) {
                        //Regarde si en faisant une approximation 10^-8 on est a 0
                        if ( Math.abs(laValeur) < 1e-8 ) {
                            c.set(i, j, 0.0);
                            break;
                        }
                        ++notNullCount;
                    }
                }
            }


            //Entrée : c'est une matrice nulle
            if (notNullCount == 0) {
                transpose = d.transpose();
                b = transpose.times(d);
                b = Matrix.identity(b.getRowDimension(), b.getColumnDimension()).plus(b);
                b = b.inverse().times(transpose).times(matAPlusk);
            } else {
                transpose = c.transpose();
                b = transpose.times(c).inverse().times(transpose);
            }

            matAPlusk = matAPlusk.minus(d.times(b));
            rowMatAPlusk = matAPlusk.getRowDimension();


            Matrix tmp = new Matrix(b.getRowDimension() + matAPlus.getRowDimension(), matAPlus.getColumnDimension());
            tmp.setMatrix(0, matAPlus.getRowDimension() - 1, 0, matAPlus.getColumnDimension() - 1, matAPlus);
            matAPlus = tmp;

            matAPlus.setMatrix(0, rowMatAPlusk - 1, 0, n - 1, matAPlusk);
            matAPlus.setMatrix(rowMatAPlusk, rowMatAPlusk, 0, b.getColumnDimension() - 1, b);

        }

        return matAPlus;
    }

}
