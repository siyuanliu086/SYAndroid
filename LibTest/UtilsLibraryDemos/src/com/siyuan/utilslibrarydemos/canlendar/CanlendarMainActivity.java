package com.siyuan.utilslibrarydemos.canlendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.siyuan.utilslibrarydemos.R;

public class CanlendarMainActivity extends Activity {
    private ListView listView;
    private String[] items = new String[]{"GridCalendarView","CircleCalendarView","ADCircleCalendarView","南通签到"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_main);
        listView = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                android.R.id.text1,items);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position){
                    case 0:
                        intent = new Intent(CanlendarMainActivity.this,GridCalendarActivity.class);
                        break;
                    case 1:
                        intent = new Intent(CanlendarMainActivity.this,CircleCalendarActivity.class);
                        break;
                    case 2:
                        intent = new Intent(CanlendarMainActivity.this, ADCircleCalendarActivity.class);
                        break;
                    case 3:
                    	intent = new Intent(CanlendarMainActivity.this, SigninCalendarActivity.class);
                    	break;
                    default:
                        break;
                }
                if(null != intent)
                startActivity(intent);
            }
        });
    }
}
