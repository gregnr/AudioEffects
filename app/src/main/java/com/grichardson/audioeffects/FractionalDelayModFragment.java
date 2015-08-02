package com.grichardson.audioeffects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FractionalDelayModFragment extends FractionalDelayFragment {

    protected RoundKnobButtonDisplay modFreqKnob;

    protected int numberOfControls = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = super.onCreateView(inflater, container, savedInstanceState);

        LinearLayout.LayoutParams layoutParams = getLayoutParams();

        modFreqKnob = new RoundKnobButtonDisplay(getActivity());
        modFreqKnob.setValueDisplayFormat("%.1f Hz");
        modFreqKnob.setLabel(getString(R.string.modulationFrequency));

        ((LinearLayout) view).addView(modFreqKnob, layoutParams);

        return view;
    }

    public float getMinModFreqValue() {
        return modFreqKnob.getMinValue();
    }

    public void setMinModFreqValue(float minModFreqValue) {
        modFreqKnob.setMinValue(minModFreqValue);
    }

    public float getMaxModFreqValue() {
        return modFreqKnob.getMaxValue();
    }

    public void setMaxModFreqValue(float maxModFreqValue) {
        modFreqKnob.setMaxValue(maxModFreqValue);
    }

    public float getModulationFrequencyValue() {
        return modFreqKnob.getValue();
    }
}
