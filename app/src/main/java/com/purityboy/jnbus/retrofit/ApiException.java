package com.purityboy.jnbus.retrofit;

import java.io.IOException;

/**
 * Created by John on 2016/11/22.
 */

public class ApiException extends IOException {

    public final int code;
    public final String msg;

    public ApiException(int code) {
        this.code = code;
        this.msg = null;
    }

    public ApiException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
