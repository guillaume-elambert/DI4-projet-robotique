package Model;

import Jama.Matrix;

/**
 * Classe pour les positions.
 */
public class Position {

    /** La position en x.*/
    private double x;
    /** La position en y.*/
    private double y;
    /** La position en z.*/
    private double z;


    /**
     * Constructeur.
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
     * @param matriceXYZ La <a href="https://math.nist.gov/javanumerics/jama/doc/Jama/Matrix.html">matrice</a> contenant la position.
     */
    public Position(Matrix matriceXYZ) {
        x = matriceXYZ.get(0, 0);
        y = matriceXYZ.get(1, 0);
        z = matriceXYZ.get(2, 0);
    }

    /**
     * Constructeur de confort
     *
     * @param tableauXYZ Le tableau a 2 dimension contenant la position.
     */
    public Position(double[][] tableauXYZ) {
        x = tableauXYZ[0][0];
        y = tableauXYZ[1][0];
        z = tableauXYZ[2][0];
    }


    /**
     * Constructeur de recopie.
     *
     * @param toCopy L'objet Model.Position à copier.
     */
    public Position(Position toCopy) {
        x = toCopy.x;
        y = toCopy.y;
        z = toCopy.z;
    }


    /**
     * Méthode qui permet de verifier si 2 objets Model.Position sont égaux.
     *
     * @param toCompareWith L'objet Model.Position à comparé avec l'objet appelant.
     * @return true si égaux, false sinon.
     */
    public boolean equals(Position toCompareWith) {
        return (
                toCompareWith != null
                        && x == toCompareWith.getX()
                        && y == toCompareWith.getY()
                        && z == toCompareWith.getZ()
        );
    }


    /**
     * Retourne l'objet sous forme de tableau.
     *
     * @return Le tableau à 2 dimensions de la position.
     */
    public double[][] getAsArray() {
        return new double[][]{
                {x},
                {y},
                {z}
        };
    }


    /**
     * Retourne l'objet sous forme de matrice.
     *
     * @return La <a href="https://math.nist.gov/javanumerics/jama/doc/Jama/Matrix.html">matrice</a> position.
     */
    public Matrix getAsMatrix() {
        return new Matrix(new double[][]{
                {x},
                {y},
                {z}
        });
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Position)) {
			return false;
		}
		Position other = (Position) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
			return false;
		}
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
			return false;
		}
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
    
}
