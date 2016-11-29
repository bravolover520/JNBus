package com.purityboy.jnbus.api;

import com.purityboy.jnbus.entity.Busline;
import com.purityboy.jnbus.entity.Page;
import com.purityboy.jnbus.entity.Status;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by John on 2016/11/22.
 */

public interface BuslineService {

    @GET("/server-ue2/rest/buslines/simple/370100/{busline}/{offset}/20")
    public Observable<Data> getBuslinesList(@Path("busline") String busline, @Path("offset") int offset);

    public class Data {

        private Status status;
        private Result result;

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }
    }

    public class Result {
        private Page pageParam;
        private List<Busline> result;

        public Page getPageParam() {
            return pageParam;
        }

        public void setPageParam(Page pageParam) {
            this.pageParam = pageParam;
        }

        public List<Busline> getResult() {
            return result;
        }

        public void setResult(List<Busline> result) {
            this.result = result;
        }
    }
}
