package com.disney4a.baymax.core.attr.impl;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.disney4a.baymax.annotations.Tag_Http;
import com.disney4a.baymax.core.attr.Correspondents;
import com.disney4a.baymax.core.attr.Expectation;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/20 0020.
 */

public abstract class HttpExpectation implements Expectation<HttpRepay> {
    public final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void post(final String url, final String json) {
        Log.i("HTTPEx", "body="+json);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient()
                        .newBuilder()
                        .connectTimeout(20, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS)
                        .build();
                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        HttpRepay httpRepay = new HttpRepay();
                        httpRepay.setResponse(response);
                        onRepay(httpRepay);
                    }
                });
            }
        }.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void get(final String url) {
        new Thread() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient()
                        .newBuilder()
                        .connectTimeout(20, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        HttpRepay httpRepay = new HttpRepay();
                        httpRepay.setResponse(response);
                        onRepay(httpRepay);
                    }
                });
            }
        }.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void doSomething(Correspondents correspondents) {
        switch (correspondents.getPackaging().getTag_http().method()) {
            case Tag_Http.POST:
                String body = correspondents.getBody() instanceof String ? (String) correspondents.getBody()
                        : new Gson().toJson(correspondents.getBody());
                post(correspondents.getUri(), body);
                break;
            case Tag_Http.GET:
                get(correspondents.getUri());
                break;
        }
    }

    @Override
    public abstract void onRepay(HttpRepay repay);
    public abstract void onError(Exception e);
}
