package com.example.app_libsys.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import java.util.List;
import java.util.Map;

public class BookActivity extends AppCompatActivity {
    private RequestQueue queue;
    Spinner spinner1;
    AdapterSpinner1 adapterSpinner1;
    private String number = "1";
    private int number_int = Integer.parseInt(number);
    private String icode;
    private String loadItemResponse = "";
    private ProgressBar progressBar2;
    private String url = "";
    private String icon = "";
    private String loadBookResponse = "";


    private ArrayList<BookItem> bookItemArrayList = new ArrayList<>();

    private ArrayList<Integer> icodeList = new ArrayList<>();
    private ArrayList<Integer> icountList = new ArrayList<>();
    private ArrayList<Integer> ireviewavgList = new ArrayList<>();
    private ArrayList<Float> iratingList = new ArrayList<>();

    private ArrayList<String> inameList = new ArrayList<>();
    private ArrayList<String> icateList = new ArrayList<>();
    private ArrayList<String> ipublisherList = new ArrayList<>();
    private ArrayList<String> iimageList = new ArrayList<>();
    private ArrayList<String> ikeywordList = new ArrayList<>();
    private ArrayList<String> itextList = new ArrayList<>();
    private ArrayList<String> idateList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_item);

        queue = Volley.newRequestQueue(this);

        final Intent categoryIntent = getIntent();
        final String id_res = categoryIntent.getStringExtra("id");
        final String nickname_res = categoryIntent.getStringExtra("nickname");
        final String response_var = categoryIntent.getStringExtra("itemListResponse");
        final String url_var = categoryIntent.getStringExtra("url");
        final String response_icon = categoryIntent.getStringExtra("icon");
        url = url_var;
        icon = response_icon;

        List<String> data = new ArrayList<>();
        data.add("기본순");
        data.add("평점순");
        data.add("리뷰순");

        progressBar2 = findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.GONE);

//        spinner1 = findViewById(R.id.spinner1);
//        adapterSpinner1 = new AdapterSpinner1(this, data);
//        spinner1.setAdapter(adapterSpinner1);


        final ListView bookItemListView = findViewById(R.id.BookItemList);
        final BookListViewAdapter adapter = new BookListViewAdapter(this, R.layout.book_listview_item, bookItemArrayList);
        bookItemListView.setAdapter(adapter);
        resetAdapter(adapter);

        startBookDetailActivity(bookItemListView, id_res, nickname_res);
        setBookItemList(response_var);

        final StringRequest loadBookListRequest = loadBookList(adapter);
        loadListView(bookItemListView, loadBookListRequest);
        resetAdapter(adapter);

        LinearLayout helpView = findViewById(R.id.bookHelpView);
        bookItemListView.setEmptyView(helpView);

    }

    private StringRequest loadBookList(final BookListViewAdapter adapter) {
        return new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadBookResponse = response;
                setBookItemList(response);
                resetAdapter(adapter);
                progressBar2.setVisibility(View.GONE);
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

    private void loadListView(ListView listView, final StringRequest loadBookListRequest) {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            boolean lastItemVisibleFlag = false;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
            }
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag) {
                    progressBar2.setVisibility(View.VISIBLE);
                    if (loadBookResponse.equals("[]")) {
                        Toast.makeText(getApplicationContext(), "마지막 도서입니다.", Toast.LENGTH_SHORT).show();
                        progressBar2.setVisibility(View.GONE);
                    } else {
                        bookItemArrayList.clear();
                        number_int++;
                        number = String.valueOf(number_int);
                        queue.add(loadBookListRequest);
                    }
                }
            }
        });
    }



    private void setBookItemList(String response_var) {
        try {
            JSONArray jsonArray = new JSONArray(response_var);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                icodeList.add(jsonObject.getInt("icode"));
                icountList.add(jsonObject.getInt("icount"));
                ireviewavgList.add(jsonObject.getInt("ireview"));
                iratingList.add((float) jsonObject.getDouble("iratingavg"));

                inameList.add(jsonObject.getString("iname"));
                icateList.add(jsonObject.getString("icate"));
                ipublisherList.add(jsonObject.getString("ipublisher"));
                iimageList.add(jsonObject.getString("iimage"));
                ikeywordList.add(jsonObject.getString("ikeyword"));
                itextList.add(jsonObject.getString("itext"));
                idateList.add(jsonObject.getString("idate"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < inameList.size(); i++) {
            bookItemArrayList.add(new BookItem(inameList.get(i), icateList.get(i), ipublisherList.get(i),
                    iimageList.get(i), ikeywordList.get(i), itextList.get(i), idateList.get(i),
                    icodeList.get(i), icountList.get(i), ireviewavgList.get(i), iratingList.get(i)));
        }
    }

    private void startBookDetailActivity(ListView listView, final String id_res, final String nickname_res) {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                icode = icodeList.get(position).toString();

                Intent BookDetailIntent = new Intent(getApplicationContext(), BookDetailActivity.class);

                BookDetailIntent.putExtra("bookItemArrayList", bookItemArrayList);
                BookDetailIntent.putExtra("position", position);
                BookDetailIntent.putExtra("id", id_res);
                BookDetailIntent.putExtra("nickname", nickname_res);
                BookDetailIntent.putExtra("icon", icon);

                startActivity(BookDetailIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });
    }

    public void resetAdapter(final BookListViewAdapter adapter){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
    }
}
