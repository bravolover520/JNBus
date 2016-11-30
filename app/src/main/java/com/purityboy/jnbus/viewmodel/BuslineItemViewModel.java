package com.purityboy.jnbus.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.purityboy.jnbus.command.ReplyCommand;
import com.purityboy.jnbus.databinding.BaseViewModel;
import com.purityboy.jnbus.entity.Busline;
import com.purityboy.jnbus.ui.activity.StationsActivity;

/**
 * Created by John on 2016/11/22.
 */

public class BuslineItemViewModel extends BaseViewModel<BuslineItemViewModel> {

    private Activity activity;
    //model
    public Busline busline;

    //field to presenter
    public final ObservableField<String> line = new ObservableField<>();

    public ViewStyle viewStyle = new ViewStyle();

    //Use class viewStyle to wrap field which is binding to style of view
    public static class ViewStyle {
        public final ObservableInt titleTextColor = new ObservableInt();
    }

    //command
    public ReplyCommand itemClickCommand = new ReplyCommand(() -> {
        this.viewStyle.titleTextColor.set(activity.getResources().getColor(android.R.color.darker_gray));
        Intent intent = new Intent(activity, StationsActivity.class);
        intent.putExtra(StationsActivity.EXTRA_BUS_LINE_ID, busline.getId());
        activity.startActivity(intent);
    });

    public BuslineItemViewModel(Activity activity, Busline busline) {
        this.activity = activity;
        this.busline = busline;
        this.viewStyle.titleTextColor.set(activity.getResources().getColor(android.R.color.black));
        line.set(busline.getLineName() + busline.getStartStationName() + "->" + busline.getEndStationName());
    }
}
