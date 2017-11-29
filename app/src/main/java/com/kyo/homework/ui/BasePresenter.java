package com.kyo.homework.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by jianghui on 2017/11/29.
 */

public interface BasePresenter {
    void onCreate(@Nullable Bundle savedInstanceState);

    void onDestroy();
}
