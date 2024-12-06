package Math.Tasks;

public class DotProductTask extends MathTask {
  public DotProductTask(float[] vector_a, float[] vector_b) {
    super(vector_a, vector_b);
  }

  DotProductTask(float[] vector_a, float[] vector_b, int low, int high) {
    super(vector_a, vector_b, low, high);
  }

  DotProductTask createSubTask(float[] vector_a, float[] vector_b, int low, int high) {
    return new DotProductTask(vector_a, vector_b, low, high);
  }

  Float computeDirectly() {
    float result = 0.0f;
    for (int i = low; i < high; ++i) {
      result += vector_a[i] * vector_b[i];
    }
    return result;
  }
}