package com.purityboy.jnbus.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.purityboy.jnbus.R;
import com.purityboy.jnbus.base.BaseActivity;
import com.purityboy.jnbus.databinding.DBinding;
import com.purityboy.jnbus.databinding.ActivityBuslinesBinding;
import com.purityboy.jnbus.utils.L;
import com.purityboy.jnbus.viewmodel.BuslineViewModel;

/**
 * Created by John on 2016/11/22.
 * 查询线路
 */

public class BuslinesActivity extends BaseActivity<ActivityBuslinesBinding> {

    BuslineViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new BuslineViewModel(this);
        DBinding.setVariables(b, viewModel);

        b.searchBar.setOnSearchBarListener(text -> {
            L.i(text.toString());
            viewModel.searchBusline(text.toString());
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_buslines;
    }
}
