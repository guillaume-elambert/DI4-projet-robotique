package Controller;

import Jama.Matrix;
import Model.Articulation;
import Model.ParametresDenavit;
import Model.Position;
import Model.Robot;

import java.util.ArrayList;

/**
 * Classe pour les calculs de cinématique pour un objet @ref Model.Robot "robot".
 */
public class Cinematique {

    /** Le robot. */
    private Robot robot;

    /** La liste des matrices de transformation du robot. */
    private Matrix[] matricesTransformation;


    /**
     * Constructeur.
     *
     * @param robot Le robot.
     */
    public Cinematique(Robot robot) {
        this.robot = robot;
    }

    /**
     * Méthode pour obtenir la position de l'organe terminal.
     *
     * @param variablesArticulaires Les variables articulaires à utiliser (si null, utilise celles de l'architecture actuelle).
     * @return La position de l'organe terminal.
     */
    public Position getPositionOrganeTerminal(double[] variablesArticulaires) {
        Matrix[] toUse;

        //Si null on utilise les matrices de transformation de l'architecture actuelle du robot
        if (variablesArticulaires == null || variablesArticulaires.length == 0) {

            //On met à jour les matrices de transformation si ça à lieu d'être.
            update();
            toUse = matricesTransformation;
        } else {
            toUse = calculerMatricesTransformation(variablesArticulaires);
        }

        //Retourne les 3 premières lignes de la 4ème colonne de la matrice de transformation
        return new Position(toUse[toUse.length - 1].getMatrix(0, 2, 3, 3));
    }


