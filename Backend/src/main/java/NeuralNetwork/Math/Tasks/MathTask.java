package NeuralNetwork.Math.Tasks;

import java.util.concurrent.RecursiveTask;

public abstract class MathTask<T> extends RecursiveTask<T> {
    private float[] vector_a;
    public float[] getVectorA() {return vector_a;}
    public void setVectorA(float[] vector_a) {this.vector_a = vector_a;}

    private int low;
    public int getLow() {return low;}
    public void setLow(int low) {this.low = low;}
    
    private int high;
    public int getHigh() {return high;}
    public void setHigh(int high) {this.high = high;}

    private int MIN_THRESH;
    public int getMinThresh() {return MIN_THRESH;}
    public void setMinThresh(int minThresh) {this.MIN_THRESH = minThresh;}

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
