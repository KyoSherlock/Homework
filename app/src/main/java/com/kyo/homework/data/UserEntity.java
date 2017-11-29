package com.kyo.homework.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jianghui on 2017/11/29.
 */

public class UserEntity {
    @SerializedName("profile-image")
    public String profileImage;
    public String avatar;
    public String nick;
    public String username;

}
