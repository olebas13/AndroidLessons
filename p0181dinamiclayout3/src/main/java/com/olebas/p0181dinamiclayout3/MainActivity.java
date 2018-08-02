package com.olebas.p0181dinamiclayout3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private SeekBar sbWeight;
    private Button btn1;
    private Button btn2;

    private LinearLayout.LayoutParams lparams1;
    private LinearLayout.LayoutParams lparams2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        lparams1 = (LinearLayout.LayoutParams) btn1.getLayoutParams();
        lparams2 = (LinearLayout.LayoutParams) btn2.getLayoutParams();

        sbWeight = (SeekBar) findViewById(R.id.sbWeight);
        sbWeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int leftValue = progress;
                int rightValue = seekBar.getMax() - progress;
                lparams1.weight = leftValue;
                lparams2.weight = rightValue;
                btn1.setText(String.valueOf(leftValue));
                btn2.setText(String.valueOf(rightValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
