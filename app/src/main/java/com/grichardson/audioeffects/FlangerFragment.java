package com.grichardson.audioeffects;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FlangerFragment extends FractionalDelayFragment {

    public FlangerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        int color = 0xFFB9CAD9;
        int tint = Color.argb(96, Color.red(color), Color.green(color), Color.blue(color));

        view.setBackgroundColor(color);

        delayKnob.setMinValue(2f);
        delayKnob.setMaxValue(30f);
        delayKnob.setValue(2f);
        delayKnob.setTint(tint);

        depthKnob.setMinValue(2f);
        depthKnob.setMaxValue(30f);
        depthKnob.setValue(2f);
        depthKnob.setTint(tint);

        modFreqKnob.setMinValue(0.2f);
        modFreqKnob.setMaxValue(3f);
        modFreqKnob.setValue(0.2f);
        modFreqKnob.setTint(tint);

        return view;
    }
}
