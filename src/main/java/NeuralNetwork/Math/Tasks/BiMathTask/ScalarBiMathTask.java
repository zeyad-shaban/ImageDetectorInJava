package NeuralNetwork.Math.Tasks.BiMathTask;

import java.util.function.BiFunction;

public class ScalarBiMathTask extends BiMathTask<Float> {
    public ScalarBiMathTask(float[] vector_a, float[] vector_b, BiFunction<Float, Float, Float> func) {
        super(vector_a, vector_b, func);
    }

    ScalarBiMathTask(float[] vector_a, float[] vector_b, BiFunction<Float, Float, Float> func, int low, int high) {
        super(vector_a, vector_b, func);
    }

    @Override
    protected ScalarBiMathTask createSubTask(int low, int high) {
        return new ScalarBiMathTask(vector_a, vector_b, func, low, high);
    }

    @Override
    protected Float computeDirectly() {
        Float result = 0.0f;
        for (int i = 0; i < vector_a.length; ++i) {
            result += func.apply(vector_a[i], vector_b[i]);
        }

        return result;
    }

    @Override
    protected Float combineResults(Float left, Float right) {
        return left + right;
    }
}