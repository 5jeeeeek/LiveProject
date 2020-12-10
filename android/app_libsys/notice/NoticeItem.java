package com.example.app_libsys.notice;

import android.os.Parcel;
import android.os.Parcelable;

public class NoticeItem implements Parcelable {
    private String title;
    private String text;
    private String name;
    private String image;
    private String date;
    private int tcode;


    protected NoticeItem(Parcel in) {
        title = in.readString();
        text = in.readString();
        name = in.readString();
        image = in.readString();
        date = in.readString();
        tcode = in.readInt();
    }

    public static final Creator<NoticeItem> CREATOR = new Creator<NoticeItem>() {
        @Override
        public NoticeItem createFromParcel(Parcel in) {
            return new NoticeItem(in);
        }

        @Override
        public NoticeItem[] newArray(int size) {
            return new NoticeItem[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public int getTcode() {
        return tcode;
    }

    public NoticeItem(String title, String text, String name, int tcode, String image, String date) {
        this.title = title;
        this.text = text;
        this.name = name;
        this.tcode = tcode;
        this.image = image;
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(text);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(date);
        dest.writeInt(tcode);
    }
}