package com.purityboy.jnbus.ui.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.purityboy.jnbus.BR;
import com.purityboy.jnbus.R;
import com.purityboy.jnbus.base.BaseActivity;
import com.purityboy.jnbus.utils.L;
import com.purityboy.jnbus.viewmodel.BuslineViewModel;
import com.purityboy.jnbus.widget.SearchBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by John on 2016/11/22.
 * 查询线路
 */

public class BuslinesActivity extends BaseActivity {

    @BindView(R.id.search_bar)
    SearchBarView searchBar;

    BuslineViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding binding = DataBindingUtil.setContentView(this, getLayoutResId());
        ButterKnife.bind(this);
        viewModel = new BuslineViewModel(this);
        binding.setVariable(BR.viewModel, viewModel);

        searchBar.setOnSearchBarListener(new SearchBarView.OnSearchBarListener() {
            @Override
            public void clear() {
                searchBar.clear();
            }

            @Override
            public void search(CharSequence text) {
                L.i(text.toString());
                viewModel.searchBusline(text.toString());
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_buslines;
    }
}
