package NeuralNetwork.Math.Tasks.UnaryMathTask;
import NeuralNetwork.Math.Math;
import java.util.function.Function;

public class VectorUnaryMathTask extends UnaryMathTask<float[]> {
    public VectorUnaryMathTask(float[] vector_a, Function<Float, Float> func) {
        super(vector_a, func);
    }

    VectorUnaryMathTask(float[] vector_a, Function<Float, Float> func, int low, int high) {
        super(vector_a, func, low, high);
    }

    @Override
    protected VectorUnaryMathTask createSubTask(int low, int high) {
        return new VectorUnaryMathTask(vector_a, func, low, high);
    }

    @Override
    protected float[] computeDirectly() {
        float[] result = new float[this.vector_a.length];
        for (int i = 0; i < this.vector_a.length; ++i)
            result[i] += func.apply(this.vector_a[i]);

        return result;
    }

    @Override
    protected float[] combineResults(float[] left, float[] right) {
        return Math.concatArr(left, right);
    }
}