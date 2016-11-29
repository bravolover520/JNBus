package com.purityboy.jnbus.api;

import com.purityboy.jnbus.entity.Bus;
import com.purityboy.jnbus.entity.Status;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by John on 2016/11/28.
 */

public interface BusService {


    //http://60.216.101.229/server-ue2/rest/buses/busline/370100/199
    @GET("http://60.216.101.229/server-ue2/rest/buses/busline/370100/{line}")
    public Observable<Data> getBus(@Path("line") String line);

    public class Data {
        private Status status;
        private List<Bus> result;

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public List<Bus> getResult() {
            return result;
        }

        public void setResult(List<Bus> result) {
            this.result = result;
        }
    }
}
