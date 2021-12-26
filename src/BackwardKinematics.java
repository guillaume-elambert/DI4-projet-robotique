import Jama.Matrix;

public class BackwardKinematics {

    public static void main(){
                                  // x, y
        double[] goal = new double[]{4, 2};

        // θ, d, α, a
        double[][] variables = new double[4][4];


        double[][] position = new double[][]{
        //       x,  y,  z
                {0,  0,  0},
                {1,  2,  3},
                {4,  5,  6},
                {9,  10, 15}
        };



        // θ2
        variables[1][0] = Math.atan2(position[1][1], position[1][0]);

        //d3
        variables[2][1] = Math.cos(position[2][0]*position[2][0]+position[2][1]+position[2][1]) - variables[2][3] - variables[3][3];

        Matrix rot_mat_0_6, inv_rot_mat_0_3, rot_mat_3_6;

        rot_mat_0_6 = new Matrix(new double[][]{
            {-1.0, 0.0, 0.0},
            {0.0, -1.0, 0.0},
            {0.0, 0.0, 1.0}
        });

        inv_rot_mat_0_3 = ImplementationGreville.greville(rot_mat_0_6);
        rot_mat_3_6 = inv_rot_mat_0_3.times(rot_mat_0_6);

        Math.acos(rot_mat_3_6.get(2,2));

    }
}
