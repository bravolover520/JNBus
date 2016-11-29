package com.purityboy.jnbus.api;

import com.purityboy.jnbus.entity.Busline;
import com.purityboy.jnbus.entity.Status;

import retrofit2.http.GET;
//import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by John on 2016/11/24.
 */

public interface StationsService {

//    @Headers({"version: android-insigma.waybook.jinan-2319"})
    @GET("/server-ue2/rest/buslines/370100/{line}")
    public Observable<Data> getStations(@Path("line") String line);

    public class Data {

        private Status status;
        private Busline result;

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Busline getResult() {
            return result;
        }

        public void setResult(Busline result) {
            this.result = result;
        }
    }
}
