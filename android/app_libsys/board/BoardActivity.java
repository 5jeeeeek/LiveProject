package com.example.app_libsys.board;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class BoardActivity extends AppCompatActivity {
    private RequestQueue queue;
    private String url = "";
    private static final String URL_BOARD_VIEW = "http://www.minback.com/board/boardview";
    private static final String URL_MY_INFO = "http://www.minback.com/users/myinfo";
    private String number = "1";
    private int number_int = Integer.parseInt(number);
    private String bcode;
    private String loadBoardResponse = "";
    private ProgressBar progressBar;
    private ArrayList<BoardItem> boardItemArrayList = new ArrayList<>();
    private ArrayList<String> btitleList = new ArrayList<>();
    private ArrayList<String> btextList = new ArrayList<>();
    private ArrayList<String> bnicknameList = new ArrayList<>();
    private ArrayList<String> bcateList = new ArrayList<>();
    private ArrayList<String> bimageList = new ArrayList<>();
    private ArrayList<String> bdateList = new ArrayList<>();
    private ArrayList<String> bcommentList = new ArrayList<>();
    private ArrayList<Integer> bcodeList = new ArrayList<>();
    private ArrayList<Integer> blikeList = new ArrayList<>();
    private ArrayList<Integer> bviewsList = new ArrayList<>();
    private boolean myPost = false;

    private DrawerLayout drawerLayout;
    private View drawerView;
    public String response_my_page = "";
    private AlertDialog dialog;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

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

        final Intent boardIntent = getIntent();
        final String id_res = boardIntent.getStringExtra("id");
        final String nickname_res = boardIntent.getStringExtra("nickname");
        final String response_var = boardIntent.getStringExtra("response_var");
        url = boardIntent.getStringExtra("url");

        TextView boardName = findViewById(R.id.boardName);
        switch (url) {
            case "http://www.minback.com/board/boardlist2":
                boardName.setText("질문게시판");
                break;
            case "http://www.minback.com/board/boardlist3":
                boardName.setText("HOT 게시판");
                break;
            case "http://www.minback.com/board/boardlist1":
                boardName.setText("자유게시판");
                break;
            default:
                boardName.setText("내가 쓴 글");
                myPost = true;
                break;
        }

        final TextView helpTextView = findViewById(R.id.helpTextView);

        if (myPost) {
            helpTextView.setText("작성한 글이 없습니다!");
        } else {
            helpTextView.setText("당겨서 새로고침 해주세요!");
        }

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
                Intent adminMessageIntent = new Intent(BoardActivity.this, AdminMessageActivity.class);
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
                Intent bookCategoryIntent = new Intent(BoardActivity.this, BookCategoryActivity.class);
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
                        Intent myPageIntent = new Intent(BoardActivity.this, MyPageActivity.class);
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
                Intent loginIntent = new Intent(BoardActivity.this, LoginActivity.class);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(BoardActivity.this);
                dialog = builder.setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("확인", loginListener)
                        .setNegativeButton("취소", null)
                        .create();
                dialog.show();
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });


        ImageView boardSearchButton = findViewById(R.id.boardSearchButton);
        boardSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BoardSearchIntent = new Intent(BoardActivity.this, BoardSearchActivity.class);

                BoardSearchIntent.putExtra("id", id_res);
                BoardSearchIntent.putExtra("nickname", nickname_res);
                if (myPost) {
                    BoardSearchIntent.putExtra("myPost", "true");
                } else {
                    BoardSearchIntent.putExtra("myPost", "false");
                }

                startActivity(BoardSearchIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });


        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        final ListView listView = findViewById(R.id.listview);
        final BoardListViewAdapter adapter = new BoardListViewAdapter(this, R.layout.board_listview_item, boardItemArrayList);
        listView.setAdapter(adapter);
        resetAdapter(adapter);

        startBoardClickedActivity(listView, id_res, nickname_res);
        setBoardItemList(response_var);

        final StringRequest refreshBoardListRequest = refreshBoardList(adapter);
        final StringRequest loadBoardListRequest = loadBoardList(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent boardWriteIntent = new Intent(BoardActivity.this, BoardWriteActivity.class);
                boardWriteIntent.putExtra("id", id_res);
                boardWriteIntent.putExtra("nickname", nickname_res);
                BoardActivity.this.startActivity(boardWriteIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        swipeRefresh(refreshBoardListRequest);
        loadListView(listView, loadBoardListRequest);
        resetAdapter(adapter);

        LinearLayout helpView = findViewById(R.id.helpView);
        listView.setEmptyView(helpView);
    }

    private StringRequest loadBoardList(final BoardListViewAdapter adapter) {
        return new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadBoardResponse = response;
                setBoardItemList(response);
                resetAdapter(adapter);
                progressBar.setVisibility(View.GONE);
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
    }

    private StringRequest refreshBoardList(final BoardListViewAdapter adapter) {
        return new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                number_int = 1;
                boardItemArrayList.clear();
                bcodeList.clear();
                blikeList.clear();
                bviewsList.clear();
                bnicknameList.clear();
                bcateList.clear();
                bimageList.clear();
                bdateList.clear();
                bcommentList.clear();
                btitleList.clear();
                btextList.clear();

                setBoardItemList(response);
                resetAdapter(adapter);
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

    private void swipeRefresh(final StringRequest stringBoardListRequest) {
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_layout);

        if (myPost) {
            swipeRefreshLayout.setEnabled(false);
        } else {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    queue.add(stringBoardListRequest);
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    private void loadListView(ListView listView, final StringRequest loadBoardListRequest) {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            boolean lastItemVisibleFlag = false;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
            }
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag) {
                    progressBar.setVisibility(View.VISIBLE);
                    if (loadBoardResponse.equals("[]")) {
                        Toast.makeText(getApplicationContext(), "마지막 글입니다.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    } else {
                        boardItemArrayList.clear();
                        number_int++;
                        number = String.valueOf(number_int);
                        queue.add(loadBoardListRequest);
                    }
                }
            }
        });
    }

    private void setBoardItemList(String response_var) {
        try {
            JSONArray jsonArray = new JSONArray(response_var);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                bcodeList.add(jsonObject.getInt("bcode"));
                blikeList.add(jsonObject.getInt("blike"));
                bviewsList.add(jsonObject.getInt("bviews"));

                btitleList.add(jsonObject.getString("btitle"));
                btextList.add(jsonObject.getString("btext"));
                bnicknameList.add(jsonObject.getString("bnickname"));
                bcateList.add(jsonObject.getString("bcate"));
                bimageList.add(jsonObject.getString("bimage"));
                bdateList.add(jsonObject.getString("bdate"));
                bcommentList.add(jsonObject.getString("bcomment"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < btitleList.size(); i++) {
            boardItemArrayList.add(new BoardItem(btitleList.get(i), btextList.get(i), bnicknameList.get(i),
                    bcodeList.get(i), bcateList.get(i), bimageList.get(i), bdateList.get(i),
                    blikeList.get(i), bviewsList.get(i), bcommentList.get(i)));
        }
    }

    private void startBoardClickedActivity(ListView listView, final String id_res, final String nickname_res) {
        final StringRequest stringBoardViewRequest = new StringRequest(Request.Method.POST, URL_BOARD_VIEW, new Response.Listener<String>() {
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
                params.put("bcode", bcode);
                return params;
            }
        };


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                queue.add(stringBoardViewRequest);
                bcode = bcodeList.get(position).toString();

                Intent BoardClickedIntent = new Intent(getApplicationContext(), BoardDetailActivity.class);

                BoardClickedIntent.putExtra("boardItemArrayList", boardItemArrayList);
                BoardClickedIntent.putExtra("position", position);
                BoardClickedIntent.putExtra("id", id_res);
                BoardClickedIntent.putExtra("nickname", nickname_res);

                startActivity(BoardClickedIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });
    }

    public void resetAdapter(final BoardListViewAdapter adapter){
        adapter.notifyDataSetChanged();
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
