package Math.Tasks;

import java.util.function.Function;

public class UnaryMathTask extends MathTask<Float[]> {
    float[] vector_a;
    Function<Float, Float> func;

    public UnaryMathTask(float[] vector_a, Function<Float, Float> func) {
        super(vector_a);
        this.func = func;
    }

    UnaryMathTask(float[] vector_a, Function<Float, Float> func, int low, int high) {
        this(vector_a, func);
        this.low = low;
        this.high = high;
    }

    @Override
    protected UnaryMathTask createSubTask(int low, int high) {
        return new UnaryMathTask(vector_a, func, low, high);
    }

    @Override
    Float[] computeDirectly() {
        Float[] result = new Float[high - low];
        for (int i = 0; i < result.length; ++i) {
            result[i] = func.apply(vector_a[i]);
        }

        return result;
    }

    @Override
    Float[] combineResults(Float[] left, Float[] right) {
        Float[] combined = new Float[left.length + right.length];
        System.arraycopy(left, 0, combined, 0, left.length);
        System.arraycopy(right, 0, combined, left.length, right.length);
        return combined;
    }
}
