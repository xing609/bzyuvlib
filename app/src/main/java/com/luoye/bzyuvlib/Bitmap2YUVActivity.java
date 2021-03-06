package com.luoye.bzyuvlib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;

public class Bitmap2YUVActivity extends AppCompatActivity {

    private static final String TAG = "bz_Bitmap2YUVActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap2_y_u_v);
        ImageView ivImageSrc = findViewById(R.id.iv_image_src);
        ImageView ivImageDis = findViewById(R.id.iv_image_dis);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.timg);

//        bitmap = convertRGB565(bitmap);

        ivImageSrc.setImageBitmap(bitmap);
        Log.d(TAG, "getWidth=" + bitmap.getWidth() + " getHeight=" + bitmap.getHeight());

        byte[] yuvBuffer = new byte[bitmap.getWidth() * bitmap.getHeight() * 3 / 2];
        BZYUVUtil.bitmapToYUV420(bitmap, yuvBuffer);


        byte[] rgbaBuffer = new byte[bitmap.getWidth() * bitmap.getHeight() * 4];
        BZYUVUtil.yuv420ToRGBA(yuvBuffer, rgbaBuffer, bitmap.getWidth(), bitmap.getHeight(), false, 0);
        Bitmap disBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        disBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(rgbaBuffer));

        ivImageDis.setImageBitmap(disBitmap);
    }

    private Bitmap convertRGB565(Bitmap bitmap) {
        Bitmap disBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(disBitmap);
        canvas.drawBitmap(bitmap, 0, 0, new Paint());
        return disBitmap;
    }

}
