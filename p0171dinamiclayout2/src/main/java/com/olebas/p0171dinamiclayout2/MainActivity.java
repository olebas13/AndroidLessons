package com.olebas.p0171dinamiclayout2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private LinearLayout llMain;
    private RadioGroup rgGravity;
    private EditText etName;
    private Button btnCreate;
    private Button btnClear;

    private int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llMain = (LinearLayout) findViewById(R.id.llMain);
        rgGravity = (RadioGroup) findViewById(R.id.rgGravity);
        etName = (EditText) findViewById(R.id.etName);

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCreate:
                LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                        wrapContent, wrapContent);
                int btnGravity = Gravity.LEFT;
                switch (rgGravity.getCheckedRadioButtonId()) {
                    case R.id.rbLeft:
                        btnGravity = Gravity.LEFT;
                        break;
                    case R.id.rbCenter:
                        btnGravity = Gravity.CENTER_HORIZONTAL;
                        break;
                    case R.id.rbRight:
                        btnGravity = Gravity.RIGHT;
                        break;
                }

                lParams.gravity = btnGravity;

                Button btnNew = new Button(this);
                btnNew.setText(etName.getText().toString());
                llMain.addView(btnNew, lParams);

                break;

            case R.id.btnClear:
                llMain.removeAllViews();
                Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
