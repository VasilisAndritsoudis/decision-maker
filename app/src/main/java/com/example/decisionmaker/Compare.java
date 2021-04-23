package com.example.decisionmaker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Compare extends AppCompatActivity {

    ArrayList<TextView> arrayList0;
    ArrayList<TextView> arrayList;
    EditText editText1;
    EditText editText2;
    SeekBar seekBar1;
    SeekBar seekBar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        arrayList = new ArrayList<>();
        arrayList0 = new ArrayList<>();
        arrayList0.add(findViewById(R.id.number_00));
        arrayList0.add(findViewById(R.id.number_01));
        arrayList0.add(findViewById(R.id.number_02));
        arrayList0.add(findViewById(R.id.number_03));
        arrayList0.add(findViewById(R.id.number_04));
        arrayList0.add(findViewById(R.id.number_05));
        arrayList0.add(findViewById(R.id.number_06));
        arrayList0.add(findViewById(R.id.number_07));
        arrayList0.add(findViewById(R.id.number_08));
        arrayList0.add(findViewById(R.id.number_09));
        arrayList0.add(findViewById(R.id.number_010));

        arrayList.add(findViewById(R.id.number_0));
        arrayList.add(findViewById(R.id.number_1));
        arrayList.add(findViewById(R.id.number_2));
        arrayList.add(findViewById(R.id.number_3));
        arrayList.add(findViewById(R.id.number_4));
        arrayList.add(findViewById(R.id.number_5));
        arrayList.add(findViewById(R.id.number_6));
        arrayList.add(findViewById(R.id.number_7));
        arrayList.add(findViewById(R.id.number_8));
        arrayList.add(findViewById(R.id.number_9));
        arrayList.add(findViewById(R.id.number_10));

        editText1 = findViewById(R.id.sb1);
        editText2 = findViewById(R.id.sb2);
        editText1.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus)
                editText1.setText("");
            else {
                if(editText1.getText().toString().isEmpty()) {
                    editText1.setText(String.valueOf(seekBar1.getProgress()));
                } else {
                    if(Integer.parseInt(editText1.getText().toString()) < 0 ||
                            Integer.parseInt(editText1.getText().toString()) > 10) {
                        editText1.setText(String.valueOf(seekBar1.getProgress()));
                    }
                }
            }
        });
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!editText1.getText().toString().isEmpty()) {
                    String s1;
                    s1 = editText1.getText().toString();
                    if (Integer.parseInt(s1) >= 0 && Integer.parseInt(s1) <= 10)
                        seekBar1.setProgress(Integer.parseInt(s1));
                }
            }
        });
        editText2 = findViewById(R.id.sb2);
        editText2.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus)
                editText2.setText("");
            else {
                if(editText2.getText().toString().isEmpty()) {
                   editText2.setText(String.valueOf(seekBar2.getProgress()));
                } else {
                    if(Integer.parseInt(editText2.getText().toString()) < 0 ||
                            Integer.parseInt(editText2.getText().toString()) > 10) {
                        editText2.setText(String.valueOf(seekBar2.getProgress()));
                    }
                }
            }
        });
        editText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!editText2.getText().toString().isEmpty()) {
                    String s1;
                    s1 = editText2.getText().toString();
                    if (Integer.parseInt(s1) >= 0 && Integer.parseInt(s1) <= 10)
                        seekBar2.setProgress(Integer.parseInt(s1));
                }
            }
        });

        seekBar1 = findViewById(R.id.seekBar);
        seekBar1.setProgress(0);
        seekBar1.setMax(10);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               visibility(progress,arrayList0,editText1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar2 = findViewById(R.id.seekBar2);
        seekBar2.setProgress(0);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                visibility(progress,arrayList,editText2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void visibility (int num, ArrayList<TextView> viewArrayList, EditText editText) {
        editText.setText(String.valueOf(num));
        for(TextView textView : viewArrayList) {
            if(Integer.parseInt(textView.getText().toString()) == num) {
                textView.setVisibility(View.VISIBLE);
            } else {
                textView.setVisibility(View.INVISIBLE);
            }
        }
    }
}