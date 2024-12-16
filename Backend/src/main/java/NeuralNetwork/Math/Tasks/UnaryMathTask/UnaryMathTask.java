package NeuralNetwork.Math.Tasks.UnaryMathTask;

import java.util.function.Function;
import NeuralNetwork.Math.Tasks.MathTask;

public abstract class UnaryMathTask<T> extends MathTask<T> {
    Function<Float, Float> func;

    public UnaryMathTask(float[] vector_a, Function<Float, Float> func) {
        super(vector_a);
        this.func = func;
    }

    UnaryMathTask(float[] vector_a, Function<Float, Float> func, int low, int high) {
        this(vector_a, func);
        this.setLow(low);
        this.setHigh(high);
    }
}