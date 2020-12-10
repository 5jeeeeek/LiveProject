package com.example.app_libsys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_libsys.board.BoardCategoryActivity;
import com.example.app_libsys.book.BookCategoryActivity;
import com.example.app_libsys.mypage.MyPageActivity;
import com.example.app_libsys.notice.NoticeActivity;
import com.example.app_libsys.notice.NoticeDetailActivity;
import com.example.app_libsys.notice.NoticeItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    public static Activity _Main_Activity;
    private static final String URL_MY_INFO = "http://www.minback.com/users/myinfo";
    private static final String URL_NOTICE_LIST = "http://www.minback.com/notice/noticelist";
    private static final String URL_SENSOR_LIST = "http://www.minback.com/iot/sensorlist";
    private RequestQueue queue;
    public String response_notice = "";
    public String response_my_page = "";
    private DrawerLayout drawerLayout;
    private View drawerView;
    long pressedTime;
    private AlertDialog dialog;
    private TextView notice1, notice2, notice3, notice4, notice5, date1, date2, date3, date4, date5, availableView, unavailableView;
    private int position;
    private Button seatNumber50, seatNumber49, seatNumber48;

    private ArrayList<NoticeItem> noticeItemArrayList = new ArrayList<>();
    private ArrayList<Integer> tcodeList = new ArrayList<>();
    private ArrayList<String> ttitleList = new ArrayList<>();
    private ArrayList<String> ttextList = new ArrayList<>();
    private ArrayList<String> timageList = new ArrayList<>();
    private ArrayList<String> tnameList = new ArrayList<>();
    private ArrayList<String> tdateList = new ArrayList<>();
    private ArrayList<String> dateList = new ArrayList<>();

    private ArrayList<Integer> snumList = new ArrayList<>();
    private ArrayList<Integer> sstateList = new ArrayList<>();
    private ArrayList<String> snameList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        _Main_Activity = MainActivity.this;
        queue = Volley.newRequestQueue(this);

        notice1 = findViewById(R.id.notice_title1);
        notice2 = findViewById(R.id.notice_title2);
        notice3 = findViewById(R.id.notice_title3);
        notice4 = findViewById(R.id.notice_title4);
        notice5 = findViewById(R.id.notice_title5);
        date1 = findViewById(R.id.notice_date1);
        date2 = findViewById(R.id.notice_date2);
        date3 = findViewById(R.id.notice_date3);
        date4 = findViewById(R.id.notice_date4);
        date5 = findViewById(R.id.notice_date5);
        availableView = findViewById(R.id.available);
        unavailableView = findViewById(R.id.unavailable);


        ImageButton sensorRefreshButton = findViewById(R.id.sensorRefreshButton);
        seatNumber50 = findViewById(R.id.seatNumber50);
        seatNumber49 = findViewById(R.id.seatNumber49);
        seatNumber48 = findViewById(R.id.seatNumber48);

        final Intent loginIntent = getIntent();
        final String loginActivity_response = loginIntent.getStringExtra("response_var");

        String[] splitText= loginActivity_response.split(",");
        final String user_id = splitText[0].replace("\"", "").replace("[","");
        final String user_nickname = splitText[1].replace("\"", "").replace("]","");


        final StringRequest stringSensorListRequest = new StringRequest(Request.Method.POST, URL_SENSOR_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        snumList.add(jsonObject.getInt("snum"));
                        sstateList.add(jsonObject.getInt("sstate"));
                        snameList.add(jsonObject.getString("sname"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                int availableSeat = 52;
                int unavailableSeat = 55-availableSeat;

                if (sstateList.get(0).toString().equals("0")) {
                    seatNumber50.setBackgroundColor(Color.parseColor("#C0C0C0"));
                    availableSeat++;
                    unavailableSeat--;
                } else {
                    seatNumber50.setBackgroundColor(Color.parseColor("#CDD8A3"));
//                    availableSeat--;
//                    unavailableSeat++;
                }
                if (sstateList.get(1).toString().equals("0")) {
                    seatNumber49.setBackgroundColor(Color.parseColor("#C0C0C0"));
                } else {
                    seatNumber49.setBackgroundColor(Color.parseColor("#CDD8A3"));
                }
                if (sstateList.get(2).toString().equals("0")) {
                    seatNumber48.setBackgroundColor(Color.parseColor("#C0C0C0"));
                } else {
                    seatNumber48.setBackgroundColor(Color.parseColor("#CDD8A3"));
                }
                availableView.setText(String.valueOf(availableSeat));
                unavailableView.setText(String.valueOf(unavailableSeat));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };
        queue.add(stringSensorListRequest);

        sensorRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snumList.clear();
                sstateList.clear();
                snameList.clear();

                queue.add(stringSensorListRequest);
            }
        });

        final StringRequest stringNoticeListRequest = getStringNoticeListRequest();
        queue.add(stringNoticeListRequest);

        View.OnClickListener noticeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.notice_title1:
                        position = 0;
                        break;
                    case R.id.notice_title2:
                        position = 1;
                        break;
                    case R.id.notice_title3:
                        position = 2;
                        break;
                    case R.id.notice_title4:
                        position = 3;
                        break;
                    case R.id.notice_title5:
                        position = 4;
                        break;
                }
                Intent NoticeClickedIntent = new Intent(getApplicationContext(), NoticeDetailActivity.class);
                NoticeClickedIntent.putExtra("noticeItemArrayList", noticeItemArrayList);
                NoticeClickedIntent.putExtra("position", position);
                startActivity(NoticeClickedIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        };
        notice1.setOnClickListener(noticeClickListener);
        notice2.setOnClickListener(noticeClickListener);
        notice3.setOnClickListener(noticeClickListener);
        notice4.setOnClickListener(noticeClickListener);
        notice5.setOnClickListener(noticeClickListener);

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
                params.put("id", user_id);
                return params;
            }
        };


        TextView idView = findViewById(R.id.drawerNicknameView);
        idView.setText(user_id);

        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("YYYY/MM/dd HH:mm", Locale.getDefault()).format(currentTime);
        TextView recentLoginView = findViewById(R.id.recent_login);
        recentLoginView.setText(date_text);

        LinearLayout homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
            }
        });

        ImageButton noticeButton = findViewById(R.id.noticeButton);
        noticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
                Intent noticeIntent =  new Intent(MainActivity.this, NoticeActivity.class);
                noticeIntent.putExtra("id", user_id);
                noticeIntent.putExtra("nickname", user_nickname);
                noticeIntent.putExtra("response_notice", response_notice);
                startActivity(noticeIntent);
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
                        Intent myPageIntent = new Intent(MainActivity.this, MyPageActivity.class);
                        myPageIntent.putExtra("response_var", response_my_page);
                        startActivity(myPageIntent);
                        overridePendingTransition(R.anim.in, R.anim.out);
                    }
                }, 500);

            }
        });

        LinearLayout boardButton = findViewById(R.id.boardButton);
        boardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
                Intent boardCategoryIntent =  new Intent(MainActivity.this, BoardCategoryActivity.class);
                boardCategoryIntent.putExtra("id", user_id);
                boardCategoryIntent.putExtra("nickname", user_nickname);
                startActivity(boardCategoryIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });


        final LinearLayout adminMessageButton = findViewById(R.id.adminMessageButton);
        adminMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                Handler timer = new Handler();
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        drawerLayout.closeDrawer(drawerView);
                        Intent adminMessageIntent = new Intent(MainActivity.this, AdminMessageActivity.class);
                        adminMessageIntent.putExtra("id", user_id);
                        adminMessageIntent.putExtra("nickname", user_nickname);
                        startActivity(adminMessageIntent);
                        overridePendingTransition(R.anim.in, R.anim.out);
                    }
                }, 200);
            }
        });

        LinearLayout bookItemLayout = findViewById(R.id.bookItemButton);
        bookItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
                Intent bookCategoryIntent = new Intent(MainActivity.this, BookCategoryActivity.class);
                bookCategoryIntent.putExtra("id", user_id);
                bookCategoryIntent.putExtra("nickname", user_nickname);
                bookCategoryIntent.putExtra("icon", "false");
                startActivity(bookCategoryIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        final DialogInterface.OnClickListener loginListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveIdAndPW();
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(loginIntent);
                MainActivity MA = (MainActivity) MainActivity._Main_Activity;
                MA.finish();
                finish();
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        };

        ImageButton logoutButton = findViewById(R.id.logout_btn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(drawerView);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                dialog = builder.setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("확인", loginListener)
                        .setNegativeButton("취소", null)
                        .create();
                dialog.show();
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });
    }

    private StringRequest getStringNoticeListRequest() {
        return new StringRequest(Request.Method.POST, URL_NOTICE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                response_notice = response;

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        tcodeList.add(jsonObject.getInt("tcode"));
                        ttitleList.add(jsonObject.getString("ttitle"));
                        ttextList.add(jsonObject.getString("ttext"));
                        tnameList.add(jsonObject.getString("tname"));
                        timageList.add(jsonObject.getString("timage"));
                        tdateList.add(jsonObject.getString("tdate"));
                        String year = tdateList.get(i).substring(2,4);
                        String month = tdateList.get(i).substring(5,7);
                        String day = tdateList.get(i).substring(8,10);
                        String date = year + "/" + month + "/" + day;
                        dateList.add(date);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < ttitleList.size(); i++) {
                    noticeItemArrayList.add(new NoticeItem(ttitleList.get(i), ttextList.get(i), tnameList.get(i),
                            tcodeList.get(i), timageList.get(i), tdateList.get(i)));
                }
                notice1.setText(ttitleList.get(0));
                notice2.setText(ttitleList.get(1));
                notice3.setText(ttitleList.get(2));
                notice4.setText(ttitleList.get(3));
                notice5.setText(ttitleList.get(4));
                date1.setText(dateList.get(0));
                date2.setText(dateList.get(1));
                date3.setText(dateList.get(2));
                date4.setText(dateList.get(3));
                date5.setText(dateList.get(4));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("number", "1");
                return params;
            }
        };
    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

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
            if ( pressedTime == 0 ) {
                Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_SHORT).show();
                pressedTime = System.currentTimeMillis();
            }
            else {
                int seconds = (int) (System.currentTimeMillis() - pressedTime);

                if ( seconds > 2000 ) {
                    Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_SHORT).show();
                    pressedTime = 0 ;
                }
                else {
                    super.onBackPressed();
//                finish(); // app 종료 시키기
                }
            }
        }

    }
}
