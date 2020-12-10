package com.example.app_libsys;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_libsys.board.BoardCategoryActivity;
import com.example.app_libsys.book.BookCategoryActivity;
import com.example.app_libsys.mypage.MyPageActivity;
import com.example.app_libsys.note.PagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class AdminMessageActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private PagerAdapter adapter;

    private DrawerLayout drawerLayout;
    private View drawerView;
    public String response_my_page = "";
    private AlertDialog dialog;
    private static final String URL_MY_INFO = "http://www.minback.com/users/myinfo";
    private RequestQueue queue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_message);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("잠시만 기다려 주세요...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        queue = Volley.newRequestQueue(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerView = findViewById(R.id.drawer);

        ImageButton btn_open = findViewById(R.id.btn_open);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        final Intent adminMessageIntent = getIntent();
        final String id_res = adminMessageIntent.getStringExtra("id");
        final String nickname_res = adminMessageIntent.getStringExtra("nickname");

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
                Intent boardCategoryIntent =  new Intent(AdminMessageActivity.this, BoardCategoryActivity.class);
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
            }
        });

        LinearLayout bookItemLayout = findViewById(R.id.bookItemButton);
        bookItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
                Intent bookCategoryIntent = new Intent(AdminMessageActivity.this, BookCategoryActivity.class);
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
                        Intent myPageIntent = new Intent(AdminMessageActivity.this, MyPageActivity.class);
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
                Intent loginIntent = new Intent(AdminMessageActivity.this, LoginActivity.class);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminMessageActivity.this);
                dialog = builder.setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("확인", loginListener)
                        .setNegativeButton("취소", null)
                        .create();
                dialog.show();
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("쪽지 쓰기"));
        tabLayout.addTab(tabLayout.newTab().setText("받은 쪽지함"));
        tabLayout.addTab(tabLayout.newTab().setText("보낸 쪽지함"));

        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), id_res);
        viewPager= findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }

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