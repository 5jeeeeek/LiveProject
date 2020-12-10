package com.example.app_libsys.board.bitmap;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class UploadBitmapImage extends BitmapImage {
    private Bitmap bitmap;
    public UploadBitmapImage(Bitmap bitmap) {
        super(bitmap);
        this.bitmap = bitmap;
    }

    @Override
    public byte[] getFileDataFromDrawable() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
