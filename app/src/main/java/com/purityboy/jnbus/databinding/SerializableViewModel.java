package com.purityboy.jnbus.databinding;

import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by John on 2016/11/30.
 */

public class SerializableViewModel<T extends BaseViewModel> implements Serializable {

    private int index;

    public SerializableViewModel(int index) {
        this.index = index;
    }

    public
    @Nullable
    T toViewModel() {
        return (T) ViewModelStorage.getInstance().removeViewModel(index);
    }
}
