package com.grichardson.audioeffects;

import android.annotation.TargetApi;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.musicg.wave.Wave;

import java.io.InputStream;


public class MainActivity extends ActionBarActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream sineInputStream = getResources().openRawResource(R.raw.x1);
        InputStream impulseInputStream = getResources().openRawResource(R.raw.reverb_impulse);

        Wave inputWave = new Wave(sineInputStream);
        Wave impulseWave = new Wave(impulseInputStream);

        short[] inputShort = inputWave.getSampleAmplitudes();
        short[] impulseShort = impulseWave.getSampleAmplitudes();

        float[] input = Util.ShortToFloat(inputShort);
        float[] impulse = Util.ShortToFloat(impulseShort);

        float[] output = Util.Convolve(input, impulse);
//        float[] output = Effects.Vibrato(input, inputWave.getWaveHeader().getSampleRate());
//        float[] output = Effects.Flanger(input, inputWave.getWaveHeader().getSampleRate());

        playWave(output, inputWave.getWaveHeader().getSampleRate(), inputWave.getWaveHeader().getBitsPerSample());
    }

    private static void playWave(float[] wave, int sampleRate, int bitDepth) {

        int encoding;
        switch (bitDepth) {

            case 8:
                encoding = AudioFormat.ENCODING_PCM_8BIT;
                break;

            case 16:
                encoding = AudioFormat.ENCODING_PCM_16BIT;
                break;

            default:
                System.out.println("Error: Can only read wave with 8 or 16 bits per sample");
                return;
        }

        // Convert wave to array of shorts
        short[] waveShort = Util.FloatToShort(wave);

        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, AudioFormat.CHANNEL_OUT_MONO, encoding, waveShort.length * 2, AudioTrack.MODE_STATIC);
        audioTrack.write(waveShort, 0, waveShort.length);
        audioTrack.play();
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
