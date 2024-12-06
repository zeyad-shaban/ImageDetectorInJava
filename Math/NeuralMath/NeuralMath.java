package Math.NeuralMath;

import Math.VectorizedMath.VectorizedMath;

public abstract class NeuralMath {
    public static float[] computeHiddenLayer(float[] input, float[][] weights, float[] bias) {
        /*
         * input shape "dims"
         * weights shape: "Whatever x dims"
         */

        float[] output = new float[weights.length];

        for (int i = 0; i < output.length; ++i) {
            output[i] = VectorizedMath.vectorizedDotProduct(input, weights[i]);
        }

        return output;
    }
}
