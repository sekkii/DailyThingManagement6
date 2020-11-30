package com.example.dailythingmanagement6;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private EditText editText;
    private TextView value;
    private String message;
    private String fileName = "sample.csv";
    private String name = null;
    private Integer num = null;
    private Date limit = null;
    private Date notice_day = null;
    private String[][] Data = new String[100][6];
    private FileControl file = new FileControl(this);

    //MainActivityから受け取るべきもの:品目のid
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = getIntent();
        int Id = intent.getIntExtra("sendText", 0)+1;
        System.out.println(Id);


        Data = file.readFile(fileName);
        TextView textView = findViewById(R.id.title);
        textView.setText(Data[Id][1]);
        value = findViewById(R.id.value);


        value.setText(Data[Id][2]);

        // back to MainActivity
        Button sbutton = findViewById(R.id.save);
        ButtonClick savelistener = new ButtonClick();
        sbutton.setOnClickListener(savelistener);

        Button plusbutton = findViewById(R.id.plusone);
        ButtonClick pluslistener = new ButtonClick();
        plusbutton.setOnClickListener(pluslistener);

    }

    private class ButtonClick implements View.OnClickListener {
        public void onClick(View view) {
            int id = view.getId();
            Button btn = (Button) view;

            switch (id) {
                case R.id.save:
                    String newval = value.getText().toString();

                    file.saveFile(Data, fileName, message, newval);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);

                    finish();
                    break;
                case R.id.plusone:
//                    i = new Integer(String.valueOf(editText.getText())).intValue();
//                    i = i + 1;
//                    System.out.print(i);
//                    editText.setText(String.valueOf(i));
                    break;
            }

        }
    }
}