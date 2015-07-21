package com.grichardson.audioeffects;

import org.jtransforms.fft.FloatFFT_1D;

public class Util {

    public static float[] Convolve(float[] x, float[] h) {

        int outputLength = x.length + h.length - 1;

        float[] xPadded = Concat(x, Zeros(outputLength - x.length));
        float[] hPadded = Concat(h, Zeros(outputLength - h.length));

        float[] X = PerformFFT(xPadded);
        float[] H = PerformFFT(hPadded);

        float[] Y = MultiplyComplex(X, H);

        float[] y = PerformInverseFFT(Y);

        float maxValue = Max(y);
        float[] yNormalized = Normalize(y, maxValue);

        return yNormalized;
    }

    public static float[] PerformFFT(float[] input) {

        FloatFFT_1D fft1d = new FloatFFT_1D(input.length);

        float[] fft = new float[input.length * 2];

        for (int i = 0; i < input.length; i++) {

            //Even elements are real, odd elements are imaginary
            fft[2*i] = input[i];
            fft[2*i+1] = 0.0f;
        }
        fft1d.complexForward(fft);

        return fft;
    }

    public static float[] PerformInverseFFT(float[] fft) {

        FloatFFT_1D fft1d = new FloatFFT_1D(fft.length / 2);

        fft1d.complexInverse(fft, true);

        float[] signal = new float[fft.length / 2];

        for (int i = 0; i < signal.length; i++) {

            //Even elements are real, odd elements are imaginary
            //We want to only take the real part (i+=2)

            signal[i] = fft[2*i];
        }

        return signal;
    }

    public static float Max(float[] signal) {

        float max = 0;

        for (int i = 0; i < signal.length; i++) {

            float value = Math.abs(signal[i]);

            if (value > max) {
                max = value;
            }
        }

        return max;
    }

    private static float[] Normalize(float[] a, float max) {

        float[] output = new float[a.length];

        for (int i = 0; i < output.length; i++) {
            output[i] = a[i] / max;
        }

        return output;
    }

    public static float[] ShortToFloat(short[] x) {

        float[] output = new float[x.length];

        for (int i = 0; i < output.length; i++) {
            output[i] = (float) x[i] / (float) Short.MAX_VALUE;
        }

        return output;
    }

    public static short[] FloatToShort(float[] x) {

        short[] output = new short[x.length];

        for (int i = 0; i < output.length; i++) {
            output[i] = (short) (x[i] * Short.MAX_VALUE);
        }

        return output;
    }

    private static float[] MultiplyComplex(float[] a, float[] b) {

        float[] output = new float[a.length];

        for (int i = 0; i < output.length; i+=2) {

            // (a1 + a2*i) * (b1 + b2*i)
            //
            // a1 = a[i], a2 = a[i+1], b1 = b[i], b2 = b[i+1]

            float real1 = a[i] * b[i];
            float imaginary1 = a[i] * b[i+1];
            float imaginary2 = a[i+1] * b[i];
            float real2 = - a[i+1] * b[i+1];

            output[i] = real1 + real2;
            output[i+1] = imaginary1 + imaginary2;
        }

        return output;
    }

    public static float[] Zeros(int length) {

        float[] output = new float[length];

        for (int i = 0; i < output.length; i++) {
            output[i] = 0;
        }

        return output;
    }

    public static short[] Concat(short[] a, short[] b) {
        int aLen = a.length;
        int bLen = b.length;
        short[] c = new short[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    public static float[] Concat(float[] a, float[] b) {
        int aLen = a.length;
        int bLen = b.length;
        float[] c = new float[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
}
