package com.example.app_libsys.board.bitmap;

import android.graphics.Bitmap;

public abstract class BitmapImage {

    private Bitmap bitmap;

    public BitmapImage(Bitmap bitmap) {

        this.bitmap = bitmap;
    }

    public abstract byte[] getFileDataFromDrawable();

    public Bitmap getBitmap() {
        return bitmap;
    }
}
