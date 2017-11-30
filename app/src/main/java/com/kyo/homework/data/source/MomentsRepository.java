package com.kyo.homework.data.source;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.kyo.homework.data.CommentEntity;
import com.kyo.homework.data.MomentEntity;
import com.kyo.homework.data.UserEntity;
import com.kyo.homework.data.source.remote.HomeworkRetrofit;
import com.kyo.homework.data.source.remote.service.MomentsService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.RunnableFuture;

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
        Log.e("Kyo", "getMoments start:" + start + ", size:" + size);
        if (cachedMoments != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    List<MomentEntity> momentEntities = new ArrayList<>(size);
                    for (int i = start; i < cachedMoments.size() && (i - start) < size; i++) {
                        momentEntities.add(cachedMoments.get(i));
                    }
                    callback.onMomentsLoaded(momentEntities);
                }
            }.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        } else {
            HomeworkRetrofit.build().create(MomentsService.class)
                    .getMoments(USER)
                    .enqueue(new Callback<List<MomentEntity>>() {
                        @Override
                        public void onResponse(Call<List<MomentEntity>> call, Response<List<MomentEntity>> response) {
                            List<MomentEntity> momentEntities = response.body();
                            if (momentEntities != null) {
//                                // remove invalid data
//                                Iterator<MomentEntity> iterator = momentEntities.iterator();
//                                while (iterator.hasNext()) {
//                                    MomentEntity entity = iterator.next();
//                                    if (entity == null ||
//                                            entity.sender == null ||
//                                            ((entity.images == null || entity.images.isEmpty()) && TextUtils.isEmpty(entity.content))) {
//                                        iterator.remove();
//                                    }
//                                }

                                // callback
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
