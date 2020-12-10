package com.example.app_libsys.book;

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
import com.example.app_libsys.board.BoardCategoryActivity;
import com.example.app_libsys.mypage.MyPageActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BookCategoryActivity extends AppCompatActivity {
    Button allButton, searchButton, fictionButton, humanitiesButton, historyButton, artButton,
            societyButton, scienceButton, economyButton, selfDevelopmentButton,
            tripButton, cookButton, foreignButton, itButton;
    private static final String URL_ITEM_LIST = "http://www.minback.com/item/itemlist";
    private static final String URL_ITEM_CATE_LIST = "http://www.minback.com/item/itemcatelist";
    private static final String URL_MY_INFO = "http://www.minback.com/users/myinfo";
    private RequestQueue queue;
    public String number = "1";
    private String itemListResponse = "";
    private String itemCateListResponse = "";
    private String category = "";

    private DrawerLayout drawerLayout;
    private View drawerView;
    public String response_my_page = "";
    private AlertDialog dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_category);

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

        final Intent bookCategoryIntent = getIntent();
        final String id_res = bookCategoryIntent.getStringExtra("id");
        final String nickname_res = bookCategoryIntent.getStringExtra("nickname");
        final String icon_res = bookCategoryIntent.getStringExtra("icon");

        allButton = findViewById(R.id.categoryAll);
        searchButton = findViewById(R.id.categorySearch);
        fictionButton = findViewById(R.id.categoryFiction);
        humanitiesButton = findViewById(R.id.categoryHumanities);
        historyButton = findViewById(R.id.categoryHistory);
        artButton = findViewById(R.id.categoryArt);
        societyButton = findViewById(R.id.categorySociety);
        scienceButton = findViewById(R.id.categoryScience);
        economyButton = findViewById(R.id.categoryEconomy);
        selfDevelopmentButton = findViewById(R.id.categorySelfDevelopment);
        tripButton = findViewById(R.id.categoryTrip);
        cookButton = findViewById(R.id.categoryCook);
        foreignButton = findViewById(R.id.categoryForeign);
        itButton = findViewById(R.id.categoryIT);

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
                Intent boardCategoryIntent =  new Intent(BookCategoryActivity.this, BoardCategoryActivity.class);
                boardCategoryIntent.putExtra("id", id_res);
                boardCategoryIntent.putExtra("nickname", nickname_res);
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
                Intent adminMessageIntent = new Intent(BookCategoryActivity.this, AdminMessageActivity.class);
                adminMessageIntent.putExtra("id", id_res);
                adminMessageIntent.putExtra("nickname", id_res);
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
                        Intent myPageIntent = new Intent(BookCategoryActivity.this, MyPageActivity.class);
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
                Intent loginIntent = new Intent(BookCategoryActivity.this, LoginActivity.class);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(BookCategoryActivity.this);
                dialog = builder.setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("확인", loginListener)
                        .setNegativeButton("취소", null)
                        .create();
                dialog.show();
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        final StringRequest stringItemListRequest = new StringRequest(Request.Method.POST, URL_ITEM_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                itemListResponse = response;
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

        final StringRequest stringItemCateListRequest = new StringRequest(Request.Method.POST, URL_ITEM_CATE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                itemCateListResponse = response;
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
                params.put("icate", category);
                return params;
            }
        };



        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                queue.add(stringItemListRequest);

                Handler timer = new Handler();
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Intent categoryIntent = new Intent(BookCategoryActivity.this, BookActivity.class);
                        categoryIntent.putExtra("id", id_res);
                        categoryIntent.putExtra("nickname", nickname_res);
                        categoryIntent.putExtra("icon", icon_res);
                        categoryIntent.putExtra("itemListResponse", itemListResponse);
                        categoryIntent.putExtra("url", URL_ITEM_LIST);
                        startActivity(categoryIntent);
                        overridePendingTransition(R.anim.in, R.anim.out);
                    }
                }, 500);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookSearchIntent = new Intent(BookCategoryActivity.this, BookSearchActivity.class);
                bookSearchIntent.putExtra("id", id_res);
                bookSearchIntent.putExtra("nickname", nickname_res);
                bookSearchIntent.putExtra("icon", icon_res);
                startActivity(bookSearchIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        View.OnClickListener categoryClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.categoryFiction:
                        category = "소설";
                        break;
                    case R.id.categoryHumanities:
                        category = "인문";
                        break;
                    case R.id.categoryHistory:
                        category = "역사";
                        break;
                    case R.id.categoryArt:
                        category = "예술";
                        break;
                    case R.id.categorySociety:
                        category = "사회";
                        break;
                    case R.id.categoryScience:
                        category = "과학";
                        break;
                    case R.id.categoryEconomy:
                        category = "경제";
                        break;
                    case R.id.categorySelfDevelopment:
                        category = "자기계발";
                        break;
                    case R.id.categoryTrip:
                        category = "여행";
                        break;
                    case R.id.categoryCook:
                        category = "요리";
                        break;
                    case R.id.categoryForeign:
                        category = "외국어";
                        break;
                    case R.id.categoryIT:
                        category = "IT";
                        break;

                }
                progressDialog.show();
                queue.add(stringItemCateListRequest);
                Handler timer = new Handler();
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Intent categoryIntent = new Intent(BookCategoryActivity.this, BookActivity.class);
                        categoryIntent.putExtra("id", id_res);
                        categoryIntent.putExtra("nickname", nickname_res);
                        categoryIntent.putExtra("icon", icon_res);
                        categoryIntent.putExtra("itemListResponse", itemCateListResponse);
                        categoryIntent.putExtra("url", URL_ITEM_CATE_LIST);

                        startActivity(categoryIntent);
                        overridePendingTransition(R.anim.in, R.anim.out);
                    }
                }, 500);
            }
        };

        fictionButton.setOnClickListener(categoryClickListener);
        humanitiesButton.setOnClickListener(categoryClickListener);
        historyButton.setOnClickListener(categoryClickListener);
        artButton.setOnClickListener(categoryClickListener);
        societyButton.setOnClickListener(categoryClickListener);
        scienceButton.setOnClickListener(categoryClickListener);
        economyButton.setOnClickListener(categoryClickListener);
        selfDevelopmentButton.setOnClickListener(categoryClickListener);
        tripButton.setOnClickListener(categoryClickListener);
        cookButton.setOnClickListener(categoryClickListener);
        foreignButton.setOnClickListener(categoryClickListener);
        itButton.setOnClickListener(categoryClickListener);
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
