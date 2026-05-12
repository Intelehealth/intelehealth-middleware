/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  okhttp3.Credentials
 *  okhttp3.Interceptor
 *  okhttp3.Interceptor$Chain
 *  okhttp3.Request
 *  okhttp3.Response
 */
package com.emrmiddleware.api;

import java.io.IOException;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuthInterceptor
implements Interceptor {
    private String credentials;

    public BasicAuthInterceptor(String user, String password) {
        this.credentials = Credentials.basic((String)user, (String)password);
    }

    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder().header("Authorization", this.credentials).build();
        return chain.proceed(authenticatedRequest);
    }
}

