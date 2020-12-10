package com.example.app_libsys.board.bitmap;

import android.graphics.Bitmap;

public class NoneBitmapImage extends BitmapImage {

    public NoneBitmapImage(Bitmap bitmap) {
        super(bitmap);
    }

    @Override
    public byte[] getFileDataFromDrawable() {

        return null;
    }
}
