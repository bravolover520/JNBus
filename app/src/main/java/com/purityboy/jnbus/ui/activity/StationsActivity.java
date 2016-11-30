package com.purityboy.jnbus.ui.activity;

import android.os.Bundle;

import com.purityboy.jnbus.R;
import com.purityboy.jnbus.base.BaseActivity;
import com.purityboy.jnbus.databinding.ActivityStationsBinding;
import com.purityboy.jnbus.databinding.DBinding;
import com.purityboy.jnbus.viewmodel.StationsViewModel;

/**
 * Created by John on 2016/11/22.
 * 站点
 */

public class StationsActivity extends BaseActivity<ActivityStationsBinding> {

    public static final String EXTRA_BUS_LINE_ID = "bus_line_id";

    private String line;

    StationsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new StationsViewModel(this, line);
        DBinding.setVariables(b, viewModel);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_stations;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        line = getIntent().getStringExtra(EXTRA_BUS_LINE_ID);
    }
}
