package com.example.app_libsys.note;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageItem implements Parcelable {
    private String ntitle;
    private String ntext;
    private String nsend;
    private String nreceive;
    private String ndate;
    private String nimage;
    private int ncode;
    private int nstate;


    protected MessageItem(Parcel in) {
        ntitle = in.readString();
        ntext = in.readString();
        nsend = in.readString();
        nreceive = in.readString();
        ndate = in.readString();
        nimage = in.readString();
        ncode = in.readInt();
        nstate = in.readInt();
    }

    public static final Creator<MessageItem> CREATOR = new Creator<MessageItem>() {
        @Override
        public MessageItem createFromParcel(Parcel in) {
            return new MessageItem(in);
        }

        @Override
        public MessageItem[] newArray(int size) {
            return new MessageItem[size];
        }
    };

    public String getNtitle() {
        return ntitle;
    }

    public String getNtext() {
        return ntext;
    }

    public String getNsend() {
        return nsend;
    }

    public String getNreceive() {
        return nreceive;
    }

    public String getNdate() {
        return ndate;
    }

    public String getNimage() {
        return nimage;
    }

    public int getNcode() {
        return ncode;
    }

    public int getNstate() {
        return nstate;
    }


    public MessageItem(String ntitle, String ntext, String nsend, String nreceive, int ncode, int nstate, String ndate, String nimage) {
        this.ntitle = ntitle;
        this.ntext = ntext;
        this.nsend = nsend;
        this.nreceive = nreceive;
        this.ncode = ncode;
        this.nstate = nstate;
        this.ndate = ndate;
        this.nimage = nimage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ntitle);
        dest.writeString(ntext);
        dest.writeString(nsend);
        dest.writeString(nreceive);
        dest.writeString(ndate);
        dest.writeString(nimage);
        dest.writeInt(ncode);
        dest.writeInt(nstate);
    }
}