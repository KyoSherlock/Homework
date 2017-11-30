package com.kyo.homework.util;

import android.content.Context;

/**
 * Created by jianghui on 2017/11/30.
 */

public class ContextUtil {

    public static int dp2px(Context context, int dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }
}
