package cubex2.cs3.util;

public class MathUtil
{
    public static float[] normalize(float[] v)
    {
        float r;

        r = (float) Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
        if (r == 0.0)
            return v;

        r = 1.0f / r;

        v[0] *= r;
        v[1] *= r;
        v[2] *= r;

        return v;
    }

    public static void cross(float[] v1, float[] v2, float[] result)
    {
        result[0] = v1[1] * v2[2] - v1[2] * v2[1];
        result[1] = v1[2] * v2[0] - v1[0] * v2[2];
        result[2] = v1[0] * v2[1] - v1[1] * v2[0];
    }

    public static void scale(float[] v, float scale)
    {
        v[0] *= scale;
        v[1] *= scale;
        v[2] *= scale;
    }

    public static void rotateY(float[] v, float a)
    {
        float sinA = (float) Math.sin(Math.toRadians(a));
        float cosA = (float) Math.cos(Math.toRadians(a));

        float[] res = new float[3];
        res[0] = v[0] * cosA + v[2] * sinA;
        res[1] = v[1];
        res[2] = v[0] * (-sinA) + v[2] * cosA;

        v[0] = res[0];
        v[1] = res[1];
        v[2] = res[2];
    }

    public static void rotateLine(float[] v, float a, float n1, float n2, float n3)
    {
        float sinA = (float) Math.sin(Math.toRadians(a));
        float cosA = (float) Math.cos(Math.toRadians(a));

        float[] res = new float[3];
        res[0] = (n1 * n1 * (1 - cosA) + cosA) *      v[0] + (n1 * n2 * (1 - cosA) - n3 * sinA) * v[1] + (n1 * n3 * (1 - cosA) + n2 * sinA) * v[2];
        res[1] = (n2 * n1 * (1 - cosA) + n3 * sinA) * v[0] + (n2 * n2 * (1 - cosA) + cosA) *      v[1] + (n2 * n3 * (1 - cosA) - n1 * sinA) * v[2];
        res[2] = (n3 * n1 * (1 - cosA) - n2 * sinA) * v[0] + (n3 * n2 * (1 - cosA) + n1 * sinA) * v[1] + (n3 * n3 * (1 - cosA) + cosA) * v[2];

        v[0] = res[0];
        v[1] = res[1];
        v[2] = res[2];
    }
}
