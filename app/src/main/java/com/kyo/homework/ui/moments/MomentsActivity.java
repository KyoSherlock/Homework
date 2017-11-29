package com.kyo.homework.ui.moments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kyo.homework.data.MomentEntity;
import com.kyo.homework.data.UserEntity;
import com.kyo.homework.ui.BaseView;

import java.util.List;

/**
 * Created by jianghui on 2017/11/29.
 */

public class MomentsActivity extends AppCompatActivity implements MomentsContract.View {

    private MomentsContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setPresenter(MomentsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isAvailable() {
        return !isFinishing();
    }

    @Override
    public void showUser(UserEntity userEntity) {

    }

    @Override
    public void showInitMoments(List<MomentEntity> momentEntities) {

    }

    @Override
    public void showMoreMoments(List<MomentEntity> momentEntities) {

    }

    @Override
    public void hideLoadMoreLayout() {

    }

    @Override
    public void hideRefreshLayout() {

    }

}
