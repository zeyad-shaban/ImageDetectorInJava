package NeuralNetwork.Math.VectorizedMath;

import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.function.BiFunction;

import NeuralNetwork.Math.Tasks.BiMathTask.ScalarBiMathTask;
import NeuralNetwork.Math.Tasks.BiMathTask.VectorBiMathTask;

import NeuralNetwork.Math.Tasks.UnaryMathTask.ScalarUnaryMathTask;
import NeuralNetwork.Math.Tasks.UnaryMathTask.VectorUnaryMathTask;

import NeuralNetwork.Math.Math;

public abstract class VectorizedMath {
  private static ForkJoinPool pool = new ForkJoinPool();

  private static final BiFunction<Float, Float, Float> DOT_PRODUCT_FUNC = (a, b) -> a * b;
  private static final BiFunction<Float, Float, Float> ADD_BIAS_FUNC = (a, b) -> a + b;

  private static final Function<Float, Float> RELU_ACTIVATION_FUNC = (a) -> Math.Max(a, 0.0f);
  private static final Function<Float, Float> EXP_FUNC = (a) -> Math.exp(a);
  private static final Function<Float, Float> SUM_FUNC = (a) -> a;

  public static float dotProduct(float[] vector_a, float[] vector_b) {
    ScalarBiMathTask task = new ScalarBiMathTask(vector_a, vector_b, DOT_PRODUCT_FUNC);
    return pool.invoke(task);
  }

  public static float[] element_wise_addition(float[] vector_a, float[] vector_b) {
    VectorBiMathTask task = new VectorBiMathTask(vector_a, vector_b, ADD_BIAS_FUNC);
    return pool.invoke(task);
  }

  public static float[] relu(float[] vector_a) {
    VectorUnaryMathTask task = new VectorUnaryMathTask(vector_a, RELU_ACTIVATION_FUNC);
    return pool.invoke(task);
  }

  public static float[] exp(float[] vector_a) {
    VectorUnaryMathTask task = new VectorUnaryMathTask(vector_a, EXP_FUNC);
    return pool.invoke(task);
  }

  public static float sum(float[] vector_a) {
    ScalarUnaryMathTask task = new ScalarUnaryMathTask(vector_a, SUM_FUNC);
    return pool.invoke(task);
  }

  public static float[] divide(float[] vector_a, float dividor) {
    Function<Float, Float> div_func = (a) -> a / dividor;
    VectorUnaryMathTask task = new VectorUnaryMathTask(vector_a, div_func);
    return pool.invoke(task);
  }

  public static float[] subtract(float[] vector_a, float subtractor) {
    Function<Float, Float> div_func = (a) -> a - subtractor;
    VectorUnaryMathTask task = new VectorUnaryMathTask(vector_a, div_func);
    return pool.invoke(task);
  }

  public static float max(float[] vector_a) {
    if (vector_a.length <= 0)
      throw new IllegalArgumentException("vector_a is empty");

    // Todo vectorize
    float max = vector_a[0];
    for (int i = 1; i < vector_a.length; ++i)
      if (vector_a[i] > max)
        max = vector_a[i];

    return max;
  }

}