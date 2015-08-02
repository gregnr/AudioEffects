package com.grichardson.audioeffects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Effects {

    public static float[] Vibrato(float[] x, int fs, float delay, float depth, float modulationFrequency) {
        return FractionalDelay(x, fs, 0f, 1f, 0f, delay, depth, modulationFrequency);
    }

    public static float[] Flanger(float[] x, int fs, float delay, float depth, float modulationFrequency) {
        return FractionalDelay(x, fs, 0.7f, 0.7f, 0.7f, delay, depth, modulationFrequency);
    }

    public static float[] Chorus(float[] x, int fs, float delay, float depth) {

        float[] lowPassNoise = Util.LowPassFilter(Effects.GenerateGaussianNoise(0, 0.707f, x.length), 5000);

        return Chorus(x, fs, delay, depth, lowPassNoise);
    }

    public static float[] Chorus(float[] x, int fs, float delay, float depth, float[] modulationSignal) {
        return FractionalDelay(x, fs, 0.7f, 1f, -0.7f, delay, depth, modulationSignal);
    }

    public static float[] Doubling(float[] x, int fs, float delay, float depth) {

        float[] lowPassNoise = Util.LowPassFilter(Effects.GenerateGaussianNoise(0, 0.707f, x.length), 5000);

        return Doubling(x, fs, delay, depth, lowPassNoise);
    }

    public static float[] Doubling(float[] x, int fs, float delay, float depth, float[] modulationSignal) {
        return FractionalDelay(x, fs, 0.7f, 0.7f, 0f, delay, depth, modulationSignal);
    }

    private static float[] FractionalDelay(float[] x, int fs, float BL, float FF, float FB, float delay, float depth, float modulationFrequency) {

        float[] modulationSignal = GenerateSineWave(modulationFrequency, fs, x.length);

        return FractionalDelay(x, fs, BL, FF, FB, delay, depth, modulationSignal);
    }

    private static float[] FractionalDelay(float[] x, int fs, float BL, float FF, float FB, float delay, float depth, float[] modulationSignal) {

        float[] y = new float[x.length];

        int delaySamples = Math.round(delay * fs); // basic delay in # samples
        int depthSamples = Math.round(depth * fs); // modulation depth (width) in # samples

        int delayLineSamples = 2 + delaySamples + depthSamples*2; // length of the entire delay

        ArrayList<Float> delayLine = new ArrayList<Float>(Collections.nCopies(delayLineSamples, (float) 0));

        for (int i = 0; i < x.length; i++) {

            float modulationValue = depthSamples * modulationSignal[i];

            float pointer = delaySamples + modulationValue;
            int roundedPointer = (int) Math.floor(pointer);
            float frac = pointer - roundedPointer;

            //Linear interpolation
            float fracDelay = delayLine.get(roundedPointer + 1) * frac + delayLine.get(roundedPointer) * (1f - frac);

            float xh = x[i] + FB*fracDelay;
            y[i] = FF*fracDelay + BL*xh;

            delayLine.remove(delayLine.size() - 1);
            delayLine.add(0, xh);
        }

        return y;
    }

    public static float[] GenerateGaussianNoise(float mean, float variance, int sampleFrequency, float duration) {

        return GenerateGaussianNoise(mean, variance, Math.round(sampleFrequency * duration));
    }

    public static float[] GenerateGaussianNoise(float mean, float variance, int numberSamples) {

        Random r = new Random();

        float[] output = new float[numberSamples];

        for (int i = 0; i < output.length; i++) {
            output[i] = (float)(r.nextGaussian() * Math.sqrt(variance) + mean);
        }

        return output;
    }

    public static float[] GenerateCScale(int sampleFrequency) {

        int[] scale = {1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1};
        float noteLength = 1;

        float[] output = new float[0];

        for (int i = 0; i < scale.length; i++) {

            if (scale[i] == 1) {

                double noteFrequency = 440f * Math.pow(2f, (i - 9f) / 12f);
                float[] note = GenerateSineWave((float) noteFrequency, sampleFrequency, noteLength);

                output = Util.Concat(output, note);
            }
        }

        return output;
    }

    public static float[] GenerateSineWave(float frequency, int sampleFrequency, int numberSamples) {

        return GenerateSineWave(frequency, 1, 0, sampleFrequency, numberSamples);
    }

    public static float[] GenerateSineWave(float frequency, int sampleFrequency, float duration) {

        return GenerateSineWave(frequency, 1, 0, sampleFrequency, Math.round(sampleFrequency * duration));
    }

    public static float[] GenerateSineWave(float frequency, float amplitude, float phase, int sampleFrequency, int numberSamples) {

        float[] output = new float[numberSamples];

        float samplePeriod = 1f / sampleFrequency;

        for (int i = 0; i < output.length; i++) {
            output[i] = amplitude * (float) Math.sin(2 * Math.PI * frequency * i * samplePeriod - phase);
        }

        return output;
    }
}
