package com.example.dailythingmanagement6;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import android.content.Context;

public class FileControl {
    Context c;

    public FileControl(Context ctx) {
        this.c = ctx;
    }


    public String[][] readFile(String file) {
        String text = null;

        String[][] data = new String[100][5];

        try (FileInputStream fileInputStream = c.openFileInput(file);
             BufferedReader br = new BufferedReader(
                     new InputStreamReader(fileInputStream, StandardCharsets.UTF_8))) {

            //読み込み行
            String line;

            //読み込み行数の管理
            int i = 0;

            //列名を管理する為の配列
            String[] arr = null;

            //1行ずつ読み込みを行う
            while ((line = br.readLine()) != null) {

                //先頭行は列名
                if (i == 0) {

                    //カンマで分割した内容を配列に格納する
                    // arr = { "no","name","num","cons" ,"notice_day};
                    arr = line.split(",");
                    data[i] = arr;

                } else {


                    String[] dataline = line.split(",");


//                    //配列の中身を順位表示する。列数(=列名を格納した配列の要素数)分繰り返す
//                    int colno = 2;
//                    System.out.println(dataline[colno]);

                    data[i] = dataline;


                }
                //行数のインクリメント
                i++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        return data;
    }

    public void saveFile(String[][] data, String file, String id, String val) {
        //受け渡されているもの:ファイルを二次元配列にしたもの,ファイル名,変更するファイルのID,変更する項目の個数
        //現状は項目の個数を変更するコードになっている。なんの項目を変更するか
        //変数を受け渡して変更できるようにしたほうがいいかも？
        String text = "";
        System.out.println(file + id + val);

        // try-with-resources
        try (FileOutputStream fileOutputstream = c.openFileOutput(file, Context.MODE_PRIVATE)) {
            for (int i = 0; i < 100; i++) {

                for (int j = 0; j < 5; j++) {
                    if (data[i][j] == null) {
                        break;
                    }
                    //4は最後の項目
                    if (j != 4) {
                        //2は個数、ここでは変更される項目は数量となるため。ここを変更するべき？
                        if (j == 2) {
                            if (data[i][0].equals(id)) {
                                text = text + val + ",";

                            } else {
                                text = text + data[i][j] + ",";

                            }
                        } else {
                            text = text + data[i][j] + ",";

                        }
                    } else {
                        text = text + data[i][j] + "\n";

                    }
                }

            }
            fileOutputstream.write(text.getBytes());
            //   System.out.println(text + "ファイルを保存しました");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}