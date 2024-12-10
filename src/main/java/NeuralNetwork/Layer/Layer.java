package NeuralNetwork.Layer;

import NeuralNetwork.Math.VectorizedMath.VectorizedMath;
import NeuralNetwork.Activations.Activation;
import NeuralNetwork.Activations.BatchNormalization1d;
import NeuralNetwork.Activations.ReLU;
import NeuralNetwork.Activations.Softmax;

public class Layer {
    private float[][] weights;
    private float[] bias;
    private Activation[] activations;

    public Layer(int in_dimensions, int out_dimensions, String... activationStrs) {
        this.weights = new float[out_dimensions][in_dimensions];
        this.bias = new float[out_dimensions];

        this.activations = new Activation[activationStrs.length];

        for (int i = 0; i < activations.length; ++i) {
            this.activations[i] = getActivationFromStr(activationStrs[i]);
        }
    }

    public void load_params(float[][] weights, float[] bias) {
        this.weights = weights;
        this.bias = bias;
    }

    public float[] compute(float[] input) {
        float[] output = new float[weights.length];

        // Apply weights
        for (int i = 0; i < output.length; ++i)
            output[i] = VectorizedMath.dotProduct(input, weights[i]);

        // Apply bias
        output = VectorizedMath.element_wise_addition(output, bias);

        // Apply activations function
        for (Activation activation : activations) {
            output = activation.compute(output);
        }

        return output;
    }

    // todo see where to put this
    private Activation getActivationFromStr(String activationStr) {
        activationStr = activationStr.toLowerCase();

        if (activationStr == "relu")
            return new ReLU();
        else if (activationStr == "softmax")
            return new Softmax();
        else if (activationStr == "batchnorm1d")
            return new BatchNormalization1d();
        else
            throw new IllegalArgumentException("Undefined activation string");
    }
}