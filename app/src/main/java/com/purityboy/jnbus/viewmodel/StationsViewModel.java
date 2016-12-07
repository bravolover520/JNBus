package com.purityboy.jnbus.viewmodel;

import android.app.Activity;
import android.databinding.ObservableField;

import com.purityboy.jnbus.api.BusService;
import com.purityboy.jnbus.api.StationsService;
import com.purityboy.jnbus.databinding.BaseViewModel;
import com.purityboy.jnbus.entity.Bus;
import com.purityboy.jnbus.entity.Busline;
import com.purityboy.jnbus.entity.Station;
import com.purityboy.jnbus.retrofit.RetrofitProvider;
import com.trello.rxlifecycle.ActivityLifecycleProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Notification;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by John on 2016/11/24.
 * 注意：在绑定过程中注意xml中先后顺序，如果某数据先展示，必须靠前放置，例如，代码中的站点展示优先于公交数据
 */
public class StationsViewModel extends BaseViewModel<StationsViewModel> {

    private Activity activity;

    private Busline data;       //线路及所有站点信息

    private List<Bus> buss;      //当前运行公交信息

    public final ObservableField<String> text = new ObservableField<>();
    public final ObservableField<List<Station>> datas = new ObservableField();
    public final ObservableField<List<Bus>> buses = new ObservableField();

    public StationsViewModel(Activity activity, String line) {
        this.activity = activity;
        loadData(line);
    }

    private void loadData(String line){
        Observable<Notification<StationsService.Data>> obj =
                RetrofitProvider.getInstance().create(StationsService.class)
                .getStations(line)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(((ActivityLifecycleProvider) activity).bindToLifecycle())      //compose()是高等级的抽象，他操作的是整个流，而不是单一发射出的项目
                .materialize().share(); //materialize将数据项和事件通知都当做数据项发射
                //.share()操作符是.publish().refcount()调用链的包装。

        obj.filter(Notification :: isOnNext)
                .map(n -> n.getValue())
                .doOnNext(m -> data = m.getResult())
                .subscribe(s -> {loadBus(line);}, throwable -> {throwable.printStackTrace();});
    }

    private void loadBus(String line) {
        Observable<Notification<BusService.Data>> obj =
                RetrofitProvider.getInstance().create(BusService.class)
                        .getBus(line)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(((ActivityLifecycleProvider) activity).bindToLifecycle())      //compose()是高等级的抽象，他操作的是整个流，而不是单一发射出的项目
                        .materialize().share(); //materialize将数据项和事件通知都当做数据项发射
        //.share()操作符是.publish().refcount()调用链的包装。

        obj.filter(Notification :: isOnNext)
                .map(n -> n.getValue())
                .doOnNext(m -> buss = m.getResult())
                .subscribe(s -> {calculateLoc(); interval(line);}, throwable -> {throwable.printStackTrace();});
    }

    //通过站点信息和当前的公交信息计算区位
    private void calculateLoc() {
        text.set(data.getOperationTime() + "\n" + data.getTicketPrice());

        //得到站点
        List<Station> stations = data.getStations();
        //放置站点
        datas.set(stations);
    }

    //每隔多久请求一次
    private void interval(String line) {
        buses.set(buss);
//        Observable.interval(15, TimeUnit.SECONDS).subscribe(aLong -> {loadBus(line);});    //延时15秒再请求
    }
}
