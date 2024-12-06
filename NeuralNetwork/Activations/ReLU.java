package NeuralNetwork.Activations;
import Math.VectorizedMath.VectorizedMath;;

public class ReLU extends Activation {
    @Override
    public float[] compute(float[] in) {
        return VectorizedMath.relu(in);
    }
}
