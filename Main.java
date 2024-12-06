import Math.VectorizedMath.VectorizedMath;
import Math.NeuralMath.NeuralMath;

public class Main {
    public static void main(String[] args) {
        float[] vector_a = {1,2,3};
        float[][] vector_b = {
            {4, 5, 6},
            {7, 8, 9},
        };
        // todo add bias

        float[] result = NeuralMath.computeHiddenLayer(vector_a, vector_b);

        System.out.println(result);
    }
}