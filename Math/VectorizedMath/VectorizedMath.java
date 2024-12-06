package Math.VectorizedMath;

import java.util.concurrent.ForkJoinPool;
import Math.Tasks.DotProductTask;
import java.util.function.Function;
import java.util.function.BiFunction;
import Math.Tasks.BiMathTask;
import Math.Tasks.UnaryMathTask;
import Math.Math;

public abstract class VectorizedMath {
  private static ForkJoinPool pool = new ForkJoinPool();

  private static final BiFunction<Float, Float, Float> DOT_PRODUCT_FUNC = (a, b) -> a * b;
  private static final BiFunction<Float, Float, Float> ADD_BIAS_FUNC = (a, b) -> a + b;
  private static final Function<Float, Float> RELU_ACTIVATION_FUNC = (a) -> Math.Max(a, 0.0f);

  public static float dotProduct(float[] vector_a, float[] vector_b) {
    BiMathTask task = new BiMathTask(vector_a, vector_b, DOT_PRODUCT_FUNC);
    return pool.invoke(task);
  }
  public static float addBias(float[] vector_a, float[] vector_b) {
    BiMathTask task = new BiMathTask(vector_a, vector_b, ADD_BIAS_FUNC);
    return pool.invoke(task);
  }
  public static Float[] relu(float[] vector_a) {
    UnaryMathTask task = new UnaryMathTask(vector_a, RELU_ACTIVATION_FUNC);
    return pool.invoke(task);
  }
}