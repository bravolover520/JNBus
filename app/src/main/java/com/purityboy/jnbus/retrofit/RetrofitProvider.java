package com.purityboy.jnbus.retrofit;

import com.purityboy.jnbus.retrofit.converter.StringConverterFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by John on 2016/11/22.
 */

public class RetrofitProvider {

    private static Retrofit retrofit;

    private RetrofitProvider(){

    }

    public static Retrofit getInstance() {
        if (null == retrofit) {
//            Gson gson = new GsonBuilder()
//                    .registerTypeAdapterFactory(new ApiTypeAdapterFactory("result"))
//                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://60.216.101.229/")
                    .client(genericClient())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    /** retrofit对于解析器是由添加的顺序分别试用的，解析成功就直接返回，失败则调用下一个解析器。
                     * 如果你们的服务器一开始基本返回格式不固定，而后来在你的建议或者坚持下，大发善心的把后面新需求的API的返回格式都固定了的话，
                     * 可以按如下代码的方式将GsonConverterFactory添加在StringConverterFactory前面。这样如果是固定格式的就可以直接解析返回了。**/
                    .addConverterFactory(GsonConverterFactory.create())     //gson
                    .addConverterFactory(StringConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {Request request = chain.request()
                        .newBuilder()
                        .removeHeader("User-Agent")         //TODO 不起作用/移除个别请求头
                        .addHeader("Connection", "Keep-Alive")
                        .addHeader("Accept-Encoding", "gzip")
                        .addHeader("version", "android-insigma.waybook.jinan-2319")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36")
                        .build();
                    return chain.proceed(request);})
                .build();

        return httpClient;
    }
}
