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
     * @param alpha Le paramètre α.
     * @param a     Le paramètre a.
     */
    public ParametresDenavit(double theta, double d, double alpha, double a) {
        this.theta = Math.toRadians(theta);
        this.d = d;
        this.alpha = Math.toRadians(alpha);
        this.a = a;
    }


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
