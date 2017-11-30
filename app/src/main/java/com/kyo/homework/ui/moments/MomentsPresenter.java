package com.kyo.homework.ui.moments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kyo.homework.data.MomentEntity;
import com.kyo.homework.data.UserEntity;
import com.kyo.homework.data.source.MomentsDataSource;
import com.kyo.homework.data.source.MomentsRepository;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by jianghui on 2017/11/29.
 */

public class MomentsPresenter implements MomentsContract.Presenter {

    private static final int PAGE_SIZE = 50;
    private final MomentsContract.View view;
    private final MomentsRepository repository;
    private int index = 0;

    public MomentsPresenter(@NonNull MomentsContract.View view) {
        this.view = view;
        this.repository = new MomentsRepository();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadUser() {
        repository.getUserInfo(new MyLoadUserCallback(this));
    }

    @Override
    public void loadInitMoments() {
        repository.getMoments(0, PAGE_SIZE, new MyLoadMomentsCallback(this, MyLoadMomentsCallback.TYPE_INIT));
    }

    @Override
    public void reloadMoments() {
        repository.getMoments(0, PAGE_SIZE, new MyLoadMomentsCallback(this, MyLoadMomentsCallback.TYPE_RELOAD));
    }

    @Override
    public void loadMoreMoments() {
        repository.getMoments(index, PAGE_SIZE, new MyLoadMomentsCallback(this, MyLoadMomentsCallback.TYPE_MORE));
    }

    private void onLoadUser(UserEntity userEntity) {
        if (userEntity != null && view.isAvailable()) {
            view.showUser(userEntity);
        }
    }

    private void onLoadInitMoments(List<MomentEntity> momentEntities) {
        if (momentEntities != null && !momentEntities.isEmpty()) {
            view.showInitMoments(momentEntities);
            index = Math.min(PAGE_SIZE, momentEntities.size());
        }
    }

    private void onReloadMoments(List<MomentEntity> momentEntities) {
        if (momentEntities != null && !momentEntities.isEmpty()) {
            view.showInitMoments(momentEntities);
            index = Math.min(PAGE_SIZE, momentEntities.size());
        }
        view.hideRefreshLayout();
    }

    private void onLoadMoreMoments(List<MomentEntity> momentEntities) {
        if (momentEntities != null && !momentEntities.isEmpty()) {
            view.hideLoadMoreLayout();
            view.showMoreMoments(momentEntities);
            index = index + Math.min(PAGE_SIZE, momentEntities.size());
        } else {
            view.stopLoadMoreLayout();
        }
    }

    private static class MyLoadUserCallback implements MomentsDataSource.LoadUserCallback {

        private final WeakReference<MomentsPresenter> presenterWeakRef;

        public MyLoadUserCallback(MomentsPresenter presenter) {
            this.presenterWeakRef = new WeakReference<>(presenter);
        }

        @Override
        public void onUserInfoLoaded(UserEntity userEntity) {
            MomentsPresenter presenter = presenterWeakRef.get();
            if (presenter != null) {
                presenter.onLoadUser(userEntity);
            }
        }

        @Override
        public void onFailure(Throwable t) {

        }
    }

    private static class MyLoadMomentsCallback implements MomentsDataSource.LoadMomentsCallback {
        private static final int TYPE_INIT = 1;
        private static final int TYPE_RELOAD = 2;
        private static final int TYPE_MORE = 3;
        private final WeakReference<MomentsPresenter> presenterWeakRef;
        private final int type;

        public MyLoadMomentsCallback(MomentsPresenter presenter, int type) {
            this.presenterWeakRef = new WeakReference<>(presenter);
            this.type = type;
        }

        @Override
        public void onMomentsLoaded(List<MomentEntity> momentEntities) {
            MomentsPresenter presenter = presenterWeakRef.get();
            if (presenter != null) {
                if (type == TYPE_INIT) {
                    presenter.onLoadInitMoments(momentEntities);
                } else if (type == TYPE_RELOAD) {
                    presenter.onReloadMoments(momentEntities);
                } else {
                    presenter.onLoadMoreMoments(momentEntities);
                }
            }
        }

        @Override
        public void onFailure(Throwable t) {

        }
    }
}
