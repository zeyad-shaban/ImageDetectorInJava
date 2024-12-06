package Math;

abstract public class Math {
    public static float E = 2.718281828459045f;

    public static float Max(float a, float b) {
        if (a > b)
            return a;
        return b;
    }

    public static float[] concatArr(float[] left, float[] right) {
        float[] combined = new float[left.length + right.length];
        System.arraycopy(left, 0, combined, 0, left.length);
        System.arraycopy(right, 0, combined, left.length, right.length);
        return combined;
    }

    public static float exp(float x) {
        float result = 1.0f;
        float term = 1.0f;

        for (int i = 1; i <= 10; ++i) {
            term *= x / i;
            result += term;
        }

        return result;
    }
}