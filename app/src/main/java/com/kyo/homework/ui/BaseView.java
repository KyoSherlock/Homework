package com.kyo.homework.ui;

/**
 * Created by jianghui on 2017/11/29.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    boolean isAvailable();
}
