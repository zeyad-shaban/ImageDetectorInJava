package NeuralNetwork.Math.Tasks;

import java.util.concurrent.RecursiveTask;

public abstract class MathTask<T> extends RecursiveTask<T> {
    protected float[] vector_a;
    protected int low;
    protected int high;
    protected int MIN_THRESH;

    public MathTask(float[] vector_a) {
        this.vector_a = vector_a;
        // todo fix this shit
        MIN_THRESH = vector_a.length / 1; // vector_a.length / (Runtime.getRuntime().availableProcessors() * 2);
        if (MIN_THRESH <= 10)
            MIN_THRESH = 10;

        low = 0;
        high = vector_a.length;
    }

    protected MathTask(int low, int high) {
        this.low = low;
        this.high = high;
    }

    // @Override
    protected T compute() {
            if (high - low <= MIN_THRESH) {
                return computeDirectly();
            } else {
                int mid = (high + low) / 2;
                MathTask<T> leftTask = createSubTask(low, mid);
                MathTask<T> rightTask = createSubTask(mid, high);
    
                leftTask.fork();
                T rightResult = rightTask.compute();
                T leftResult = leftTask.join();
    
                return combineResults(leftResult, rightResult);
            }
    }

    protected abstract MathTask<T> createSubTask(int low, int high);
    protected abstract T computeDirectly();
    protected abstract T combineResults(T left, T right);
}
