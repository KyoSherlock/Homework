package com.kyo.homework.ui.moments;

import com.kyo.homework.data.MomentEntity;
import com.kyo.homework.data.UserEntity;
import com.kyo.homework.data.source.MomentsDataSource;
import com.kyo.homework.ui.BasePresenter;
import com.kyo.homework.ui.BaseView;

import java.util.List;

/**
 * Created by jianghui on 2017/11/29.
 */

public class MomentsContract {

    interface View extends BaseView<Presenter> {
        void showUser(UserEntity userEntity);

        void showInitMoments(List<MomentEntity> momentEntities);

        void showMoreMoments(List<MomentEntity> momentEntities);

        void hideLoadMoreLayout();

        void stopLoadMoreLayout();

        void hideRefreshLayout();
    }

    interface Presenter extends BasePresenter {

        void loadUser();

        void loadInitMoments();

        void reloadMoments();

        void loadMoreMoments();
    }
}
