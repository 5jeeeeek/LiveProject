package com.example.app_libsys.board;

import android.os.Parcel;
import android.os.Parcelable;

public class BoardItem implements Parcelable {
    private String title;
    private String text;
    private String nickname;
    private String cate;
    private String image;
    private String date;
    private String comment;
    private int bcode;
    private int like;
    private int views;


    protected BoardItem(Parcel in) {
        title = in.readString();
        text = in.readString();
        nickname = in.readString();
        cate = in.readString();
        image = in.readString();
        date = in.readString();
        comment = in.readString();
        bcode = in.readInt();
        like = in.readInt();
        views = in.readInt();
    }

    public static final Creator<BoardItem> CREATOR = new Creator<BoardItem>() {
        @Override
        public BoardItem createFromParcel(Parcel in) {
            return new BoardItem(in);
        }

        @Override
        public BoardItem[] newArray(int size) {
            return new BoardItem[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCate() {
        return cate;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public int getBcode() {
        return bcode;
    }

    public int getLike() {
        return like;
    }

    public int getViews() {
        return views;
    }


    public BoardItem(String title, String text, String nickname, int bcode, String cate, String image, String date, int like, int views, String comment) {
        this.title = title;
        this.text = text;
        this.nickname = nickname;
        this.bcode = bcode;
        this.cate = cate;
        this.image = image;
        this.date = date;
        this.like = like;
        this.views = views;
        this.comment = comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(text);
        dest.writeString(nickname);
        dest.writeString(cate);
        dest.writeString(image);
        dest.writeString(date);
        dest.writeString(comment);
        dest.writeInt(bcode);
        dest.writeInt(like);
        dest.writeInt(views);
    }
}