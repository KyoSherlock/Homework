package com.kyo.homework.ui.moments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kyo.homework.R;
import com.kyo.homework.data.MomentEntity;
import com.kyo.homework.data.UserEntity;

import java.util.List;

import space.sye.z.library.RefreshRecyclerView;
import space.sye.z.library.listener.OnBothRefreshListener;
import space.sye.z.library.listener.OnLoadMoreListener;
import space.sye.z.library.manager.RecyclerMode;
import space.sye.z.library.manager.RecyclerViewManager;

/**
 * Created by jianghui on 2017/11/29.
 */

public class MomentsActivity extends AppCompatActivity implements MomentsContract.View {

    private MomentsContract.Presenter presenter;
    // --
    private RefreshRecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MomentsAdapter momentsAdapter;
    // --
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isLoading = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_moments);

        swipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        // --
        recyclerView = (RefreshRecyclerView) this.findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        momentsAdapter = new MomentsAdapter(this);
        RecyclerViewManager.with(momentsAdapter, new LinearLayoutManager(this))
                .setMode(RecyclerMode.BOTTOM)
                .setOnLoadMoreListener(onLoadMoreListener)
                .into(recyclerView, this);


        // --
        this.presenter = new MomentsPresenter(this);
        this.presenter.loadUser();
        this.presenter.loadInitMoments();


    }

    @Override
    public boolean isAvailable() {
        return !isFinishing();
    }

    @Override
    public void showUser(UserEntity userEntity) {
        momentsAdapter.setUserInfo(userEntity);
        momentsAdapter.notifyDataSetChanged();
        momentsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showInitMoments(List<MomentEntity> momentEntities) {
        momentsAdapter.setMoments(momentEntities);
        momentsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreMoments(List<MomentEntity> momentEntities) {
        int position = momentsAdapter.getItemCount();
        momentsAdapter.addMoments(momentEntities);
        momentsAdapter.notifyItemInserted(position);
    }

    @Override
    public void hideLoadMoreLayout() {
        recyclerView.onRefreshCompleted();
        swipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void stopLoadMoreLayout() {
        recyclerView.onRefreshCompleted();
        swipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void hideRefreshLayout() {
        swipeRefreshLayout.setRefreshing(false);
        recyclerView.setMode(RecyclerMode.BOTTOM);
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            presenter.reloadMoments();
            recyclerView.setMode(RecyclerMode.NONE);
        }
    };
    private OnLoadMoreListener onLoadMoreListener = new OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            presenter.loadMoreMoments();
            swipeRefreshLayout.setEnabled(false);
        }
    };
}
