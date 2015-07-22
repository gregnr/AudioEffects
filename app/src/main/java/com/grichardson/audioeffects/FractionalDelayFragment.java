package com.grichardson.audioeffects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FractionalDelayFragment extends Fragment {

    protected RoundKnobButtonDisplay delayKnob;
    protected RoundKnobButtonDisplay depthKnob;
    protected RoundKnobButtonDisplay modFreqKnob;

    public FractionalDelayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 100/3;

        delayKnob = new RoundKnobButtonDisplay(getActivity());
        delayKnob.setValueDisplayFormat("%.1f ms");
        delayKnob.setLabel(getString(R.string.delay));

        depthKnob = new RoundKnobButtonDisplay(getActivity());
        depthKnob.setValueDisplayFormat("%.1f ms");
        depthKnob.setLabel(getString(R.string.depth));

        modFreqKnob = new RoundKnobButtonDisplay(getActivity());
        modFreqKnob.setValueDisplayFormat("%.1f Hz");
        modFreqKnob.setLabel(getString(R.string.modulationFrequency));

        ((LinearLayout)view).addView(delayKnob, layoutParams);
        ((LinearLayout)view).addView(depthKnob, layoutParams);
        ((LinearLayout)view).addView(modFreqKnob, layoutParams);

        // Enforce rule where depth cannot be greater than delay
        delayKnob.setListener(new RoundKnobButtonDisplay.RoundKnobButtonDisplayListener() {

            @Override
            public void onStateChange(boolean newstate) {

            }

            @Override
            public void onRotate(float value) {

                if (value < depthKnob.getValue()) {
                    depthKnob.setValue(value);
                }
            }
        });

        // Enforce rule where depth cannot be greater than delay
        depthKnob.setListener(new RoundKnobButtonDisplay.RoundKnobButtonDisplayListener() {

            @Override
            public void onStateChange(boolean newstate) {

            }

            @Override
            public void onRotate(float value) {

                if (value > delayKnob.getValue()) {
                    delayKnob.setValue(value);
                }
            }
        });

        return view;
    }

    public float getMinDelayValue() {
        return delayKnob.getMinValue();
    }

    public void setMinDelayValue(float minDelayValue) {
        delayKnob.setMinValue(minDelayValue);
    }

    public float getMaxDelayValue() {
        return delayKnob.getMaxValue();
    }

    public void setMaxDelayValue(float maxDelayValue) {
        delayKnob.setMaxValue(maxDelayValue);
    }

    public float getMinDepthValue() {
        return depthKnob.getMinValue();
    }

    public void setMinDepthValue(float minDepthValue) {
        depthKnob.setMinValue(minDepthValue);
    }

    public float getMaxDepthValue() {
        return depthKnob.getMaxValue();
    }

    public void setMaxDepthValue(float maxDepthValue) {
        depthKnob.setMaxValue(maxDepthValue);
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

    public float getDelayValue() {
        System.out.println(delayKnob);
        return delayKnob.getValue() / 1000;
    }

    public float getDepthValue() {
        return depthKnob.getValue() / 1000;
    }

    public float getModulationFrequencyValue() {
        return modFreqKnob.getValue();
    }
}
