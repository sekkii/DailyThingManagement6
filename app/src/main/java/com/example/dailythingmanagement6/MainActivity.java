package com.example.dailythingmanagement6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.AppLaunchChecker;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity   implements AdapterView.OnItemClickListener {

    private String[][] data = new String[100][6];
    private FileControl file = new FileControl(this);
    private String fileName = "sample.csv";


    // 表示する画像の名前（拡張子無し）
    private String[] members = new String[100];
    private String[] nums = new String[100];
    private List<String> memlist = new ArrayList<>();


    // Resource IDを格納するarray
    private List<Integer> imgList = new ArrayList<>();


    String data1 = "";
    static String EXTRA_DATA = "";
    private static final String TAG = MainActivity.class.getSimpleName();
    private AlarmManager am;
    private PendingIntent pending;
    private int requestCode = 1;
    Notification notification = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //データ読込
      //  data = file.readFile(fileName);
        // FloatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), AddActivity.class);
                // System.out.println(position);
               // intent.putExtra("sendText", position);
                int requestCode = 10000;
                startActivityForResult(intent, requestCode);

            }
        }
        );

        if(AppLaunchChecker.hasStartedFromLauncher(this)){
            Log.d("AppLaunchChecker","2回目以降");
        } else {
            Log.d("AppLaunchChecker","はじめてアプリを起動した");


            try (InputStream is = this.getAssets().open("sample.csv");
                 BufferedReader br = new BufferedReader(
                         new InputStreamReader(is, StandardCharsets.UTF_8))){
                System.out.println("読み込み");

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
            }  catch (IOException e) {
                e.printStackTrace();
            }
            file.saveFile(data, fileName, "1", data[1][1],1);


            Intent intent = new Intent(getApplication(), FirstSetting1.class);
            // System.out.println(position);
            // intent.putExtra("sendText", position);
            int requestCode = 10000;
            startActivityForResult(intent, requestCode);
        }

        AppLaunchChecker.onActivityCreate(this);



    }
    //Activityを表示したとき。通知登録とcsv読み込みはここに入れる。
    @Override
    protected void onResume() {
        super.onResume();

        //データ読込
        data = file.readFile(fileName);

        //view 表示処理

        //表示する項目のリストの初期化
        memlist.clear();
        members = new String[100];
        nums = new String[100];
        imgList.clear();
        int j = 1;

        for (int i = 1; i < 100; i++) {


            if (data[i][0] == null) {
                break;
            }
            if (data[i][2].equals("0")) {
//
//                file.saveDeleteFile(data, fileName, Integer.toString(i));
//                data = file.readFile(fileName);
//                System.out.println(data[i][0]);
                //バグ：現状個数が0の行を削除していないため、positionの行の登録画面が出力されるため修正必須
                //解決案　0の行を消すようにする←こっちが理想新規項目を登録したときに、0の行が残るままだと不便なため
                // 　　　csvに対応した数値をだすようにする

            }else {
                memlist.add(data[i][5]);
                members[j-1] = data[i][1];
                nums[j-1] = data[i][2];
                j++;
            }

        }

        // for-each member名をR.drawable.名前としてintに変換してarrayに登録
        for (String member : memlist) {
            int imageId = getResources().getIdentifier(member, "drawable", getPackageName());
            imgList.add(imageId);
        }

        // GridViewのインスタンスを生成
        GridView gridview = findViewById(R.id.gridview);
        // BaseAdapter を継承したGridAdapterのインスタンスを生成
        // 子要素のレイアウトファイル grid_items.xml を
        // activity_main.xml に inflate するためにGridAdapterに引数として渡すaaa
        GridAdapter adapter = new GridAdapter(this.getApplicationContext(),
                R.layout.grid_items,
                imgList,
                members,
                nums
        );


        // gridViewにadapterをセット
        gridview.setAdapter(adapter);

        // item clickのListnerをセット
        gridview.setOnItemClickListener(this);

        //通知処理

        //呼び出す日時を設定する
        int ACTIVE_REQUEST_CODE = 100;    // たとえば、requestCodeが12以上のアラームを有効にしておくとして

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // 不要になった過去のアラームをまとめて削除する
        // requestCodeを0から登録していたとする
        for (int requestCode = 0; requestCode < ACTIVE_REQUEST_CODE; requestCode++) {
            Intent intent = new Intent(getApplicationContext(), Notifier.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, intent, 0);

            pendingIntent.cancel();
            alarmManager.cancel(pendingIntent);
        }
        for (int i = 1; i < 100; i++) {

            if (data[i][0] == null) {
                break;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/d");
            Date date = null;
            try {
                date = dateFormat.parse(data[i][4]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            Calendar triggerTime = Calendar.getInstance();
//            triggerTime.add(Calendar.SECOND, 10);    //今から5秒後

            Calendar triggerTime = Calendar.getInstance();
            triggerTime.setTime(date);
            //設定した日時で発行するIntentを生成
            int picId = getResources().getIdentifier(data[i][5], "drawable", getPackageName());
            Intent intent1 = new Intent(getApplicationContext(), Notifier.class);
            intent1.putExtra("id", i-1);
            intent1.putExtra("name", data[i][1]);
            intent1.putExtra("picname", picId);
            PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), i, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

            //日時と発行するIntentをAlarmManagerにセットします
            AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
            manager.set(AlarmManager.RTC_WAKEUP, triggerTime.getTimeInMillis(), sender);
            System.out.println(triggerTime.getTime());
        }


    }

    private class ButtonClick implements View.OnClickListener {


        public void onClick(View view) {
            String apText;
            int id = view.getId();
            Button btn = (Button) view;

            switch (id) {
//            case R.id.milk:
//                String data1 = "1";
//                Intent intent = new Intent(getApplication(), RegisterActivity.class);
//                intent.putExtra("sendText", data1);
//                int requestCode = 1000;
//                startActivityForResult(intent, requestCode);

            }
        }

    }

    protected void onActivityResult( int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = Integer.toString(position);
        Intent intent = new Intent(getApplication(), RegisterActivity.class);
        // System.out.println(position);

        intent.putExtra("sendText", position);
        int requestCode = 1000;
        startActivityForResult(intent, requestCode);
        //startActivity( intent );




    }


}
