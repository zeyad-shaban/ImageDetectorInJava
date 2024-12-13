package NeuralNetwork.Math.Tasks.BiMathTask;

import java.util.function.BiFunction;

import NeuralNetwork.Math.Tasks.MathTask;

abstract class BiMathTask<T> extends MathTask<T> {
    float[] vector_b;
    BiFunction<Float, Float, Float> func;

    public BiMathTask(float[] vector_a, float[] vector_b, BiFunction<Float, Float, Float> func) {
        super(vector_a);
        this.vector_b = vector_b;

        if (vector_a.length != vector_b.length)
            throw new IllegalArgumentException(
                    "expected vector_b to be of shape " + vector_a.length + ", instead got " + vector_b.length);

        this.func = func;
    }

    BiMathTask(float[] vector_a, float[] vector_b, BiFunction<Float, Float, Float> func, int low, int high) {
        this(vector_a, vector_b, func);
        this.low = low;
        this.high = high;
    }
}