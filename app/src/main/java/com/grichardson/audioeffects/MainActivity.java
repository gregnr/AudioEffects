package com.grichardson.audioeffects;

import android.annotation.TargetApi;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.musicg.wave.Wave;

import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    private AudioEffectPagerAdapter audioEffectPagerAdapter;
    private ViewPager viewPager;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.top_toolbar);
        setSupportActionBar(toolbar);

        audioEffectPagerAdapter = new AudioEffectPagerAdapter(getSupportFragmentManager(), this);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(audioEffectPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Load sounds
        InputStream sineInputStream = getResources().openRawResource(R.raw.x1);
        InputStream impulseInputStream = getResources().openRawResource(R.raw.reverb_impulse);

        final Wave inputWave = new Wave(sineInputStream);
        Wave impulseWave = new Wave(impulseInputStream);

        short[] inputShort = inputWave.getSampleAmplitudes();
        short[] impulseShort = impulseWave.getSampleAmplitudes();

        final float[] input = Util.ShortToFloat(inputShort);
        final float[] impulse = Util.ShortToFloat(impulseShort);

        // Get play button
        Button play = (Button) findViewById(R.id.playButton);
        play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                float[] output;
//              output = Util.Convolve(input, impulse);

                int currentIndex = viewPager.getCurrentItem();
                switch (currentIndex) {

                    case AudioEffectPagerAdapter.VIBRATO:

                        VibratoFragment vibratoFragment = (VibratoFragment) audioEffectPagerAdapter.getFragment(currentIndex);

                        float vibratoDelay = vibratoFragment.getDelayValue();
                        float vibratoDepth = vibratoFragment.getDepthValue();
                        float vibratoModulationFrequency = vibratoFragment.getModulationFrequencyValue();

                        output = Effects.Vibrato(input, inputWave.getWaveHeader().getSampleRate(), vibratoDelay, vibratoDepth, vibratoModulationFrequency);

                        break;

                    case AudioEffectPagerAdapter.FLANGER:

                        FlangerFragment flangerFragment = (FlangerFragment) audioEffectPagerAdapter.getFragment(currentIndex);

                        float flangerDelay = flangerFragment.getDelayValue();
                        float flangerDepth = flangerFragment.getDepthValue();
                        float flangerModulationFrequency = flangerFragment.getModulationFrequencyValue();

                        output = Effects.Flanger(input, inputWave.getWaveHeader().getSampleRate(), flangerDelay, flangerDepth, flangerModulationFrequency);

                        break;

                    default:
                        output = new float[0];
                }

                playWave(output, inputWave.getWaveHeader().getSampleRate(), inputWave.getWaveHeader().getBitsPerSample());
            }
        });
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