    /**
     * Méthode pour obtenir l'orientation de l'organe terminal.
     *
     * @param variablesArticulaires Les variables articulaires à utiliser (si null, utilise celles de l'architecture actuelle).
     * @return La matrice orientation.
     */
    public Matrix getOrientationOrganeTerminal(double[] variablesArticulaires) {
        Matrix[] toUse;

        //Si null on utilise les matrices de transformation de l'architecture actuelle du robot
        if (variablesArticulaires == null || variablesArticulaires.length == 0) {

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
    public Matrix getMatriceJacobienne(double[] variablesArticulaires) {

        //Si null on utilise les variables articulaires de l'architecture actuelle du robot
        if (variablesArticulaires == null || variablesArticulaires.length == 0) {
            variablesArticulaires = robot.getVariablesArticulaires();
        }

        int nbVariablesArticulaires = variablesArticulaires.length;
        double[][] matJacobienne = new double[3][variablesArticulaires.length];
        double[][] positionInitiale, position2;
        double epsilon = 1E-6;
        double[] nouvellesVariablesArticulaires = new double[nbVariablesArticulaires];

        positionInitiale = getPositionOrganeTerminal(variablesArticulaires).getAsArray();

        // On parcourt les variables articulaires
        for (int i = 0; i < nbVariablesArticulaires; i++) {

            //On copie la liste des variables articulaires
            System.arraycopy(variablesArticulaires, 0, nouvellesVariablesArticulaires, 0, nbVariablesArticulaires);

            nouvellesVariablesArticulaires[i] += epsilon;

            position2 = getPositionOrganeTerminal(nouvellesVariablesArticulaires).getAsArray();

            matJacobienne[0][i] = (position2[0][0] - positionInitiale[0][0]) / epsilon;
            matJacobienne[1][i] = (position2[1][0] - positionInitiale[1][0]) / epsilon;
            matJacobienne[2][i] = (position2[2][0] - positionInitiale[2][0]) / epsilon;

        }

        return new Matrix(matJacobienne);
    }


    /**
     * Méthode qui calcule la valeur des variables articulaires afin d'arriver au plus proche de l'objectif.
     *
     * @param objectifOrganeTerminal L'objet @ref Model.Position "position" qui définit la position objectif de l'organe terminal.
     * @param maxIterations          Le nombre maximum d'itérations à effectuer.
     * @return La liste des variables articulaires pour se rapprocher au mieux de l'objectif.
     */
    public double[] resoudreVariablesArticulaires(Position objectifOrganeTerminal, int maxIterations) {


        double[] variablesArticulaires = robot.getVariablesArticulaires();
        int nbVariablesArticulaires = variablesArticulaires.length;
        double[][] deltaTheta;


        Position posOrgTerm;
        Matrix deltaPos, pinvMatJacobienne, objectifMatrice = objectifOrganeTerminal.getAsMatrix();


        //On s'arrête après maxIterations ou si on a obtenu la position attendue
        for (int i = 0; i < maxIterations && !objectifOrganeTerminal.equals(posOrgTerm = getPositionOrganeTerminal(variablesArticulaires)); ++i) {

            // ΔX
            deltaPos = objectifMatrice.minus(posOrgTerm.getAsMatrix());

            // "J^-1(θ)"
            pinvMatJacobienne = Greville.calculerPseudoInverse(getMatriceJacobienne(variablesArticulaires));

            // Δθ = "J^-1(θ)" * ΔX
            deltaTheta = pinvMatJacobienne.times(deltaPos).getArray();

            // On met à jour les variables articulaires
            for (int j = 0; j < nbVariablesArticulaires; ++j) {
                variablesArticulaires[j] += deltaTheta[j][0];
            }
        }

        return variablesArticulaires;
    }


    /**
     * Cette fonction calcule les matrices de transformation.
     *
     * @param variablesArticulaires Les variables articulaires à utiliser (si null, utilise celles de l'architecture actuelle).
     * @return La liste des <a href="https://math.nist.gov/javanumerics/jama/doc/Jama/Matrix.html">matrice</a> de transformation de Denavit.
     */
    public Matrix[] calculerMatricesTransformation(double[] variablesArticulaires) {

        Robot robotCopy = robot.copy();

        ArrayList<Articulation> articulations = robotCopy.getArticulations();
        int nbArticulations = articulations.size();
        Matrix transformation = Matrix.identity(4, 4);
        Matrix[] T = new Matrix[nbArticulations];

        Articulation uneArticulation;
        ParametresDenavit paramsArticulation;
        Articulation.Type leType;


        if (variablesArticulaires == null || variablesArticulaires.length == 0) {
            variablesArticulaires = robot.getVariablesArticulaires();
        }

        // On parcourt les articulations en partant de la fin
        for (int i = 0; i < nbArticulations; ++i) {

            uneArticulation = articulations.get(i);
            paramsArticulation = uneArticulation.getDenavit();
            leType = uneArticulation.getType();

            //Si l'articulation est du type ROTATION, on récupère la prochaine variable articulaire
            paramsArticulation.setTheta(switch (leType) {
                case ROTATION -> variablesArticulaires[i];
                default -> paramsArticulation.getTheta();
            });

            //Si l'articulation est du type TRANSLATION, on récupère la prochaine variable articulaire
            paramsArticulation.setD(switch (leType) {
                case TRANSLATION -> variablesArticulaires[i];
                default -> paramsArticulation.getD();
            });

            // On multiplie la matrice de Denavit (ou identité si 1ère itération) par la matrice
            // Denavit de l'articulation précédente dans l'architecture du robot.
            transformation = transformation.times(uneArticulation.getMatriceDenavit());

            //On stock la matrice de Denavit
            T[i] = transformation.copy();
        }

        Position base = robot.getBase();
        Matrix baseMatrix = new Matrix(new double[][]{
                {base.getX()},
                {base.getY()},
                {base.getZ()}
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
     * Méthode qui met à jour les matrices de transformation si cela à lieu d'être.
     */
    public void update() {
        if (robot.isArchitectureChanged()) {
            matricesTransformation = calculerMatricesTransformation(robot.getVariablesArticulaires());
            robot.setArchitectureChanged(false);
        }
    }


    /**
     * @return the robot
     */
    public Robot getRobot() {
        return robot;
    }


    /**
     * @param robot the robot to set
     */
    public void setRobot(Robot robot) {
        this.robot = robot;
    }


    /**
     * @return the matricesTransformation
     */
    public Matrix[] getMatricesTransformation() {
        return matricesTransformation;
    }


    /**
     * @param matricesTransformation the matricesTransformation to set
     */
    public void setMatricesTransformation(Matrix[] matricesTransformation) {
        this.matricesTransformation = matricesTransformation;
    }
}
