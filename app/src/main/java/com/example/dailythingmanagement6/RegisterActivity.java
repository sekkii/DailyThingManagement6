package com.example.dailythingmanagement6;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private EditText showDate;
    private TextView value;
    private String message;
    private String fileName = "sample.csv";
    private String name = null;
    private Integer num = null;
    private Date limit = null;
    private Date notice_day = null;
    private String[][] Data = new String[100][6];
    private FileControl file = new FileControl(this);
    private Integer Id = null;

    //MainActivityから受け取るべきもの:品目のid
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = getIntent();
        Id = intent.getIntExtra("sendText", 0)+1;
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

        Button minusbutton = findViewById(R.id.minusone);
        ButtonClick minuslistener = new ButtonClick();
        minusbutton.setOnClickListener(minuslistener);

        //部品の取得
        showDate = (EditText) findViewById(R.id.showDate);
        showDate.setText(Data[Id][4]);
        showDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Calendarインスタンスを取得
                final Calendar date = Calendar.getInstance();

                //DatePickerDialogインスタンスを取得
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //setした日付を取得して表示
                                showDate.setText(String.format("%d/%02d/%02d", year, month+1, dayOfMonth));
                            }
                        },
                        date.get(Calendar.YEAR),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.DATE)
                );

                //dialogを表示
                datePickerDialog.show();

            }
        });

    }

    private class ButtonClick implements View.OnClickListener {
        public void onClick(View view) {
            int id = view.getId();
            Button btn = (Button) view;

            switch (id) {
                case R.id.save:
                    //個数の保存
                    String newval = value.getText().toString();
                    file.saveFile(Data, fileName, Id.toString(), newval,2);
                    //通知日の保存 変更を行ったあともう一度保存する場合はファイルを読み込み直す
                    Data = file.readFile(fileName);
                    String day = showDate.getText().toString();
                    file.saveFile(Data, fileName, Id.toString(), day,4);
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);

                    finish();
                    break;
                case R.id.plusone:
                    Integer i = new Integer(String.valueOf(value.getText())).intValue();
                    i = i + 1;
                    System.out.print(i);
                    value.setText(String.valueOf(i));
                    break;
                case R.id.minusone:
                    Integer j = new Integer(String.valueOf(value.getText())).intValue();
                    j = j - 1;
                    System.out.print(j);
                    value.setText(String.valueOf(j));
                    break;
            }

        }
    }
}