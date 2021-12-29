import Jama.Matrix;

public class Articulation {

    protected enum Type {
        ROTATION,
        TRANSLATION
    }

    private ParametresDenavit denavit;
    private Position position;
    private Type type;


    /**
     * Constructeur par défaut.
     */
    public Articulation() {
    }


    /**
     * Constructeur de confort.
     *
     * @param denavit  Les paramètres Denavit de l'articulation.
     * @param position La position dans l'espace de l'articulation.
     * @param type     Le type de l'articulation.
     */
    public Articulation(ParametresDenavit denavit, Position position, Type type) {
        this.denavit = denavit;
        this.position = position;
        this.type = type;
    }


    /**
     * Constructeur de copie.
     *
     * @param toCopy L'objet à copier.
     */
    public Articulation(Articulation toCopy){
        denavit = new ParametresDenavit(toCopy.getDenavit());
        position = new Position(toCopy.getPosition());
        type = toCopy.getType();
    }


    /**
     * Méthode qui retourne la variable articulaire de l'articulation.
     *
     * @return La valeur de la variable articulaire (avec theta en degrés).
     */
    public double getVariableArticulaire(){
        return switch (type) {
            case ROTATION -> Math.toDegrees(this.denavit.getTheta());
            case TRANSLATION -> this.denavit.getD();
        };
    }


    /**
     * Méthode qui retourne la matrice de Denavit de l'articulation.
     *
     * @return La matrice de Denavit de l'articulation.
     */
    public Matrix getMatriceDenavit(){
        Matrix matriceDenavit = null;

        double theta, d, alpha, a;
        double cosTheta, sinTheta, cosAlpha, sinAlpha;

        //On récupère les paramètres de Denavit
        theta = denavit.getTheta();
        d = denavit.getD();
        alpha = denavit.getAlpha();
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
     * Méthode pour obtenir l'objet sous forme de chaîne de caractères.
     *
     * @return L'objet sous forme de chaîne de caractères.
     */
    public String toString(){
        return  "Type : " + this.type.toString()+"\n\n" +
                "Position : \n" + position.toString() + "\n\n"+
                "Denavit : \n" + denavit.toString();
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
     * @return the position
     */
    public Position getPosition() {
        return position;
    }


    /**
     * @param position the position to set
     */
    public void setPosition(Position position) {
        this.position = position;
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
}
