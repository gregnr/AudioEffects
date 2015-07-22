package com.grichardson.audioeffects;

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

        delayKnob.setMinValue(2f);
        delayKnob.setMaxValue(30f);
        delayKnob.setValue(15f);

        depthKnob.setMinValue(2f);
        depthKnob.setMaxValue(30f);
        depthKnob.setValue(15f);

        modFreqKnob.setMinValue(0.2f);
        modFreqKnob.setMaxValue(3f);
        modFreqKnob.setValue(1.5f);

        return view;
    }
}
