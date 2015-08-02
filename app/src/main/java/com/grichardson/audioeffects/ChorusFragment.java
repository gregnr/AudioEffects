package com.grichardson.audioeffects;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChorusFragment extends FractionalDelayFragment {

    public ChorusFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        int color = 0xFFD6B0B0;
        int tint = Color.argb(96, Color.red(color), Color.green(color), Color.blue(color));

        view.setBackgroundColor(color);

        delayKnob.setMinValue(25f);
        delayKnob.setMaxValue(50f);
        delayKnob.setValue(45f);
        delayKnob.setTint(tint);

        depthKnob.setMinValue(10f);
        depthKnob.setMaxValue(20f);
        depthKnob.setValue(15f);
        depthKnob.setTint(tint);

        return view;
    }
}
