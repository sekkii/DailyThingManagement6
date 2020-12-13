package com.example.dailythingmanagement6;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;


import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FirstSetting2 extends AppCompatActivity {

    private String[][] fileData = new String[100][6];
    private String fileName = "sample.csv";
    private FileControl file = new FileControl(this);
    private CheckBox[] checkBox = new CheckBox[10];
    private final String newval1 = "1";
    private final String newval2 = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstsetting2);

        fileData = file.readFile(fileName);

        checkBox[0] = (CheckBox)findViewById(R.id.checkBox1);
        checkBox[1] = (CheckBox)findViewById(R.id.checkBox2);
        checkBox[2] = (CheckBox)findViewById(R.id.checkBox3);
        checkBox[3] = (CheckBox)findViewById(R.id.checkBox4);
        checkBox[4] = (CheckBox)findViewById(R.id.checkBox5);
        checkBox[5] = (CheckBox)findViewById(R.id.checkBox6);
        checkBox[6] = (CheckBox)findViewById(R.id.checkBox7);
        checkBox[7] = (CheckBox)findViewById(R.id.checkBox8);
        checkBox[8] = (CheckBox)findViewById(R.id.checkBox9);
        checkBox[9] = (CheckBox)findViewById(R.id.checkBox10);

        final Button buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonSave.setText("押された");
                for (int i = 0; i <= 9; i++) {
                    CheckBox checkbox = (CheckBox)checkBox[i];
                    fileData = file.readFile(fileName);
                    String id = String.valueOf(i+1);
                    // TODO Auto-generated method stub
                    if (checkbox.isChecked()) {
                        // チェックされた状態の時の処理を記述
                        file.saveFile(fileData, fileName, id, newval1,2);

                    } else {
                        //チェックされていない状態の時の処理を記述
                        file.saveFile(fileData, fileName, id, newval2,2);

                    }
                }
                // Intent のインスタンスを取得する（最初の画面）
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);// ActivitySecond と ActivityThird を消す
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // ActivityFirst を再利用する（onCreate() は呼ばれない）
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                // 遷移先の画面を呼び出す
                startActivity(intent);
            }
        });
    }




}