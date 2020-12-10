package com.example.app_libsys.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.app_libsys.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SendDetailActivity extends AppCompatActivity {
    private RequestQueue queue;
    private static final String URL_NOTE_DELETE = "http://www.minback.com/note/notedel";
    private final String URL_IMAGE = "http://minback.com/upimg/";

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_send_detail);

        Intent MessageClickedIntent = getIntent();
        final String id_res = MessageClickedIntent.getStringExtra("id");
        final ArrayList<MessageItem> messageItemArrayList = MessageClickedIntent.getParcelableArrayListExtra("messageItemArrayList");
        final int position = MessageClickedIntent.getIntExtra("position", 0);

        queue = Volley.newRequestQueue(this);

        final ImageView detail_image = findViewById(R.id.detail_image);
        TextView detail_title = findViewById(R.id.detail_title);
        TextView detail_text = findViewById(R.id.detail_text);
        TextView detail_date2 = findViewById(R.id.detail_date2);

        TextView boardUpdate = findViewById(R.id.boardUpdate);
        TextView boardDelete = findViewById(R.id.boardDelete);

        String image_url = messageItemArrayList.get(position).getNimage();

        detail_title.setText(messageItemArrayList.get(position).getNtitle());
        detail_text.setText(messageItemArrayList.get(position).getNtext());
        detail_date2.setText(messageItemArrayList.get(position).getNdate());

        final ImagePopup imagePopup = new ImagePopup(this);

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
//                Toast.makeText(BoardDetailActivity.this,"이미지가 없는 글", Toast.LENGTH_SHORT).show();
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


        if (id_res.equals(messageItemArrayList.get(position).getNsend())) {
            boardUpdate.setVisibility(View.INVISIBLE);
            boardDelete.setVisibility(View.VISIBLE);
        }

        final StringRequest stringNoteDeleteRequest = new StringRequest(Request.Method.POST, URL_NOTE_DELETE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("ncode", Integer.toString(messageItemArrayList.get(position).getNcode()));
                return params;
            }
        };

        final DialogInterface.OnClickListener boardDeleteListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                queue.add(stringNoteDeleteRequest);
                finish();
            }
        };


        boardDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SendDetailActivity.this);
                dialog = builder.setMessage("글을 삭제하시겠습니까?")
                        .setPositiveButton("확인", boardDeleteListener)
                        .setNegativeButton("취소", null)
                        .create();
                dialog.show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
    }
}
