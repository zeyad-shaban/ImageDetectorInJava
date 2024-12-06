import NeuralNetwork.Layer;
import NeuralNetwork.Activations.*;
import Math.VectorizedMath.VectorizedMath;

public class Main {
    public static void main(String[] args) {
        float[] input = { 1, 2, 3 };

        float[][] weights = {
                { 4, 5, 20 },
                { 10, 8, 9 },
        };
        float[] bias = { 8, 30 };

        Layer layer1 = new Layer(weights, bias, new Softmax());

        float[] result = layer1.compute(input);

        System.out.println(result);
    }
}