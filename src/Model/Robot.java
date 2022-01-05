package Model;

import java.util.ArrayList;

/**
 * La classe pour les robots.
 */
public class Robot {

    /** Variable pour savoir si l'architecture du robot à été changée. */
    private transient boolean architectureChanged; //transient pour éviter l'export en JSON
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
        architectureChanged = true;
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
        architectureChanged = !this.base.equals(base);
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
        architectureChanged = !this.articulations.equals(articulations);
        this.articulations = articulations;
    }


    /**
     * @return the architectureChanged
     */
    public boolean isArchitectureChanged() {
        return architectureChanged;
    }


    /**
     * @param architectureChanged the architectureChanged to set
     */
    public void setArchitectureChanged(boolean architectureChanged) {
        this.architectureChanged = architectureChanged;
    }
}
