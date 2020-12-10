package com.example.app_libsys.mypage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_libsys.AdminMessageActivity;
import com.example.app_libsys.LoginActivity;
import com.example.app_libsys.MainActivity;
import com.example.app_libsys.R;
import com.example.app_libsys.board.BoardActivity;
import com.example.app_libsys.board.BoardCategoryActivity;
import com.example.app_libsys.book.BookActivity;
import com.example.app_libsys.book.BookCategoryActivity;
import com.example.app_libsys.finduser.PWConfirmActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MyPageActivity extends AppCompatActivity {
    public static Activity _MyPageActivity;
    private static final String URL_MY_BOARD = "http://www.minback.com/users/myboard";
    private static final String URL_MY_WISH = "http://www.minback.com/item/wishlist";
    private String myBoardListResponse = "";
    private String myWishListResponse = "";
    private RequestQueue queue;
    private AlertDialog dialog;

    private DrawerLayout drawerLayout;
    private View drawerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("잠시만 기다려 주세요...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerView = findViewById(R.id.drawer);

        ImageButton btn_open = findViewById(R.id.btn_open);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        queue = Volley.newRequestQueue(this);
        _MyPageActivity = MyPageActivity.this;

        TextView myPagenameView = findViewById(R.id.myPagenameView);
        TextView myPagenicknameView = findViewById(R.id.myPagenicknameView);
        TextView myPageemailView = findViewById(R.id.myPageemailView);

        final Intent myPageIntent = getIntent();

        final String parse_res = myPageIntent.getStringExtra("response_var");

        final StringBuilder nameBuilder = new StringBuilder();
        final StringBuilder nicknameBuilder = new StringBuilder();
        final StringBuilder emailBuilder = new StringBuilder();
        final StringBuilder pwBuilder = new StringBuilder();
        final StringBuilder idBuilder = new StringBuilder();
        final StringBuilder phoneBuilder = new StringBuilder();
        final StringBuilder questionBuilder = new StringBuilder();
        final StringBuilder answerBuilder = new StringBuilder();


        try {
            JSONArray jsonArray = new JSONArray(parse_res);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String json_id = jsonObject.getString("id");
                String json_pw = jsonObject.getString("pw");
                String json_email = jsonObject.getString("email");
                String json_name = jsonObject.getString("name");
                String json_nickname = jsonObject.getString("nickname");
                String json_phone = jsonObject.getString("phone");
                String json_question = jsonObject.getString("question");
                String json_answer = jsonObject.getString("answer");

                nameBuilder.append(json_name);
                nicknameBuilder.append(json_nickname);
                emailBuilder.append(json_email);
                pwBuilder.append(json_pw);
                idBuilder.append(json_id);
                phoneBuilder.append(json_phone);
                questionBuilder.append(json_question);
                answerBuilder.append(json_answer);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        myPagenameView.setText(nameBuilder);
        myPagenicknameView.setText(nicknameBuilder);
        myPageemailView.setText(emailBuilder);


        final DialogInterface.OnClickListener loginListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveIdAndPW();
                Intent loginIntent = new Intent(MyPageActivity.this, LoginActivity.class);
                MyPageActivity.this.startActivity(loginIntent);
                MainActivity MA = (MainActivity) MainActivity._Main_Activity;
                MA.finish();
                finish();
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        };

        TextView idView = findViewById(R.id.drawerNicknameView);
        idView.setText(idBuilder.toString());

        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("YYYY/MM/dd HH:mm", Locale.getDefault()).format(currentTime);
        TextView recentLoginView = findViewById(R.id.recent_login);
        recentLoginView.setText(date_text);

        LinearLayout homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
                finish();
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        LinearLayout boardButton = findViewById(R.id.boardButton);
        boardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
                Intent boardCategoryIntent =  new Intent(MyPageActivity.this, BoardCategoryActivity.class);
                boardCategoryIntent.putExtra("id", idBuilder.toString());
                boardCategoryIntent.putExtra("nickname", nicknameBuilder.toString());
                finish();
                startActivity(boardCategoryIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        LinearLayout adminMessageButton = findViewById(R.id.adminMessageButton);
        adminMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
                Intent adminMessageIntent = new Intent(MyPageActivity.this, AdminMessageActivity.class);
                adminMessageIntent.putExtra("id", idBuilder.toString());
                adminMessageIntent.putExtra("nickname", nicknameBuilder.toString());
                finish();
                startActivity(adminMessageIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        LinearLayout bookItemLayout = findViewById(R.id.bookItemButton);
        bookItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
                Intent bookCategoryIntent = new Intent(MyPageActivity.this, BookCategoryActivity.class);
                bookCategoryIntent.putExtra("id", idBuilder.toString());
                bookCategoryIntent.putExtra("nickname", nicknameBuilder.toString());
                bookCategoryIntent.putExtra("icon", "false");
                finish();
                startActivity(bookCategoryIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        LinearLayout myPageButton = findViewById(R.id.myPageButton);
        myPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
            }
        });

        ImageButton logoutToolbarButton = findViewById(R.id.logout_btn);
        logoutToolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
                AlertDialog.Builder builder = new AlertDialog.Builder(MyPageActivity.this);
                dialog = builder.setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("확인", loginListener)
                        .setNegativeButton("취소", null)
                        .create();
                dialog.show();
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        Button myInfoButton = findViewById(R.id.myInfoButton);
        myInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_send = idBuilder.toString();
                String pw_send = pwBuilder.toString();
                String email_send = emailBuilder.toString();
                String name_send = nameBuilder.toString();
                String nickname_send = nicknameBuilder.toString();
                String phone_send = phoneBuilder.toString();
                String question_send = questionBuilder.toString();
                String answer_send = answerBuilder.toString();

                Intent pwConfirmIntent = new Intent(MyPageActivity.this, PWConfirmActivity.class);
                pwConfirmIntent.putExtra("id", id_send);
                pwConfirmIntent.putExtra("pw", pw_send);
                pwConfirmIntent.putExtra("email", email_send);
                pwConfirmIntent.putExtra("name", name_send);
                pwConfirmIntent.putExtra("nickname", nickname_send);
                pwConfirmIntent.putExtra("phone", phone_send);
                pwConfirmIntent.putExtra("question", question_send);
                pwConfirmIntent.putExtra("answer", answer_send);
                startActivity(pwConfirmIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        final StringRequest stringMyBoardListRequest = getBoardList(idBuilder);
        final StringRequest stringMyFavoriteListRequest = getFavoriteList(idBuilder);

        Button myPostButton = findViewById(R.id.myPostButton);
        myPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                queue.add(stringMyBoardListRequest);

                Handler timer = new Handler();
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Intent boardIntent =  new Intent(MyPageActivity.this, BoardActivity.class);
                        boardIntent.putExtra("id", idBuilder.toString());
                        boardIntent.putExtra("nickname", nicknameBuilder.toString());
                        boardIntent.putExtra("response_var", myBoardListResponse);
                        boardIntent.putExtra("url", URL_MY_BOARD);
                        startActivity(boardIntent);
                        overridePendingTransition(R.anim.in, R.anim.out);
                    }
                }, 500);
            }
        });

        Button devMessageButton = findViewById(R.id.devMessageButton);
        devMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminMessageIntent = new Intent(MyPageActivity.this, AdminMessageActivity.class);
                adminMessageIntent.putExtra("id", idBuilder.toString());
                startActivity(adminMessageIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        Button myFavoriteButton = findViewById(R.id.myFavoriteButton);
        myFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                queue.add(stringMyFavoriteListRequest);

                Handler timer = new Handler();
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Intent wishIntent =  new Intent(MyPageActivity.this, BookActivity.class);
                        wishIntent.putExtra("id", idBuilder.toString());
                        wishIntent.putExtra("nickname", nicknameBuilder.toString());
                        wishIntent.putExtra("itemListResponse", myWishListResponse);
                        wishIntent.putExtra("url", URL_MY_WISH);
                        wishIntent.putExtra("icon", "true");
                        startActivity(wishIntent);
                        overridePendingTransition(R.anim.in, R.anim.out);
                    }
                }, 500);
            }
        });

        LinearLayout devButton = findViewById(R.id.devButton);
        devButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent devIntent = new Intent(MyPageActivity.this, DevActivity.class);
                startActivity(devIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        LinearLayout logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyPageActivity.this);
                dialog = builder.setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("확인", loginListener)
                        .setNegativeButton("취소", null)
                        .create();
                dialog.show();
            }
        });
    }

    private StringRequest getFavoriteList(final StringBuilder idBuilder) {
        return new StringRequest(Request.Method.POST, URL_MY_WISH, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                myWishListResponse = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", idBuilder.toString());
                return params;
            }
        };
    }

    private StringRequest getBoardList(final StringBuilder idBuilder) {
        return new StringRequest(Request.Method.POST, URL_MY_BOARD, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                myBoardListResponse = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", idBuilder.toString());
                return params;
            }
        };
    }


    private void saveIdAndPW() {
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", "");
        editor.putString("pw", "");
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(drawerView)) {
            drawerLayout.closeDrawer(drawerView);
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.in, R.anim.out);
        }
    }
}
