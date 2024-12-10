package NeuralNetwork.Math.Tasks.BiMathTask;

import java.util.function.BiFunction;
import NeuralNetwork.Math.Math;

public class VectorBiMathTask extends BiMathTask<float[]> {
    public VectorBiMathTask(float[] vector_a, float[] vector_b, BiFunction<Float, Float, Float> func) {
        super(vector_a, vector_b, func);
    }

    VectorBiMathTask(float[] vector_a, float[] vector_b, BiFunction<Float, Float, Float> func, int low, int high) {
        super(vector_a, vector_b, func);
    }

    @Override
    protected VectorBiMathTask createSubTask(int low, int high) {
        return new VectorBiMathTask(vector_a, vector_b, func, low, high);
    }

    @Override
    protected float[] computeDirectly() {
        float[] result = new float[vector_a.length];
        for (int i = 0; i < vector_a.length; ++i) {
            result[i] = func.apply(vector_a[i], vector_b[i]);
        }

        return result;
    }

    @Override
    protected float[] combineResults(float[] left, float[] right) {
        return Math.concatArr(left, right);
    }
}