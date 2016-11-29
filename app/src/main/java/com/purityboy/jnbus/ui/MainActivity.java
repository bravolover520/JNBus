package com.purityboy.jnbus.ui;

import android.content.Intent;
import android.os.Bundle;

import com.purityboy.jnbus.R;
import com.purityboy.jnbus.base.BaseActivity;
import com.purityboy.jnbus.entity.Station;
import com.purityboy.jnbus.ui.activity.BuslinesActivity;
import com.purityboy.jnbus.ui.activity.StationsActivity;
import com.purityboy.jnbus.widget.BusStationsView;

import java.util.ArrayList;

/**
 * Created by John on 2016/11/22.
 *
 * 可以结合地图，标注公交车当前位置
 */

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

//        Bundle bundle = new Bundle();
//        bundle.putString(StationsActivity.EXTRA_BUS_LINE_ID, "199");
        Intent intent = new Intent(this, BuslinesActivity.class);
//        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }
}
