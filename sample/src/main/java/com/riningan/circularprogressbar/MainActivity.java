package com.riningan.circularprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.SeekBar;

import com.riningan.widget.CircularProgressBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CircularProgressBar mCircularProgressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnLoaded40).setOnClickListener(this);
        findViewById(R.id.btnLoaded80).setOnClickListener(this);

        ((SeekBar) findViewById(R.id.seekBar1)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCircularProgressBar.setBackgroundWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ((SeekBar) findViewById(R.id.seekBar2)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mCircularProgressBar.setProgressWidth(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ((RadioButton) findViewById(R.id.radioButton1)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCircularProgressBar.setState(CircularProgressBar.StateEnum.ProgressDirect);
                }
            }
        });

        ((RadioButton) findViewById(R.id.radioButton2)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCircularProgressBar.setState(CircularProgressBar.StateEnum.ProgressInvert);
                }
            }
        });

        mCircularProgressBar = (CircularProgressBar) findViewById(R.id.circularProgressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                mCircularProgressBar.setState(CircularProgressBar.StateEnum.Loading);
                break;
            case R.id.btnLoaded40:
                mCircularProgressBar.setProgressWithAnimation(40);
                break;
            case R.id.btnLoaded80:
                mCircularProgressBar.setProgressWithAnimation(80);
                break;
        }
    }
}
