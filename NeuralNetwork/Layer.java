package NeuralNetwork;

import Math.VectorizedMath.VectorizedMath;
import NeuralNetwork.Activations.Activation;

// import Network.Activation.Activation;

public class Layer {
    private float[][] weights;
    private float[] bias;
    private Activation activation;

    public Layer(float[][] weights, float[] bias, Activation activation) {
        this.weights=  weights;
        this.bias = bias;
        this.activation = activation;
    }

    public float[] compute(float[] input) {
        float[] output = new float[weights.length];

        // Apply weights
        for (int i = 0; i < output.length; ++i)
            output[i] = VectorizedMath.dotProduct(input, weights[i]);

        // Apply bias
        output = VectorizedMath.element_wise_addition(output, bias);

        // Apply activation function
        output = activation.compute(output);

        return output;
    }
}
