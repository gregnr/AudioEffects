package com.grichardson.audioeffects;

import java.util.ArrayList;
import java.util.Collections;

public class Effects {

    // Default:
    //     delay = 0.0005f
    //     depth = 0.0005f
    //     modulationFrequency = 9f
    public static float[] Vibrato(float[] x, int fs, float delay, float depth, float modulationFrequency) {
        return FractionalDelay(x, fs, 0f, 1f, 0f, delay, depth, modulationFrequency);
    }

    // Default:
    //     delay = 0.002f
    //     depth = 0.002f
    //     modulationFrequency = 0.2f
    public static float[] Flanger(float[] x, int fs, float delay, float depth, float modulationFrequency) {
        return FractionalDelay(x, fs, 0.7f, 0.7f, 0.7f, delay, depth, modulationFrequency);
    }

    private static float[] FractionalDelay(float[] x, int fs, float BL, float FF, float FB, float delay, float depth, float modFreq) {

        float[] y = new float[x.length];

        int delaySamples = Math.round(delay * fs); // basic delay in # samples
        int depthSamples = Math.round(depth * fs); // modulation depth (width) in # samples
        float modFreqSamples = modFreq/fs; // modulation frequency in 1 / # samples

        int delayLineSamples = 2 + delaySamples + depthSamples*2; // length of the entire delay

        ArrayList<Float> delayLine = new ArrayList<Float>(Collections.nCopies(delayLineSamples, (float) 0));

        for (int i = 0; i < x.length; i++) {

            float modulationValue = depthSamples * (float) Math.sin(modFreqSamples*2*Math.PI*i);

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

    public static float[] GenerateSineWave(float frequency, int sampleFrequency, float duration) {

        return GenerateSineWave(frequency, 1, 0, sampleFrequency, duration);
    }

    public static float[] GenerateSineWave(float frequency, float amplitude, float phase, int sampleFrequency, float duration) {

        float[] output = new float[Math.round(sampleFrequency * duration)];

        float samplePeriod = 1f / sampleFrequency;

        for (int i = 0; i < output.length; i++) {
            output[i] = amplitude * (float) Math.sin(2 * Math.PI * frequency * i * samplePeriod - phase);
        }

        return output;
    }
}
