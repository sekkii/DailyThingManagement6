package com.example.dailythingmanagement6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    private String[][] data = new String[100][6];
    private FileControl file = new FileControl(this);
    private String fileName = "sample.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        final TextView textView1 = findViewById(R.id.name);
        final TextView textView2 = findViewById(R.id.num);
        final EditText editDate = (EditText) findViewById(R.id.date);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Calendarインスタンスを取得
                final Calendar date = Calendar.getInstance();

                //DatePickerDialogインスタンスを取得
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //setした日付を取得して表示
                                editDate.setText(String.format("%d/%02d/%02d", year, month+1, dayOfMonth));
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

        Button sbutton = findViewById(R.id.addSave);
        sbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplication(), MainActivity.class);
//                // System.out.println(position);
//                // intent.putExtra("sendText", position);
//                int requestCode = 1000;
//                startActivityForResult(intent, requestCode);
                data = file.readFile(fileName);
                for (int i = 1; i < 100; i++) {

                    if (data[i][0] == null) {
                        data[i][0] = Integer.toString(i);
                        data[i][1] = textView1.getText().toString();
                        data[i][2] = textView2.getText().toString();
                        data[i][3] = "0.1";
                        data[i][4] = editDate.getText().toString();
                        data[i][5] = "text_mu";
                        break;
                    }
                }
                file.addFile(data,fileName);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

            }
        });
        Button bbutton = findViewById(R.id.back);
        bbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
            });
    }

}