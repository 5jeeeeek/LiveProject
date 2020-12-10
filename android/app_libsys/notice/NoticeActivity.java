package com.example.app_libsys.notice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_libsys.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NoticeActivity extends AppCompatActivity {
    private RequestQueue queue;
    private static final String URL_NOTICE_LIST = "http://www.minback.com/notice/noticelist";
    private static final String URL_NOTICE_VIEW = "http://www.minback.com/notice/noticeview";
    private String number = "1";
    private int number_int = Integer.parseInt(number);
    private String tcode;
    private String loadNoticeResponse = "";
    private ProgressBar progressBar;
    private ArrayList<NoticeItem> noticeItemArrayList = new ArrayList<>();
    private ArrayList<String> ttitleList = new ArrayList<>();
    private ArrayList<String> ttextList = new ArrayList<>();
    private ArrayList<String> tnameList = new ArrayList<>();
    private ArrayList<String> timageList = new ArrayList<>();
    private ArrayList<String> tdateList = new ArrayList<>();
    private ArrayList<Integer> tcodeList = new ArrayList<>();
    private LinearLayout helpView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        queue = Volley.newRequestQueue(this);

        final Intent noticeIntent = getIntent();
        final String id_res = noticeIntent.getStringExtra("id");
        final String nickname_res = noticeIntent.getStringExtra("nickname");
        final String response_notice = noticeIntent.getStringExtra("response_notice");

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        final ListView noticeListView = findViewById(R.id.noticeListView);
        final NoticeListViewAdapter adapter = new NoticeListViewAdapter(this, R.layout.notice_listview_item, noticeItemArrayList);
        noticeListView.setAdapter(adapter);
        resetAdapter(adapter);

        startBoardClickedActivity(noticeListView, id_res, nickname_res);
        setBoardItemList(response_notice);

        final StringRequest refreshBoardListRequest = refreshBoardList(adapter);
        final StringRequest loadBoardListRequest = loadBoardList(adapter);

        swipeRefresh(refreshBoardListRequest);
        loadListView(noticeListView, loadBoardListRequest);
        resetAdapter(adapter);

        helpView = findViewById(R.id.helpView);
        noticeListView.setEmptyView(helpView);
    }

    private StringRequest loadBoardList(final NoticeListViewAdapter adapter) {
        return new StringRequest(Request.Method.POST, URL_NOTICE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadNoticeResponse = response;
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

    private StringRequest refreshBoardList(final NoticeListViewAdapter adapter) {
        return new StringRequest(Request.Method.POST, URL_NOTICE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                number_int = 1;
                noticeItemArrayList.clear();
                tcodeList.clear();
                tnameList.clear();
                timageList.clear();
                tdateList.clear();
                ttitleList.clear();
                ttextList.clear();

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

    private void swipeRefresh(final StringRequest stringNoitceListRequest) {
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queue.add(stringNoitceListRequest);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadListView(ListView listView, final StringRequest loadNoticeListRequest) {
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
                    if (loadNoticeResponse.equals("[]")) {
                        Toast.makeText(getApplicationContext(), "마지막 글입니다.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    } else {
                        noticeItemArrayList.clear();
                        number_int++;
                        number = String.valueOf(number_int);
                        queue.add(loadNoticeListRequest);
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

                tcodeList.add(jsonObject.getInt("tcode"));
                ttitleList.add(jsonObject.getString("ttitle"));
                ttextList.add(jsonObject.getString("ttext"));
                tnameList.add(jsonObject.getString("tname"));
                timageList.add(jsonObject.getString("timage"));
                tdateList.add(jsonObject.getString("tdate"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < ttitleList.size(); i++) {
            noticeItemArrayList.add(new NoticeItem(ttitleList.get(i), ttextList.get(i), tnameList.get(i),
                    tcodeList.get(i), timageList.get(i), tdateList.get(i)));
        }
    }

    private void startBoardClickedActivity(ListView listView, final String id_res, final String nickname_res) {
        final StringRequest stringNoticeViewRequest = new StringRequest(Request.Method.POST, URL_NOTICE_VIEW, new Response.Listener<String>() {
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
                params.put("tcode", tcode);
                return params;
            }
        };


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                queue.add(stringNoticeViewRequest);
                tcode = tcodeList.get(position).toString();

                Intent NoticeClickedIntent = new Intent(getApplicationContext(), NoticeDetailActivity.class);

                NoticeClickedIntent.putExtra("noticeItemArrayList", noticeItemArrayList);
                NoticeClickedIntent.putExtra("position", position);
                NoticeClickedIntent.putExtra("id", id_res);
                NoticeClickedIntent.putExtra("nickname", nickname_res);

                startActivity(NoticeClickedIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });
    }

    public void resetAdapter(final NoticeListViewAdapter adapter){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
    }
}
