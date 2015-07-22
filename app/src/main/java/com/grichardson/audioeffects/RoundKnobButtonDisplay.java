package com.grichardson.audioeffects;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RoundKnobButtonDisplay extends LinearLayout {

    private final TextView valueTextView;
    private final RoundKnobButton knob;
    private final TextView labelTextView;

    private RoundKnobButtonDisplayListener listener;

    private String valueDisplayFormat = "%.0f%%";

    private float minValue = 0;
    private float maxValue = 100;
    private float currentValue = minValue;

    private String label = "";

    public RoundKnobButtonDisplay(final Context context) {

        super(context);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        valueTextView = new TextView(context);
        valueTextView.setGravity(Gravity.CENTER);

        knob = new RoundKnobButton(context, R.drawable.stator, R.drawable.rotoron, R.drawable.rotoroff, 300, 300);
        knob.setState(true);

        knob.setListener(new RoundKnobButton.RoundKnobButtonListener() {

            public void onStateChange(boolean newstate) {
                if (listener != null) listener.onStateChange(newstate);
            }

            public void onRotate(final int percentage) {

                float value = getMinValue() + (getMaxValue() - getMinValue()) * percentage / 100;

                setValue(value);

                if (listener != null) listener.onRotate(value);
            }
        });

        labelTextView = new TextView(context);
        labelTextView.setGravity(Gravity.CENTER);
        labelTextView.setTypeface(labelTextView.getTypeface(), Typeface.BOLD);

//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.weight = 100/3;

        RelativeLayout knobContainer = new RelativeLayout(context);

        addView(valueTextView, layoutParams);
        addView(knob, layoutParams);
        addView(labelTextView, layoutParams);

        setValueDisplayFormat(valueDisplayFormat);
//        setValue(currentValue);
        setLabel(label);
    }

    public void setListener(RoundKnobButtonDisplayListener listener) {
        this.listener = listener;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {

        this.minValue = minValue;

        if (getValue() < minValue) {
            setValue(minValue);
        }
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {

        this.maxValue = maxValue;

        if (getValue() > maxValue) {
            setValue(maxValue);
        }
    }

    public float getValue() {
        return currentValue;
    }

    public void setValue(float currentValue) {

        if (currentValue > getMaxValue()) {
            this.currentValue = getMaxValue();
        } else if (currentValue < getMinValue()) {
            this.currentValue = getMinValue();
        } else {
            this.currentValue = currentValue;
        }

        knob.setRotorPercentage((int) ((currentValue - getMinValue()) / (getMaxValue() - getMinValue()) * 100));
        valueTextView.setText(String.format(valueDisplayFormat, currentValue));
    }

    public String getValueDisplayFormat() {
        return valueDisplayFormat;
    }

    public void setValueDisplayFormat(String valueDisplayFormat) {
        this.valueDisplayFormat = valueDisplayFormat;
        valueTextView.setText(String.format(valueDisplayFormat, getValue()));
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        labelTextView.setText(label);
    }

    public void setTint(int tint) {
        knob.setTint(tint);
    }

    interface RoundKnobButtonDisplayListener {
        public void onStateChange(boolean newstate) ;
        public void onRotate(float value);
    }
}
