package com.grichardson.audioeffects;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VibratoFragment extends FractionalDelayModFragment {

    public VibratoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        int color = 0xFFC5E9C5;
        int tint = Color.argb(96, Color.red(color), Color.green(color), Color.blue(color));

        view.setBackgroundColor(color);

        delayKnob.setMinValue(0.5f);
        delayKnob.setMaxValue(3f);
        delayKnob.setValue(1f);
        delayKnob.setTint(tint);

        depthKnob.setMinValue(0.5f);
        depthKnob.setMaxValue(3f);
        depthKnob.setValue(1f);
        depthKnob.setTint(tint);

        modFreqKnob.setMinValue(3f);
        modFreqKnob.setMaxValue(15f);
        modFreqKnob.setValue(9f);
        modFreqKnob.setTint(tint);

        return view;
    }
}
