package Model;

import java.util.ArrayList;
import java.util.Objects;

/**
 * La classe pour les robots.
 */
public class Robot {

    /** La @ref Model.Position "position" de la base. */
    private Position base;
    /** La liste des @ref Model.Articulation "articulation" du robot. */
    private ArrayList<Articulation> articulations;


    /**
     * Constructeur.
     * Les matrices de transfomations sont relatives à l'articulation 1;
     *
     * @param base          L'objet @ref Model.Position "position" de la base du robot.
     * @param articulations Les @ref Model.Articulation "articulation" du robot.
     */
    public Robot(Position base, ArrayList<Articulation> articulations) {
        this.base = base;
        this.articulations = articulations;
    }


    /**
     * Méthode pour obtenir l'ensemble des valeurs des variables
     * articulaires des articulations qui composent le robot.
     *
     * @return La liste des valeurs des variables articulaires.
     */
    public double[] getVariablesArticulaires() {
        int nbArticulations = articulations.size();
        double[] toReturn = new double[nbArticulations];

        for (int i = 0; i < nbArticulations; ++i) {
            toReturn[i] = articulations.get(i).getVariableArticulaire();
        }

        return toReturn;
    }


    /**
     * Méthode pour modifier l'ensemble des valeurs des variables
     * articulaires des articulations qui composent le robot.
     *
     * @param nouvellesValeurs La liste de nouvelles variables articulaires.
     */
    public void setVariablesArticulaires(double[] nouvellesValeurs) {

        int nbArticulations = articulations.size();

        if (nouvellesValeurs.length < nbArticulations) return;

        for (int i = 0; i < nbArticulations; ++i) {
            articulations.get(i).setVariableArticulaire(nouvellesValeurs[i]);
        }
    }


    /**
     * Méthode qui permet de copier un robot.
     *
     * @return La copie du robot.
     */
    public Robot copy() {
        ArrayList<Articulation> articulationsCopy = new ArrayList<>();

        for (Articulation uneArticulation : articulations) {
            articulationsCopy.add(new Articulation(uneArticulation));
        }

        return new Robot(new Position(this.base), articulationsCopy);
    }


    /**
     * @return the base
     */
    public Position getBase() {
        return base;
    }


    /**
     * @param base the base to set
     */
    public void setBase(Position base) {
        this.base = base;
    }


    /**
     * @return the articulations
     */
    public ArrayList<Articulation> getArticulations() {
        return articulations;
    }


    /**
     * @param articulations the articulations to set
     */
    public void setArticulations(ArrayList<Articulation> articulations) {
        this.articulations = articulations;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Robot robot)) return false;
        return getBase().equals(robot.getBase()) && getArticulations().equals(robot.getArticulations());
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(getBase(), getArticulations());
    }


    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Robot [base=" + base + ", articulations=" + articulations + "]";
	}

}
