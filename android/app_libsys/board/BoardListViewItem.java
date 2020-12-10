package com.example.app_libsys.board;

import android.graphics.drawable.Drawable;


public class BoardListViewItem {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String descStr ;
    private String userIdStr ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }
    public void setUserId(String userId) {
        userIdStr = userId ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
    public String getUserId() {
        return this.userIdStr ;
    }
}
