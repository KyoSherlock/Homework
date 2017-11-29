package com.kyo.homework.data.source;

import com.kyo.homework.data.CommentEntity;
import com.kyo.homework.data.MomentEntity;
import com.kyo.homework.data.UserEntity;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by jianghui on 2017/11/29.
 */

public interface MomentsDataSource {

    public interface LoadMomentsCallback {
        void onMomentsLoaded(List<MomentEntity> momentEntities);

        void onFailure(Throwable t);
    }

    public interface LoadUserCallback {
        void onUserInfoLoaded(UserEntity userEntity);

        void onFailure(Throwable t);
    }

    void getUserInfo(LoadUserCallback callback);

    void getMoments(int start, int size, LoadMomentsCallback callback);
}
