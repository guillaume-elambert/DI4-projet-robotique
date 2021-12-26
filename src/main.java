import Jama.Matrix;

import java.util.ArrayList;

public class main {

    public static void main(String[] args) {

        Position base = new Position(0, 0, 0);
        ArrayList<Articulation> articulations = new ArrayList<>();
        Robot robot;

        //double[] target = { 0.8606601717798215, -0.2442706013342191, 2.060660171779821 };

        articulations.add(new Articulation(
                new ParametresDenavit(0, 1, 0, 0),
                new Position(),
                Articulation.Type.ROTATION
        ));

        articulations.add(new Articulation(
                new ParametresDenavit(135, 0, 90, -0.2),
                new Position(),
                Articulation.Type.ROTATION
        ));

        articulations.add(new Articulation(
                new ParametresDenavit(0, 1.5, 90, 0),
                new Position(),
                Articulation.Type.TRANSLATION
        ));

/*
        articulations.add(new Articulation(
                new ParametresDenavit(45,0,0,1),
                new Position(),
                Articulation.Type.ROTATION
        ));

        articulations.add(new Articulation(
                new ParametresDenavit(90,0,0,1),
                new Position(),
                Articulation.Type.ROTATION
        ));*/


        robot = new Robot(base, articulations);

        Matrix[] results = robot.getMatricesTransformation();

       for (Matrix T : results) {
            T.print(10,10);
       }
    }
}
