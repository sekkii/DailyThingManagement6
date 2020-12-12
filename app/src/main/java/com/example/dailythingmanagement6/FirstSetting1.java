package com.example.dailythingmanagement6;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class FirstSetting1 extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private String fileName1 = "family_size.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstsetting1);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.family_size);

        Button buttonSave = findViewById(R.id.button_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                // エディットテキストのテキストを取得
                String text = editText.getText().toString();

                saveFile(fileName1, text);

                Intent intent = new Intent(getApplication(), FirstSetting2.class);
                // System.out.println(position);
                // intent.putExtra("sendText", position);
                int requestCode = 10000;
                startActivityForResult(intent, requestCode);
            }
        });
    }


    // ファイルを保存
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void saveFile(String file, String str) {

        // try-with-resources
        try (FileOutputStream fileOutputstream = openFileOutput(file, Context.MODE_PRIVATE)){

            fileOutputstream.write(str.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}