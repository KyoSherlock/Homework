package com.kyo.homework.data.source.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jianghui on 2017/11/29.
 */

public class HomeworkRetrofit {

    public static Retrofit build() {
        return new Retrofit.Builder()
                .baseUrl("http://thoughtworks-ios.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
}
