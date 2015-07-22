package com.grichardson.audioeffects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VibratoFragment extends FractionalDelayFragment {

    public VibratoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);

        delayKnob.setMinValue(0.5f);
        delayKnob.setMaxValue(3f);
        delayKnob.setValue(1.5f);

        depthKnob.setMinValue(0.5f);
        depthKnob.setMaxValue(3f);
        depthKnob.setValue(1.5f);

        modFreqKnob.setMinValue(3f);
        modFreqKnob.setMaxValue(15f);
        modFreqKnob.setValue(9f);

        return view;
    }
}
