package com.grichardson.audioeffects;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DoublingFragment extends FractionalDelayFragment {

    public DoublingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        int color = 0xFFFAF0A5;
        int tint = Color.argb(96, Color.red(color), Color.green(color), Color.blue(color));

        view.setBackgroundColor(color);

        delayKnob.setMinValue(10f);
        delayKnob.setMaxValue(100f);
        delayKnob.setValue(90f);
        delayKnob.setTint(tint);

        depthKnob.setMinValue(1f);
        depthKnob.setMaxValue(30f);
        depthKnob.setValue(15f);
        depthKnob.setTint(tint);

        modFreqKnob.setMinValue(0.2f);
        modFreqKnob.setMaxValue(3f);
        modFreqKnob.setValue(0.2f);
        modFreqKnob.setTint(tint);

        return view;
    }
}
