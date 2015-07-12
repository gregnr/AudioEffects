package com.grichardson.audioeffects;

import android.annotation.TargetApi;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;


public class MainActivity extends ActionBarActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        InputStream beep = getResources().openRawResource(R.raw.beep);

        byte[] music1 = null;

        try {
            music1 = convertStreamToByteArray(beep);
            beep.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        short[] sineWave = generateSineWave(441, 1, 0, 44100, 4);
        short[] signal = generateCScale(44100);
        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, signal.length * 2, AudioTrack.MODE_STATIC);
        audioTrack.write(signal, 0, signal.length);
        audioTrack.play();
    }

    private static short[] generateCScale(int sampleFrequency) {

        int[] scale = {1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1};
        float noteLength = 1;

        short[] output = new short[0];

        for (int i = 0; i < scale.length; i++) {

            if (scale[i] == 1) {

                double noteFrequency = 440f * Math.pow(2f, (i - 9f) / 12f);
                short[] note = generateSineWave((float)noteFrequency, 1, 0, sampleFrequency, noteLength);

                output = concat(output, note);
            }
        }

        return output;
    }

    private static short[] generateSineWave(float frequency, float amplitude, float phase, int sampleFrequency, float duration) {

        short[] output = new short[Math.round(sampleFrequency * duration)];

        float samplePeriod = 1f / sampleFrequency;

        for (int i = 0; i < output.length; i++) {
            float sample = amplitude * (float) Math.sin(2 * Math.PI * frequency * i * samplePeriod - phase);
            output[i] = (short) (sample * Short.MAX_VALUE);
        }

        return output;
    }

    private static short[] concat(short[] a, short[] b) {
        int aLen = a.length;
        int bLen = b.length;
        short[] c = new short[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    private static byte[] convertStreamToByteArray(InputStream inputStream) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[10240];

        int i = inputStream.read(buffer, 0, buffer.length);

        while (i > 0) {

            byteArrayOutputStream.write(buffer, 0, i);
            i = inputStream.read(buffer, 0, buffer.length);
        }

        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
