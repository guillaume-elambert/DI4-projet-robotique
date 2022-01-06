package Model;

import Jama.Matrix;

/**
 * Classe pour les articutlation.
 */
public class Articulation {

    /** Les types d'articulation possibles. */
    public enum Type {
        /** Type Rotation. */
        ROTATION,
        /** Type Translation. */
        TRANSLATION
    }

    /** Les paramètres Denavit de l'articulation (@ref Model.ParametresDenavit "paramètres de Denavit"). */
    private ParametresDenavit denavit;
    /** Le type de l'articulation. */
    private Type type;


    /**
     * Constructeur par défaut.
     */
    public Articulation() {
    }


    /**
     * Constructeur de confort.
     *
     * @param denavit  Les @ref Model.ParametresDenavit "paramètres de Denavit" de l'articulation.
     * @param type     Le type de l'articulation.
     */
    public Articulation(ParametresDenavit denavit, Type type) {
        this.denavit = denavit;
        this.type = type;
    }


    /**
     * Constructeur de copie.
     *
     * @param toCopy L'objet à copier.
     */
    public Articulation(Articulation toCopy){
        denavit = new ParametresDenavit(toCopy.getDenavit());
        type = toCopy.getType();
    }


    /**
     * Méthode qui retourne la variable articulaire de l'articulation.
     *
     * @return La valeur de la variable articulaire (avec theta en degrés).
     */
    public double getVariableArticulaire(){
        return switch (type) {
            case ROTATION -> this.denavit.getTheta();
            case TRANSLATION -> this.denavit.getD();
        };
    }


    /**
     * Méthode qui définit la valeur de la variable articulaire.
     *
     * @param nouvelleValeur La valeur de la variable articulaire.
     */
    public void setVariableArticulaire(double nouvelleValeur){
        switch (type) {
            case ROTATION -> this.denavit.setTheta(nouvelleValeur);
            case TRANSLATION -> this.denavit.setD(nouvelleValeur);
        }
    }


    /**
     * Méthode qui retourne la matrice de Denavit de l'articulation.
     *
     * @return La <a href="https://math.nist.gov/javanumerics/jama/doc/Jama/Matrix.html">matrice</a> de Denavit de l'articulation.
     */
    public Matrix getMatriceDenavit(){
        Matrix matriceDenavit = null;

        double theta, d, alpha, a;
        double cosTheta, sinTheta, cosAlpha, sinAlpha;

        //On récupère les paramètres de Denavit
        theta = Math.toRadians(denavit.getTheta());
        d = denavit.getD();
        alpha = Math.toRadians(denavit.getAlpha());
        a = denavit.getA();

        //Calcul des sin et cos
        cosTheta = Math.cos(theta);
        sinTheta = Math.sin(theta);
        cosAlpha = Math.cos(alpha);
        sinAlpha = Math.sin(alpha);

        //On calcul la matrice de Denavit pour l'articulation i
        matriceDenavit = new Matrix(new double[][]{
                { cosTheta, -sinTheta*cosAlpha, sinTheta*sinAlpha , a*cosTheta },
                { sinTheta, cosTheta*cosAlpha , -cosTheta*sinAlpha, a*sinTheta },
                { 0       , sinAlpha          , cosAlpha          , d          },
                { 0       , 0                 , 0                 , 1          }
        });


        return matriceDenavit;
    }


    /**
     * @return the denavit
     */
    public ParametresDenavit getDenavit() {
        return denavit;
    }


    /**
     * @param denavit the denavit to set
     */
    public void setDenavit(ParametresDenavit denavit) {
        this.denavit = denavit;
    }


	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((denavit == null) ? 0 : denavit.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (!(obj instanceof Articulation)) {
			return false;
		}
		Articulation other = (Articulation) obj;
		if (denavit == null) {
			if (other.denavit != null) {
				return false;
			}
		} else if (!denavit.equals(other.denavit)) {
			return false;
		}
		if (type != other.type) {
			return false;
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Articulation [denavit=" + denavit + ", type=" + type + "]";
	}
    
}
