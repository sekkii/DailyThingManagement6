package com.example.dailythingmanagement6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity   implements AdapterView.OnItemClickListener {

    private String[][] data = new String[100][6];
    private FileControl file = new FileControl(this);
    private String fileName = "sample.csv";



    // 表示する画像の名前（拡張子無し）
    private String[] members = new String[100];
    private List<String> memlist = new ArrayList<>();


    // Resource IDを格納するarray
    private List<Integer> imgList = new ArrayList<>();


    String data1 = "";
    static String EXTRA_DATA = "";
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = file.readFile(fileName);

        for (int i = 1; i < 100; i++) {

                if (data[i][0] == null) {
                    break;
                }
            if(data[i][2].equals("0")){

                file.saveDeleteFile(data, fileName, Integer.toString(i));
            }else {
                memlist.add(data[i][5]);
                members[i-1] = data[i][5];
            }
        }


//        Button milkClick = findViewById(R.id.milk);
//        ButtonClick milklistener = new ButtonClick();
//        milkClick.setOnClickListener(milklistener);

        // for-each member名をR.drawable.名前としてintに変換してarrayに登録
        for (String member: memlist){
            int imageId = getResources().getIdentifier(member,"drawable", getPackageName());
            imgList.add(imageId);
        }

        // GridViewのインスタンスを生成
        GridView gridview = findViewById(R.id.gridview);
        // BaseAdapter を継承したGridAdapterのインスタンスを生成
        // 子要素のレイアウトファイル grid_items.xml を
        // activity_main.xml に inflate するためにGridAdapterに引数として渡す
        GridAdapter adapter = new GridAdapter(this.getApplicationContext(),
                R.layout.grid_items,
                imgList,
                members
        );

        // gridViewにadapterをセット
        gridview.setAdapter(adapter);

        // item clickのListnerをセット
        gridview.setOnItemClickListener(this);
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
