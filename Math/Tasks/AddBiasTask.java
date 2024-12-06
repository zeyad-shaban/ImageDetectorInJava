package Math.Tasks;

public class AddBiasTask extends MathTask {
    public AddBiasTask(float[] vector_a, float[] vector_b) {
        super(vector_a, vector_b);
    }

    AddBiasTask(float[] vector_a, float[] vector_b, int low, int high) {
        super(vector_a, vector_b, low, high);
    }

    AddBiasTask createSubTask(float[] vector_a, float[] vector_b, int low, int high) {
        return new AddBiasTask(vector_a, vector_b, low, high);
    }

    Float computeDirectly() {
        float result = 0.0f;
        for (int i = low; i < high; ++i) {
            result += vector_a[i] + vector_b[i];
        }
        return result;
    }
}