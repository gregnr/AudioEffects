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
    public static final int CHORUS = 2;
    public static final int DOUBLING = 3;

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

            case CHORUS:

                Fragment chorusFragment = new ChorusFragment();
                fragments.put(i, chorusFragment);

                return chorusFragment;

            case DOUBLING:

                Fragment doublingFragment = new DoublingFragment();
                fragments.put(i, doublingFragment);

                return doublingFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case VIBRATO:
                return context.getString(R.string.vibrato);
            case FLANGER:
                return context.getString(R.string.flanger);
            case CHORUS:
                return context.getString(R.string.chorus);
            case DOUBLING:
                return context.getString(R.string.doubling);
            default:
                return "";
        }
    }
}