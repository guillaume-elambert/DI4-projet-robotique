import Jama.Matrix;

import java.util.ArrayList;

public class Robot {

    private Position base;
    private ArrayList<Articulation> articulations;

    /**
     * Constructeur par défaut.
     */
    public Robot() {
    }


    /**
     * Constructeur de confort.
     * Les matrices de transfomations sont relatives à l'articulation 1;
     *
     * @param base          La position de la base du robot.
     * @param articulations Les articulations du robot.
     */
    public Robot(Position base, ArrayList<Articulation> articulations) {
        this.base = base;
        this.articulations = articulations;
        //matricesTransformations = forwardKinematic(null);
    }


    /**
     * Méthode pour obtenir la position de l'organe terminal.
     *
     * @param variablesArticulaires Les variables articulaires.
     * @return La position
     */
    public Position getPositionOrganeTerminal(double[] variablesArticulaires){

        ParametresDenavit paramsArticulation;
        Articulation uneArticulation;
        Articulation.Type leType;

        int nbArticulations = articulations.size();
        Matrix T = Matrix.identity(4,4);

        if(variablesArticulaires == null || variablesArticulaires.length == 0){
            variablesArticulaires = getValeursArticulaires();
        }

        // On parcourt les articulations en partant de la fin
        for (int i = 0, j = 0; i < nbArticulations; ++i){

            uneArticulation = articulations.get(i);
            paramsArticulation = uneArticulation.getDenavit();
            leType = uneArticulation.getType();

            //Si l'articulation est du type ROTATION, on récupère la prochaine variable articulaire
            paramsArticulation.setTheta(switch(leType){
                case ROTATION -> variablesArticulaires[j++];
                default -> paramsArticulation.getTheta();
            });

            //Si l'articulation est du type TRANSLATION, on récupère la prochaine variable articulaire
            paramsArticulation.setD(switch(leType){
                case TRANSLATION -> variablesArticulaires[j++];
                default -> paramsArticulation.getD();
            });

            // On multiplie la matrice de Denavit (ou identité si 1ère itération) par la matrice
            // Denavit de l'articulation précédente dans l'architecture du robot.
            T = T.times(uneArticulation.getMatriceDenavit());

        }

        Matrix baseMatrix = new Matrix(new double[][]{
                { base.getX() },
                { base.getY() },
                { base.getZ() }
        });

        int rowTostart = 0, rowToEnd = 2, column = 3;

        // On ajoute les valeursActuelle des coordonnées de la base sur la
        // 4e colonne des 3 premières lignes pour chaque articulation
        for (int i = 0; i < nbArticulations; ++i) {
            T.setMatrix(rowTostart, rowToEnd, column, column, T.getMatrix(rowTostart, rowToEnd, column, column).plus(baseMatrix));
        }

        return new Position(T.getMatrix(0, 2, 3, 3));
    }


    /**
     * Cette fonction calcule les matrices de transformation.
     *
     * @return La liste des matrices de Denavit.
     */
    public Matrix[] getMatricesTransformation() {

        int nbArticulations = articulations.size();
        Matrix transformation = Matrix.identity(4,4);
        Matrix[] T = new Matrix[nbArticulations];
        Articulation uneArticulation;

        // On parcourt les articulations en partant de la fin
        for (int i = 0; i < nbArticulations; ++i){

            uneArticulation = articulations.get(i);

            // On multiplie la matrice de Denavit (ou identité si 1ère itération) par la matrice
            // Denavit de l'articulation précédente dans l'architecture du robot.
            transformation = transformation.times(uneArticulation.getMatriceDenavit());

            //On stock la matrice de Denavit
            T[i] = transformation.copy();
        }


        Matrix baseMatrix = new Matrix(new double[][]{
                { base.getX() },
                { base.getY() },
                { base.getZ() }
        });

        int rowTostart = 0, rowToEnd = 2, column = 3;

        // On ajoute les valeursActuelle des coordonnées de la base sur la
        // 4e colonne des 3 premières lignes pour chaque articulation
        for (int i = 0; i < nbArticulations; ++i) {
            T[i].setMatrix(rowTostart, rowToEnd, column, column, T[i].getMatrix(rowTostart, rowToEnd, column, column).plus(baseMatrix));
        }

        return T;
    }


    public double[] inverseKinetic(double[] target, double[] valeursActuelles){

        if(valeursActuelles == null || valeursActuelles.length == 0){
            valeursActuelles = getValeursArticulaires();
        }

        double[] nouvellesValeurs = valeursActuelles.clone();



        return nouvellesValeurs;
    }


    /**
     * Méthode pour obtenir l'ensemble des valeurs des variables
     * articulaires des articulations qui composent le robot.
     *
     * @return La liste des valeurs des variables articulaires.
     */
    public double[] getValeursArticulaires(){
        int nbArticulations = articulations.size();
        double[] toReturn = new double[nbArticulations];

        for(int i = 0; i < nbArticulations; ++i){
            toReturn[i] = articulations.get(i).getVariableArticulaire();
        }

        return toReturn;
    }


    /**
     * Méthode qui permet de copier un robot.
     *
     * @return La copie du robot.
     */
    public Robot copy(){
        return new Robot(this.base, this.articulations);
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
}
