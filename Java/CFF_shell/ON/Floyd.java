/**
 *@Project  :   cff_shell
 *@Package  :   ON
 *@Revision :   1.0.1
 *@Created  :   17/03/2017 15:37
 *@Author   :   Frederick NEY and Stephane OVERLEN
 */

package ON;

// import Objects
import java.util.List;
import ON.Geography.Map;

/**
 * <h1>Class used for calculating shortest path between a source and a target.</h1>
 * <a>Class used by the Main class.</a>
 * <h2>Long description</h2>
 * <p>It can be used without creating an new instance of this class.</p>
 * <h3>Author(s)</h3>
 * <p>Frederick NEY and Stephane OVERLEN</p>
 * <p><b>version</b> : 1.201</p>
 */
public class Floyd {

    public static int [][] generateMatrix(Map virtualMap , boolean swap) {
        int i, j, k, weightij;
        int [][] matrix = new int[virtualMap.size()][virtualMap.size()];
        int [][] pred = new int[virtualMap.size()][virtualMap.size()];
        for(i = 0; i < virtualMap.size(); i++){
            for(j = 0; j < virtualMap.size(); j++){
                matrix[i][j] = Integer.MAX_VALUE;           // initialize the matrix with "max value"
                pred[i][j] = Integer.MAX_VALUE;             // initialize the matrix with "max value"
            }
        }
        List<String> cities = virtualMap.getCitiesList();
        for(i = 0; i < virtualMap.size(); i++) {
            for (j = 0; j < virtualMap.size(); j++) {
                matrix[i][j] = virtualMap.getWeight(cities.get(i), cities.get(j));
                if (matrix[i][j] != 0 && matrix[i][j] != Integer.MAX_VALUE) { //if the value in the matrix is not 0 or inf
                    pred[i][j] = i;                 // current box = i + 1
                }
            }
        }
        for(k = 0; k < virtualMap.size(); k++){
            matrix [k][k] = 0;
            pred [k][k] = -1;
            for(i = 0; i < virtualMap.size(); i++){
                for(j = 0; j < virtualMap.size(); j++){
                    if ((matrix[i][k] + matrix[k][j]) < matrix[i][j] && (matrix[i][k] < Integer.MAX_VALUE && matrix[k][j] < Integer.MAX_VALUE)) {
                        matrix[i][j] = matrix[i][k] + matrix[k][j];
                        pred[i][j] = pred[k][j];
                    }
                }
            }
        }
        return swap ? pred : matrix;
    }

    /**
     * Function to print content of the matrix.
     *
     * @param matrix (int matrix): Matrix array to print
     */
    public static void printMatrix(int [][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] == Integer.MAX_VALUE ? "inf" : matrix[i][j]);
                System.out.print(j == matrix.length -1 ? "" : " ");
            }
            System.out.println();
        }
    }
}
