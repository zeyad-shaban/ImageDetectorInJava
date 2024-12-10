package NeuralNetwork.Activations;

import NeuralNetwork.Math.VectorizedMath.VectorizedMath;

public class Softmax extends Activation {
    @Override
    public float[] compute(float[] in) {
        float max = VectorizedMath.max(in);
        float[] exponent_in = VectorizedMath.exp(VectorizedMath.subtract(in, max));
        float sum_exponents = VectorizedMath.sum(exponent_in);
        float[] output = VectorizedMath.divide(exponent_in, sum_exponents);

        return output;
    }
}
