package com.example.app_libsys.book;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

public class BookSearchActivity extends AppCompatActivity {
    private RequestQueue queue;
    private static final String URL_BOOK_SEARCH = "http://www.minback.com/item/itemsearch";
    private String icon = "false";
    private InputMethodManager inputMethodManager;
    private String searchBookResponse = "";


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
        setContentView(R.layout.activity_book_search);

        queue = Volley.newRequestQueue(this);

        final Intent bookSearchIntent = getIntent();
        final String id_res = bookSearchIntent.getStringExtra("id");
        final String nickname_res = bookSearchIntent.getStringExtra("nickname");

        final EditText search_bar = findViewById(R.id.book_search_bar);
        final ImageView bookSearchButton = findViewById(R.id.bookSearchButton);
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        ListView listView = findViewById(R.id.book_search_listView);
        BookListViewAdapter adapter = new BookListViewAdapter(this, R.layout.book_listview_item, bookItemArrayList);
        listView.setAdapter(adapter);
        resetAdapter(adapter);

        startBookClickedActivity(listView, id_res, nickname_res);
        searchBoard(search_bar, bookSearchButton, adapter);
        resetAdapter(adapter);
    }

    private void searchBoard(final EditText search_bar, ImageView bookSearchButton, final BookListViewAdapter adapter) {
        final StringRequest stringSearchRequest = new StringRequest(Request.Method.POST, URL_BOOK_SEARCH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                searchBookResponse = response;

                bookItemArrayList.clear();
                icodeList.clear();
                icountList.clear();
                ireviewavgList.clear();
                iratingList.clear();
                inameList.clear();
                icateList.clear();
                ipublisherList.clear();
                iimageList.clear();
                ikeywordList.clear();
                itextList.clear();
                idateList.clear();
                resetAdapter(adapter);

                if (response.equals("[]")) {
                    toast("검색 결과가 없습니다.");
                } else {
                    setBoardItemList(response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("itemsearch", search_bar.getText().toString());
                return params;
            }
        };

        bookSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.hideSoftInputFromWindow(search_bar.getWindowToken(), 0);
                String searchText = search_bar.getText().toString();

                if (searchText.equals("")) {
                    toast("검색어를 입력해주세요.");
                } else if (searchBookResponse.equals("[]")) {
                    toast("검색할 글이 없습니다.");
                } else {
                    queue.add(stringSearchRequest);
                }
            }
        });
    }

    private void toast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void resetAdapter(final BookListViewAdapter adapter){
        adapter.notifyDataSetChanged();
    }

    private void startBookClickedActivity(ListView listView, final String id_res, final String nickname_res) {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Intent BookDetailIntent = new Intent(getApplicationContext(), BookDetailActivity.class);

                BookDetailIntent.putExtra("bookItemArrayList", bookItemArrayList);
                BookDetailIntent.putExtra("position", position);
                BookDetailIntent.putExtra("id", id_res);
                BookDetailIntent.putExtra("nickname", nickname_res);
                BookDetailIntent.putExtra("icon", icon);


                startActivity(BookDetailIntent);
            }
        });
    }

    private void setBoardItemList(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
    }
}
