package Model;

import java.util.Objects;

/**
 * La classe pour les paramètres de Denavit.
 */
public class ParametresDenavit {

    /** Le paramètre θ (en degrés). */
    private double theta;
    /** Le paramètre d. */
    private double d;
    /** Le paramètre α (en degrés). */
    private double alpha;
    /** Le paramètre a. */
    private double a;


    /**
     * Constructeur.
     *
     * @param theta Le paramètre θ (en degrés).
     * @param d     Le paramètre d.
     * @param alpha Le paramètre α (en degrés).
     * @param a     Le paramètre a.
     */
    public ParametresDenavit(double theta, double d, double alpha, double a) {
        this.theta = theta;
        this.d = d;
        this.alpha = alpha;
        this.a = a;
    }


    /**
     * Constructeur de copie.
     *
     * @param toCopy L'objet à copier.
     */
    public ParametresDenavit(ParametresDenavit toCopy) {
        theta = toCopy.theta;
        d = toCopy.d;
        alpha = toCopy.alpha;
        a = toCopy.a;
    }


    /**
     * @return the theta
     */
    public double getTheta() {
        return theta;
    }


    /**
     * @param theta the theta to set
     */
    public void setTheta(double theta) {
        this.theta = theta;
    }


    /**
     * @return the d
     */
    public double getD() {
        return d;
    }


    /**
     * @param d the d to set
     */
    public void setD(double d) {
        this.d = d;
    }


    /**
     * @return the alpha
     */
    public double getAlpha() {
        return alpha;
    }


    /**
     * @param alpha the alpha to set
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }


    /**
     * @return the a
     */
    public double getA() {
        return a;
    }


    /**
     * @param a the a to set
     */
    public void setA(double a) {
        this.a = a;
    }


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(a);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(alpha);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(d);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(theta);
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
		if (!(obj instanceof ParametresDenavit)) {
			return false;
		}
		ParametresDenavit other = (ParametresDenavit) obj;
		if (Double.doubleToLongBits(a) != Double.doubleToLongBits(other.a)) {
			return false;
		}
		if (Double.doubleToLongBits(alpha) != Double.doubleToLongBits(other.alpha)) {
			return false;
		}
		if (Double.doubleToLongBits(d) != Double.doubleToLongBits(other.d)) {
			return false;
		}
		if (Double.doubleToLongBits(theta) != Double.doubleToLongBits(other.theta)) {
			return false;
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ParametresDenavit [theta=" + theta + ", d=" + d + ", alpha=" + alpha + ", a=" + a + "]";
	}
}
