package com.purityboy.jnbus.ui.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.widget.TextView;

import com.purityboy.jnbus.BR;
import com.purityboy.jnbus.R;
import com.purityboy.jnbus.base.BaseActivity;
import com.purityboy.jnbus.viewmodel.StationsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by John on 2016/11/22.
 * 站点
 */

public class StationsActivity extends BaseActivity {

    public static final String EXTRA_BUS_LINE_ID = "bus_line_id";

    @BindView(R.id.textView)
    TextView textView;

    private String line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding binding = DataBindingUtil.setContentView(this, getLayoutResId());
        ButterKnife.bind(this);
        binding.setVariable(BR.viewModel, new StationsViewModel(this, line));
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
