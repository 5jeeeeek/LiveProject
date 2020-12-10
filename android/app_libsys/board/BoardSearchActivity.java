package com.example.app_libsys.board;

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

public class BoardSearchActivity extends AppCompatActivity {
    private RequestQueue queue;
    private static final String URL_MY_BOARD_SEARCH = "http://www.minback.com/users/myboardsearch";
    private static final String URL_BOARD_SEARCH = "http://www.minback.com/board/boardsearch";
    private static final String URL_BOARD_VIEW = "http://www.minback.com/board/boardview";
    private String bcode;
    private InputMethodManager inputMethodManager;
    private String searchBoardResponse = "";
    private String url = "";
    private String id = "";


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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_search);

        queue = Volley.newRequestQueue(this);

        final Intent BoardSearchIntent = getIntent();
        final String id_res = BoardSearchIntent.getStringExtra("id");
        id = id_res;
        final String nickname_res = BoardSearchIntent.getStringExtra("nickname");
        final String myPost = BoardSearchIntent.getStringExtra("myPost");

        final EditText search_bar = findViewById(R.id.search_bar);
        final ImageView boardSearchButton = findViewById(R.id.boardSearchButton);
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);


        if (myPost.equals("true")) {
            url = URL_MY_BOARD_SEARCH;
        } else {
            url = URL_BOARD_SEARCH;
        }

        ListView listView = findViewById(R.id.listview);
        BoardListViewAdapter adapter = new BoardListViewAdapter(this, R.layout.board_listview_item, boardItemArrayList);
        listView.setAdapter(adapter);
        resetAdapter(adapter);

        startBoardClickedActivity(listView, id_res, nickname_res);
        searchBoard(search_bar, boardSearchButton, adapter);
        resetAdapter(adapter);
    }

    private void searchBoard(final EditText search_bar, ImageView boardSearchButton, final BoardListViewAdapter adapter) {
        final StringRequest stringSearchRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                searchBoardResponse = response;

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
                resetAdapter(adapter);

                if (response.equals("[]")) {
                    toast("검색할 글이 없습니다.");
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
                if (url.equals(URL_MY_BOARD_SEARCH)) {
                    params.put("myboardsearch", search_bar.getText().toString());
                    params.put("id", id);
                } else if (url.equals(URL_BOARD_SEARCH)){
                    params.put("boardsearch", search_bar.getText().toString());
                }
                return params;
            }
        };

        boardSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.hideSoftInputFromWindow(search_bar.getWindowToken(), 0);
                String searchText = search_bar.getText().toString();

                if (searchText.equals("")) {
                    toast("검색어를 입력해주세요.");
                } else {
                    queue.add(stringSearchRequest);
                }
            }
        });
    }

    private void toast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void resetAdapter(final BoardListViewAdapter adapter){
        adapter.notifyDataSetChanged();
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
            }
        });
    }

    private void setBoardItemList(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
    }
}
