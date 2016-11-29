package com.purityboy.jnbus.viewmodel;

import android.app.Activity;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.purityboy.jnbus.BR;
import com.purityboy.jnbus.R;
import com.purityboy.jnbus.api.BuslineService;
import com.purityboy.jnbus.retrofit.RetrofitProvider;
import com.trello.rxlifecycle.ActivityLifecycleProvider;

import me.tatarka.bindingcollectionadapter.BaseItemViewSelector;
import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;
import rx.Notification;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by John on 2016/11/24.
 */

public class BuslineViewModel implements ViewModel {

    private Activity activity;

    private BuslineService.Result content;

    private int busline_offset = 0;

    // viewModel for RecyclerView   历史记录
    public final ObservableList<BuslineItemViewModel> historyItemViewModel = new ObservableArrayList<>();
    // view layout for RecyclerView
    public final ItemViewSelector<BuslineItemViewModel> historyItemView = new BaseItemViewSelector<BuslineItemViewModel>() {
        @Override
        public void select(ItemView itemView, int position, BuslineItemViewModel itemViewModel) {
            itemView.set(BR.viewModel, R.layout.item_buslines);
        }
    };

    // viewModel for RecyclerView 每次的更新
    public final ObservableList<BuslineItemViewModel> itemViewModel = new ObservableArrayList<>();
    // view layout for RecyclerView
    public final ItemViewSelector<BuslineItemViewModel> itemView = new BaseItemViewSelector<BuslineItemViewModel>() {
        @Override
        public void select(ItemView itemView, int position, BuslineItemViewModel itemViewModel) {
            itemView.set(BR.viewModel, R.layout.item_buslines);
        }
    };

    //collection of view style,wrap to a class to manage conveniently!
    public final ViewStyle viewStyle = new ViewStyle();

    public class ViewStyle {
        public final ObservableBoolean progressRefreshing = new ObservableBoolean(true);
    }

    public BuslineViewModel(Activity activity) {
        this.activity = activity;
    }

    private void loadBusline(String line, int busline_offset) {
        Observable<Notification<BuslineService.Data>> obj =
                RetrofitProvider.getInstance().create(BuslineService.class)
                        .getBuslinesList(line, busline_offset)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(((ActivityLifecycleProvider) activity).bindToLifecycle())      //compose()是高等级的抽象，他操作的是整个流，而不是单一发射出的项目
                        .materialize().share(); //materialize将数据项和事件通知都当做数据项发射
        //.share()操作符是.publish().refcount()调用链的包装。

        obj.filter(Notification :: isOnNext)
                .map(n -> n.getValue())
                .filter(f -> !f.getResult().getResult().isEmpty())
                .doOnNext(m -> Observable.just(content).subscribe(b -> itemViewModel.clear()))
                .doOnNext(m -> content = m.getResult())
                .flatMap(m -> Observable.from(content.getResult()))
                .subscribe(i -> {
                    itemViewModel.add(new BuslineItemViewModel(activity, i));
                }, throwable -> {throwable.printStackTrace();});//loadData(String.valueOf(Integer.valueOf(line).intValue() + 1));

        obj.subscribe((n) -> {
            viewStyle.progressRefreshing.set(false);
        });
    }

    public void searchBusline(String line) {
        loadBusline(line, busline_offset);
    }
}
