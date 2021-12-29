import Jama.Matrix;

public class Position {

    private double x, y, z;

    /**
     * Constructeur par défaut.
     */
    public Position() {
    }


    /**
     * Constructeur de confort
     *
     * @param x La position sur l'axe x.
     * @param y La position sur l'axe y.
     * @param z La position sur l'axe z.
     */
    public Position(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructeur de confort
     *
     * @param matriceXYZ La matrice contenant la position.
     */
    public Position(Matrix matriceXYZ){
        x = matriceXYZ.get(0,0);
        y = matriceXYZ.get(1,0);
        z = matriceXYZ.get(2, 0);
    }


    /**
     * Constructeur de recopie.
     *
     * @param toCopy L'objet Position à copier.
     */
    public Position(Position toCopy){
        x = toCopy.x;
        y = toCopy.y;
        z = toCopy.z;
    }


    /**
     * Retourne l'objet sous forme de tableau.
     *
     * @return Le tableau à 2 dimensions de la position.
     */
    public double[][] getAsArray(){
        return new double[][]{
                {x},
                {y},
                {z}
        };
    }


    /**
     * Retourne l'objet sous forme de matrice.
     *
     * @return La matrice position.
     */
    public Matrix getAsMatrix(){
        return new Matrix(new double[][]{
                {x},
                {y},
                {z}
        });
    }


    /**
     * Méthode pour obtenir l'objet sous forme de chaîne de caractères.
     *
     * @return L'objet sous forme de chaîne de caractères.
     */
    public String toString(){
        return  "x : " + x +"\n"+
                "y : " + y +"\n"+
                "z : " + z;
    }


    /**
     * @return the x
     */
    public double getX() {
        return x;
    }


    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }


    /**
     * @return the y
     */
    public double getY() {
        return y;
    }


    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }


    /**
     * @return the z
     */
    public double getZ() {
        return z;
    }


    /**
     * @param z the z to set
     */
    public void setZ(double z) {
        this.z = z;
    }
}
