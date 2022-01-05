public class ParametresDenavit {

    private double theta, d, alpha, a;


    /**
     * Constructeur par défaut.
     */
    public ParametresDenavit() {
    }


    /**
     * Constructeur de confort.
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
    public ParametresDenavit(ParametresDenavit toCopy){
        theta = toCopy.theta;
        d = toCopy.d;
        alpha = toCopy.alpha;
        a = toCopy.a;
    }


    /**
     * Méthode pour obtenir l'objet sous forme de chaîne de caractères.
     *
     * @return L'objet sous forme de chaîne de caractères.
     */
    public String toString(){
        return  "Theta (θ) = "+theta+"\n" +
                "d = "+d+"\n" +
                "Alpha (α) = "+alpha+"\n" +
                "a = "+a;
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


}
