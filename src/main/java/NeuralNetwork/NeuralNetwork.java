package NeuralNetwork;

import NeuralNetwork.Layer.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NeuralNetwork {
    public Layer[] layers;

    public NeuralNetwork(Layer... layers) {
        this.layers = new Layer[layers.length];

        for (int i = 0; i < layers.length; ++i) {
            this.layers[i] = layers[i]; // todo deep copy layer
        }
    }

    public boolean loadParamsFromJson(String path) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(path)));
            JSONArray layersData = new JSONArray(jsonContent);

            for (int i = 0; i < this.layers.length; i++) {
                Layer layer = this.layers[i];

                JSONObject layerData = layersData.getJSONObject(i);
                JSONArray weightsArray = layerData.getJSONArray("weights");
                JSONArray biasesArray = layerData.getJSONArray("biases");

                float[][] weights = new float[weightsArray.length()][];
                for (int j = 0; j < weightsArray.length(); j++) {
                    JSONArray row = weightsArray.getJSONArray(j);
                    weights[j] = new float[row.length()];
                    for (int k = 0; k < row.length(); k++) {
                        weights[j][k] = row.getFloat(k);
                    }
                }

                float[] biases = new float[biasesArray.length()];
                for (int j = 0; j < biasesArray.length(); j++) {
                    biases[j] = biasesArray.getFloat(j);
                }

                layer.load_params(weights, biases);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public float[] forward(float[] input) {
        float[] output = input;

        for (Layer layer : layers) {
            output = layer.compute(output);
        }

        return output;
    }

    public float[] forward(float[][] input) {
        float[] input_flattened = new float[input.length * input[0].length];

        for (int i = 0; i < input.length; ++i)
            for (int j = 0; j < input[0].length; ++j)
                input_flattened[i * input.length + j] = input[i][j];

        return forward(input_flattened);
    }
}
