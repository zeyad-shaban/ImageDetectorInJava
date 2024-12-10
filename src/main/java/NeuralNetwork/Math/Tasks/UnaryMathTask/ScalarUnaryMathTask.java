package NeuralNetwork.Math.Tasks.UnaryMathTask;

import java.util.function.Function;

public class ScalarUnaryMathTask extends UnaryMathTask<Float> {
    public ScalarUnaryMathTask(float[] vector_a, Function<Float, Float> func) {
        super(vector_a, func);
    }

    ScalarUnaryMathTask(float[] vector_a, Function<Float, Float> func, int low, int high) {
        super(vector_a, func, low, high);
    }

    @Override
    protected ScalarUnaryMathTask createSubTask(int low, int high) {
        return new ScalarUnaryMathTask(vector_a, func, low, high);
    }

    @Override
    protected Float computeDirectly() {
        Float result = 0.0f;
        for (int i = 0; i < vector_a.length; ++i) {
            result += func.apply(vector_a[i]);
        }

        return result;
    }

    @Override
    protected Float combineResults(Float left, Float right) {
        return left + right;
    }
}