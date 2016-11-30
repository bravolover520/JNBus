package com.purityboy.jnbus.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.purityboy.jnbus.databinding.DBinding;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by John on 2016/11/22.
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends RxAppCompatActivity {

    protected T b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // base setup
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
        bindViews();
    }

    protected void bindViews(){
        b = DBinding.bind(this, getLayoutResId());
    }

    protected void getBundleExtras(Bundle extras){

    }

    protected abstract int getLayoutResId();
}
