package com.kyo.homework.data.source;

import com.kyo.homework.data.CommentEntity;
import com.kyo.homework.data.MomentEntity;
import com.kyo.homework.data.UserEntity;
import com.kyo.homework.data.source.remote.HomeworkRetrofit;
import com.kyo.homework.data.source.remote.service.MomentsService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jianghui on 2017/11/29.
 */

public class MomentsRepository implements MomentsDataSource {
    private static String USER = "jsmith";

    private static MomentsRepository momentsRepository = null;

    private List<MomentEntity> cachedMoments = null;

    @Override
    public void getUserInfo(final LoadUserCallback callback) {
        HomeworkRetrofit.build().create(MomentsService.class)
                .getUserInfo(USER)
                .enqueue(new Callback<UserEntity>() {
                    @Override
                    public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {
                        callback.onUserInfoLoaded(response.body());
                    }

                    @Override
                    public void onFailure(Call<UserEntity> call, Throwable t) {
                        callback.onFailure(t);
                    }
                });
    }


    @Override
    public void getMoments(final int start, final int size, final LoadMomentsCallback callback) {
        if (cachedMoments != null) {
            List<MomentEntity> momentEntities = new ArrayList<>(size);
            for (int i = start; i < cachedMoments.size() && (i - start) < size; i++) {
                momentEntities.add(cachedMoments.get(i));
            }
            callback.onMomentsLoaded(momentEntities);
        } else {
            HomeworkRetrofit.build().create(MomentsService.class)
                    .getMoments(USER)
                    .enqueue(new Callback<List<MomentEntity>>() {
                        @Override
                        public void onResponse(Call<List<MomentEntity>> call, Response<List<MomentEntity>> response) {
                            List<MomentEntity> momentEntities = response.body();
                            if (momentEntities != null) {
                                cachedMoments = momentEntities;
                                momentEntities = new ArrayList<>(size);
                                for (int i = start; i < cachedMoments.size() && (i - start) < size; i++) {
                                    momentEntities.add(cachedMoments.get(i));
                                }
                                callback.onMomentsLoaded(momentEntities);
                            } else {
                                callback.onMomentsLoaded(null);
                            }
                        }
                        
                        @Override
                        public void onFailure(Call<List<MomentEntity>> call, Throwable t) {
                            callback.onFailure(t);
                        }
                    });
        }
    }
}
