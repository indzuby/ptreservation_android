package com.fastandslow.ptreservation.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fastandslow.ptreservation.R;
import com.fastandslow.ptreservation.domain.TodaySchedule;

/**
 * Created by zuby on 2016. 7. 1..
 */
public class SideMenuAdapter extends BaseAdapter {
    String[] menu ={"프로필","앱 문의하기","로그아웃"};
    int[] icon ={R.drawable.oval,R.drawable.oval,R.drawable.oval};
    Context mContext;

    public SideMenuAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return menu.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.element_side_menu, null);
        }
        ImageView iconView = (ImageView) v.findViewById(R.id.icon);
        TextView nameView = (TextView) v.findViewById(R.id.name);
        nameView.setText(menu[position]);
        iconView.setImageResource(icon[position]);

        return v;
    }
}
