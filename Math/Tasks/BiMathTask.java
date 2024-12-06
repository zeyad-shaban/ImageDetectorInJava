package Math.Tasks;

import java.util.function.BiFunction;

public class BiMathTask extends MathTask<Float> {
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

    @Override
    BiMathTask createSubTask(int low, int high) {
        return new BiMathTask(vector_a, vector_b, func, low, high);
    }

    Float computeDirectly() {
        Float result = 0.0f;
        for (int i = 0; i < vector_a.length; ++i) {
            result += func.apply(vector_a[i], vector_b[i]);
        }

        return result;
    }

    Float combineResults(Float left, Float right) {
        return left + right;
    }
}