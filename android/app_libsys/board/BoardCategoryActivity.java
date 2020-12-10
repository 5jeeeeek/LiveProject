package com.example.app_libsys.board;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
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
import com.example.app_libsys.book.BookCategoryActivity;
import com.example.app_libsys.mypage.MyPageActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BoardCategoryActivity extends AppCompatActivity {
    Button board1Button, board2Button, board3Button;
    private static final String URL_BOARD_LIST1 = "http://www.minback.com/board/boardlist1";
    private static final String URL_BOARD_LIST2 = "http://www.minback.com/board/boardlist2";
    private static final String URL_BOARD_LIST3 = "http://www.minback.com/board/boardlist3";
    private static final String URL_MY_INFO = "http://www.minback.com/users/myinfo";

    private RequestQueue queue;
    public String number = "1";
    private String boardListResponse1 = "";
    private String boardListResponse2 = "";
    private String boardListResponse3 = "";

    private DrawerLayout drawerLayout;
    private View drawerView;
    public String response_my_page = "";
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_category);

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

        final Intent boardCategoryIntent = getIntent();
        final String id_res = boardCategoryIntent.getStringExtra("id");
        final String nickname_res = boardCategoryIntent.getStringExtra("nickname");

        board1Button = findViewById(R.id.board1);
        board2Button = findViewById(R.id.board2);
        board3Button = findViewById(R.id.board3);

        TextView idView = findViewById(R.id.drawerNicknameView);
        idView.setText(id_res);

        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("YYYY/MM/dd HH:mm", Locale.getDefault()).format(currentTime);
        TextView recentLoginView = findViewById(R.id.recent_login);
        recentLoginView.setText(date_text);

        final StringRequest stringMyPageRequest = new StringRequest(Request.Method.POST, URL_MY_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                response_my_page = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id_res);
                return params;
            }
        };

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
            }
        });

        LinearLayout adminMessageButton = findViewById(R.id.adminMessageButton);
        adminMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
                Intent adminMessageIntent = new Intent(BoardCategoryActivity.this, AdminMessageActivity.class);
                adminMessageIntent.putExtra("id", id_res);
                adminMessageIntent.putExtra("nickname", nickname_res);
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
                Intent bookCategoryIntent = new Intent(BoardCategoryActivity.this, BookCategoryActivity.class);
                bookCategoryIntent.putExtra("id", id_res);
                bookCategoryIntent.putExtra("nickname", nickname_res);
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
                progressDialog.show();
                queue.add(stringMyPageRequest);

                Handler timer = new Handler();
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        drawerLayout.closeDrawer(drawerView);
                        Intent myPageIntent = new Intent(BoardCategoryActivity.this, MyPageActivity.class);
                        myPageIntent.putExtra("response_var", response_my_page);
                        finish();
                        startActivity(myPageIntent);
                        overridePendingTransition(R.anim.in, R.anim.out);
                    }
                }, 500);
            }
        });

        final DialogInterface.OnClickListener loginListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveIdAndPW();
                Intent loginIntent = new Intent(BoardCategoryActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                MainActivity MA = (MainActivity) MainActivity._Main_Activity;
                MA.finish();
                finish();
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        };

        ImageButton logoutToolbarButton = findViewById(R.id.logout_btn);
        logoutToolbarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
                AlertDialog.Builder builder = new AlertDialog.Builder(BoardCategoryActivity.this);
                dialog = builder.setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("확인", loginListener)
                        .setNegativeButton("취소", null)
                        .create();
                dialog.show();
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent boardWriteIntent = new Intent(BoardCategoryActivity.this, BoardWriteActivity.class);
                boardWriteIntent.putExtra("id", id_res);
                boardWriteIntent.putExtra("nickname", nickname_res);
                BoardCategoryActivity.this.startActivity(boardWriteIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        final StringRequest stringBoardListRequest = new StringRequest(Request.Method.POST, URL_BOARD_LIST1, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                boardListResponse1 = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("number", number);
                return params;
            }
        };

        final StringRequest stringBoardListRequest2 = new StringRequest(Request.Method.POST, URL_BOARD_LIST2, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                boardListResponse2 = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("number", number);
                return params;
            }
        };

        final StringRequest stringBoardListRequest3 = new StringRequest(Request.Method.POST, URL_BOARD_LIST3, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                boardListResponse3 = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("number", number);
                return params;
            }
        };


        board1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                queue.add(stringBoardListRequest);

                Handler timer = new Handler();
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Intent boardIntent =  new Intent(BoardCategoryActivity.this, BoardActivity.class);
                        boardIntent.putExtra("id", id_res);
                        boardIntent.putExtra("nickname", nickname_res);
                        boardIntent.putExtra("response_var", boardListResponse1);
                        boardIntent.putExtra("url", URL_BOARD_LIST1);

                        startActivity(boardIntent);
                        overridePendingTransition(R.anim.in, R.anim.out);
                    }
                }, 500);

            }
        });

        board2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                queue.add(stringBoardListRequest2);

                Handler timer = new Handler();
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Intent boardIntent =  new Intent(BoardCategoryActivity.this, BoardActivity.class);
                        boardIntent.putExtra("id", id_res);
                        boardIntent.putExtra("nickname", nickname_res);
                        boardIntent.putExtra("response_var", boardListResponse2);
                        boardIntent.putExtra("url", URL_BOARD_LIST2);
                        startActivity(boardIntent);
                        overridePendingTransition(R.anim.in, R.anim.out);
                    }
                }, 500);
            }
        });

        board3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                queue.add(stringBoardListRequest3);

                Handler timer = new Handler();
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Intent boardIntent =  new Intent(BoardCategoryActivity.this, BoardActivity.class);
                        boardIntent.putExtra("id", id_res);
                        boardIntent.putExtra("nickname", nickname_res);
                        boardIntent.putExtra("response_var", boardListResponse3);
                        boardIntent.putExtra("url", URL_BOARD_LIST3);

                        startActivity(boardIntent);
                        overridePendingTransition(R.anim.in, R.anim.out);
                    }
                }, 500);
            }
        });
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
