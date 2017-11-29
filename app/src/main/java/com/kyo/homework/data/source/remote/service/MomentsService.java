package com.kyo.homework.data.source.remote.service;

import com.kyo.homework.data.MomentEntity;
import com.kyo.homework.data.UserEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jianghui on 2017/11/29.
 */

public interface MomentsService {

    @GET("user/{user}")
    Call<UserEntity> getUserInfo(@Path("user") String user);

    @GET("user/{user}/tweets ")
    Call<List<MomentEntity>> getMoments(@Path("user") String user);

}
