import Jama.Matrix;


public class ImplementationGreville {
	
	static Matrix matA;
	
	public static void main(String[] args) {
		
		matA = new Matrix(new double[][] {
			{1, 0, 0},
			{0, 2, 0},
			{0, 0, 4}
		});
		
		
		greville(matA);
		
		
		matA = new Matrix(new double[][] {
			{1, 4, 2},
			{3, 1, 7}
		});
		
		greville(matA);
	}
	
	
	static Matrix greville(Matrix matA) {
		Matrix matAPlus, matAk, matAPlusk, matNulle, colNulle, ligneNulle, transpose, a, b, c, d, tmp;
		int n, m;
		

		n = matA.getRowDimension();
		m = matA.getColumnDimension();
		
		matAPlus = new Matrix(n, m);
		matNulle = new Matrix(n, m);
		colNulle = new Matrix(n, 1);
		ligneNulle = new Matrix(1, m);
		
		
		/*======== 1ere iteration ========*/
		a = matA.getMatrix(0, n-1, 0, 0);
		if(!a.equals(new Matrix(a.getRowDimension(), 1))) {
			transpose = a.transpose();
			matAPlusk = transpose.times(a).inverse().times(transpose);
			matAPlus.setMatrix(0, 0, 0, m-1, matAPlusk);
		}
		
		double dDouble;
		int tailleMat;
		
		for(int k = 1; k < m; ++k) {
			
			matAk = matA.getMatrix(0, n-1, 0, k-1);
			matAPlusk = matAPlus.getMatrix(0, k-1, 0, m-1);
			
			
			a = matA.getMatrix(0, n-1, k, k);
			d = matAPlusk.times(a);
			
			
			//Entrée : d est un chiffre et non une matrice
			if(d.getRowDimension() == 1 && d.getColumnDimension() == 1) {
				dDouble = d.get(0, 0);
				c = a.minus(matAk.times(dDouble));
			} else {
				c = a.minus(matAk.times(d));
			}

			
			//Entrée : c'est une matrice nulle
			if(c.equals(new Matrix(c.getRowDimension(), 1))) {
				transpose = d.transpose();
				b = Matrix.identity(n, m).plus(transpose.times(d));
				b = b.inverse().times(transpose).times(matAPlusk); 
			} else {
				transpose = c.transpose();
				b = transpose.times(c).inverse().times(transpose);
			}
			
			matAPlusk = matAPlusk.minus(d.times(b));
			tailleMat = matAPlusk.getRowDimension();
			
			
			matAPlus.setMatrix(0, tailleMat-1, 0, m-1, matAPlusk);
			matAPlus.setMatrix(tailleMat, tailleMat, 0, m-1, b);
			
			System.out.println("\n\nIt�ration "+(k+1));
			System.out.print("\nA+k:");
			matAPlusk.print(0, 5);
			System.out.print("b:");
			b.print(0, 5);
			System.out.print("A+:");
			matAPlus.print(0, 5);
		}
		
		return matAPlus;
	}
	
}
