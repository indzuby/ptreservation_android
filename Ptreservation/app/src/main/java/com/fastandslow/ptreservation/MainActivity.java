package com.fastandslow.ptreservation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;


import com.fastandslow.ptreservation.adapter.ListAdapter;
import com.fastandslow.ptreservation.domain.TodaySchedule;
import com.fastandslow.ptreservation.view.common.BaseActivity;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ListAdapter mAdapter;

    private ListView mList;

    List<TodaySchedule> time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_schedule);

        DateTime dateTime = new DateTime();


        String dayOfMonth = Integer.toString(dateTime.getDayOfMonth());


        TextView textview = (TextView)findViewById(R.id.day_of_month);

        textview.setText(dayOfMonth);

        time = getTodayList();


        mList = (ListView)findViewById(R.id.today_schedule);
        ListAdapter adapter = new ListAdapter(this,time);

        mList.setAdapter(adapter);

        LocalTime nowTime = new LocalTime();

        mList.setSelection((nowTime.getHourOfDay()-3 <=0 ? 0 : nowTime.getHourOfDay()-3));
    }

    public List<TodaySchedule> getTodayList(){
        List<TodaySchedule> list = new ArrayList<>();
        for(int i = 0 ; i <=23;i++) {
            LocalTime time = new LocalTime(i,0);
            TodaySchedule schedule = new TodaySchedule();
            schedule.setLocalTime(time);
            list.add(schedule);
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater(

        ).inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
