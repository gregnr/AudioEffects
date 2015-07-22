package com.grichardson.audioeffects;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;
import java.util.Map;

public class AudioEffectPagerAdapter extends FragmentStatePagerAdapter {

    public static final int VIBRATO = 0;
    public static final int FLANGER = 1;

    private Context context;
    private Map<Integer, Fragment> fragments = new HashMap<>();

    public AudioEffectPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }

    public Fragment getFragment(int i) {
        return fragments.get(i);
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case VIBRATO:

                Fragment vibratoFragment = new VibratoFragment();
                fragments.put(i, vibratoFragment);

                return vibratoFragment;

            case FLANGER:

                Fragment flangerFragment = new FlangerFragment();
                fragments.put(i, flangerFragment);

                return flangerFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case VIBRATO:
                return context.getString(R.string.vibrato);
            case FLANGER:
                return context.getString(R.string.flanger);
            default:
                return "";
        }
    }
}