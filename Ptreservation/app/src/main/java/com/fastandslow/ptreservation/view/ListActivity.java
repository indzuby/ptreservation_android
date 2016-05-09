package com.fastandslow.ptreservation.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.domain.TodaySchedule;
import com.fastandslow.ptreservation.view.common.BaseActivity;

import java.util.ArrayList;

/**
 * Created by supernotebook on 2016-04-17.
 */
public class ListActivity extends BaseActivity {
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = (ListView) findViewById(R.id.today_schedule);

    }
}
