import NeuralNetwork.Layer.Layer;
import NeuralNetwork.*;

public class Main {
    public static void main(String[] args) {
        float[] input = new float[28 * 28];

        float[][] weights = {
                { 4, 5, 20 },
                { 10, 8, 9 },
        };
        float[] bias = { 8, 30 };

        NeuralNetwork model = new NeuralNetwork(
                // todo flatten
                new Layer(28 * 28, 256, "relu"),
                new Layer(256, 128, "relu"),
                new Layer(128, 32, "relu"),
                new Layer(32, 10, "relu")
                );

        model.loadParamsFromJson("../notebook/model_params.json");

        float[] result = model.forward(input);

        System.out.println(result);
    }
}