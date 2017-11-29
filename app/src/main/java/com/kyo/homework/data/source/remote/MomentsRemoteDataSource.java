package com.kyo.homework.data.source.remote;

import com.kyo.homework.data.source.remote.service.MomentsService;
import com.kyo.homework.data.MomentEntity;
import com.kyo.homework.data.UserEntity;

import retrofit2.Callback;

/**
 * Created by jianghui on 2017/11/29.
 */

public class MomentsRemoteDataSource {

    public static void getUserInfo(Callback<UserEntity> callback){
            HomeworkRetrofit.build().create(MomentsService.class)
                    .getUserInfo("jsmith")
                    .enqueue(callback);
    }

    public static void getMoments(Callback<MomentEntity> callback){
        HomeworkRetrofit.build().create(MomentsService.class)
                .getMoments("jsmith")
                .enqueue(callback);
    }
}
