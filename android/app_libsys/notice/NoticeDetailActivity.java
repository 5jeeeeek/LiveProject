package com.example.app_libsys.notice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.app_libsys.R;

import java.util.ArrayList;

public class NoticeDetailActivity extends AppCompatActivity {

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        queue = Volley.newRequestQueue(this);


        Intent NoticeClickedIntent = getIntent();
        final ArrayList<NoticeItem> noticeItemArrayList = NoticeClickedIntent.getParcelableArrayListExtra("noticeItemArrayList");
        final int position = NoticeClickedIntent.getIntExtra("position", 0);


        final ImageView detail_image = findViewById(R.id.detail_image);
        TextView detail_title = findViewById(R.id.detail_title);
        TextView detail_text = findViewById(R.id.detail_text);
        TextView detail_name = findViewById(R.id.detail_name);
        TextView detail_date = findViewById(R.id.detail_notice_date);



        String image_url = noticeItemArrayList.get(position).getImage();

        detail_title.setText(noticeItemArrayList.get(position).getTitle());
        detail_text.setText(noticeItemArrayList.get(position).getText());
        detail_name.setText(noticeItemArrayList.get(position).getName());
        detail_date.setText(noticeItemArrayList.get(position).getDate());

        final ImagePopup imagePopup = new ImagePopup(this);

        String URL_IMAGE = "http://minback.com/upimg/";
        final ImageRequest imageRequest = new ImageRequest(URL_IMAGE + image_url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                detail_image.setImageBitmap(response);

//                imagePopup.setWindowHeight(800); // Optional
//                imagePopup.setWindowWidth(800); // Optional
                imagePopup.setBackgroundColor(Color.BLACK);  // Optional
                imagePopup.setFullScreen(true); // Optional
                imagePopup.setHideCloseIcon(true);  // Optional
//                imagePopup.setImageOnClickClose(true);  // Optional
                imagePopup.initiatePopup(detail_image.getDrawable());
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                detail_image.setVisibility(View.GONE);
                error.printStackTrace();
            }
        });
        queue.add(imageRequest);


        detail_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.viewPopup();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
    }
}
