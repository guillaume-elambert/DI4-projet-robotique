import Jama.Matrix;

import java.util.ArrayList;

public class Robot {

    private boolean architectureChanged;
    private Position base;
    private ArrayList<Articulation> articulations;
    private Matrix[] matricesTransformation;

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
        architectureChanged = false;
        matricesTransformation = calculerMatricesTransformation(null);
    }


    /**
     * Méthode pour obtenir la position de l'organe terminal.
     *
     * @param variablesArticulaires Les variables articulaires à utiliser (si null, utilise celles de l'architecture actuelle).
     * @return La position de l'organe terminal.
     */
    public Position getPositionOrganeTerminal(double[] variablesArticulaires){
        Matrix[] toUse;

        //Si null on utilise les matrices de transformation de l'architecture actuelle du robot
        if(variablesArticulaires == null || variablesArticulaires.length == 0) {

            //On met à jour les matrices de transformation si ça à lieu d'être.
            update();
            toUse = matricesTransformation;
        } else {
            toUse = calculerMatricesTransformation(variablesArticulaires);
        }

        //Retourne les 3 premières lignes de la 4ème colonne de la matrice de transformation
        return new Position(toUse[toUse.length-1].getMatrix(0, 2, 3, 3));
    }


    /**
     * Méthode pour obtenir l'orientation de l'organe terminal.
     *
     * @param variablesArticulaires Les variables articulaires à utiliser (si null, utilise celles de l'architecture actuelle).
     * @return La matrice orientation.
     */
    public Matrix getOrientationOrganeTerminal(double[] variablesArticulaires){
        Matrix[] toUse;

        //Si null on utilise les matrices de transformation de l'architecture actuelle du robot
        if(variablesArticulaires == null || variablesArticulaires.length == 0) {

            //On met à jour les matrices de transformation si ça à lieu d'être.
            update();
            toUse = matricesTransformation;
        } else {
            toUse = calculerMatricesTransformation(variablesArticulaires);
        }

        //Retourne les 3 premières lignes et les 3 premières colonnes de la matrice de transformation
        return toUse[toUse.length - 1].getMatrix(0, 2, 0, 2);
    }


    /**
     * Cette fonction calcule la matrice Jacobienne.
     *
     * @param variablesArticulaires Les variables articulaires à utiliser (si null, utilise celles de l'architecture actuelle).
     * @return La matrice Jacobienne.
     */
    public Matrix getMatriceJacobienne(double[] variablesArticulaires){

        if(variablesArticulaires == null || variablesArticulaires.length == 0) {
            variablesArticulaires = getVariablesArticulaires();
        }

        double[][] matJacobienne = new double[3][variablesArticulaires.length];
        double[][] positionInitiale, position2;
        double epsilon = 1E-6;
        double[] variablesArticulairesCpy;

        positionInitiale = getPositionOrganeTerminal(variablesArticulaires).getAsArray();

        for (int i = 0; i < variablesArticulaires.length; i++) {

            variablesArticulairesCpy = variablesArticulaires;
            variablesArticulairesCpy[i] += epsilon;

            position2 = getPositionOrganeTerminal(variablesArticulairesCpy).getAsArray();
            System.out.println(getPositionOrganeTerminal(variablesArticulairesCpy).toString());

            matJacobienne[0][i] = (position2[0][0] - positionInitiale[0][0]) / epsilon;
            matJacobienne[1][i] = (position2[1][0] - positionInitiale[1][0]) / epsilon;
            matJacobienne[2][i] = (position2[2][0] - positionInitiale[2][0]) / epsilon;

        }

        return new Matrix(matJacobienne);
    }


    /**
     * Cette fonction calcule les matrices de transformation.
     *
     * @param variablesArticulaires Les variables articulaires à utiliser (si null, utilise celles de l'architecture actuelle).
     * @return La liste des matrices de transformation de Denavit.
     */
    public Matrix[] calculerMatricesTransformation(double[] variablesArticulaires) {

        int nbArticulations = articulations.size();
        Matrix transformation = Matrix.identity(4,4);
        Matrix[] T = new Matrix[nbArticulations];

        Articulation uneArticulation;
        ParametresDenavit paramsArticulation;
        Articulation.Type leType;


        if(variablesArticulaires == null || variablesArticulaires.length == 0) {
            variablesArticulaires = getVariablesArticulaires();
        }


        // On parcourt les articulations en partant de la fin
        for (int i = 0, j = 0; i < nbArticulations; ++i){

            uneArticulation = articulations.get(i);
            paramsArticulation = uneArticulation.getDenavit();
            leType = uneArticulation.getType();

            //Si l'articulation est du type ROTATION, on récupère la prochaine variable articulaire
            paramsArticulation.setTheta(switch(leType){
                case ROTATION -> Math.toRadians(variablesArticulaires[j++]);
                default -> Math.toRadians(paramsArticulation.getTheta());
            });

            //Si l'articulation est du type TRANSLATION, on récupère la prochaine variable articulaire
            paramsArticulation.setD(switch(leType){
                case TRANSLATION -> variablesArticulaires[j++];
                default -> paramsArticulation.getD();
            });

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


    /**
     * Méthode pour obtenir l'ensemble des valeurs des variables
     * articulaires des articulations qui composent le robot.
     *
     * @return La liste des valeurs des variables articulaires.
     */
    public double[] getVariablesArticulaires(){
        int nbArticulations = articulations.size();
        double[] toReturn = new double[nbArticulations];

        for(int i = 0; i < nbArticulations; ++i){
            toReturn[i] = articulations.get(i).getVariableArticulaire();
        }

        return toReturn;
    }


    /**
     * Méthode qui met à jour les matrices de transformation si cela à lieu d'être.
     */
    public void update(){
        if(architectureChanged){
            matricesTransformation = calculerMatricesTransformation(null);
        }
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
        architectureChanged = !this.base.equals(base);
        architectureChanged = true;
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
}
