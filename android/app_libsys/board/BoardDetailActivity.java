package com.example.app_libsys.board;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.app_libsys.R;
import com.example.app_libsys.board.comment.CommentAdapter;
import com.example.app_libsys.board.comment.CommentItem;

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

public class BoardDetailActivity extends AppCompatActivity {
    public static Activity _BoardClickedActivity;
    private RequestQueue queue;
    private static final String URL_IMAGE_DELETE = "http://www.minback.com/imgupload/boardimgdel";
    private static final String URL_BOARD_DELETE = "http://www.minback.com/board/boarddel";
    private static final String URL_BOARD_LIKE = "http://www.minback.com/board/boardlike";
    private static final String URL_COMMENT_ADD = "http://www.minback.com/board/bcadd";
    private static final String URL_COMMENT_LIST = "http://www.minback.com/board/bclist";
    private static final String URL_COMMENT_DELETE = "http://www.minback.com/board/bcdel";
    private static final String URL_VIEW_ADD = "http://www.minback.com/board/viewadd";
    private final String URL_IMAGE = "http://www.minback.com/upimg/";
    private AlertDialog dialog;

    private ArrayList<CommentItem> commentItemArrayList = new ArrayList<>();
    private ArrayList<Integer> ccodeList = new ArrayList<>();
    private ArrayList<Integer> bcodeList = new ArrayList<>();
    private ArrayList<Integer> cprivateList = new ArrayList<>();
    private ArrayList<String> idList = new ArrayList<>();
    private ArrayList<String> cnameList = new ArrayList<>();
    private ArrayList<String> ctextList = new ArrayList<>();
    private ArrayList<String> cdateList = new ArrayList<>();

    private String nickname;
    private String checkString;

    Date currentTime = Calendar.getInstance().getTime();
    final String date_text = new SimpleDateFormat("YYYY-MM-dd H:mm", Locale.getDefault()).format(currentTime);


    ListView comment_list;
    public EditText comment_edit;
    CommentAdapter ca;
    View footer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_detail);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("잠시만 기다려 주세요...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        _BoardClickedActivity = BoardDetailActivity.this;

        Intent BoardClickedIntent = getIntent();
        final String id_res = BoardClickedIntent.getStringExtra("id");
        final String nickname_res = BoardClickedIntent.getStringExtra("nickname");
        nickname = nickname_res;

        queue = Volley.newRequestQueue(this);

        final ImageView detail_image = findViewById(R.id.detail_image);
        TextView detail_title = findViewById(R.id.detail_title);
        TextView detail_text = findViewById(R.id.detail_text);
        TextView detail_nickname = findViewById(R.id.detail_nickname);
        TextView detail_code = findViewById(R.id.detail_code);
        TextView boardDateView = findViewById(R.id.boardDateView);
        final TextView detail_like = findViewById(R.id.detail_like);
        TextView detail_views = findViewById(R.id.detail_views);

        TextView boardUpdate = findViewById(R.id.boardUpdate);
        TextView boardDelete = findViewById(R.id.boardDelete);

        final TextView commentSize = findViewById(R.id.commentSize);


        final ArrayList<BoardItem> boardItemArrayList = BoardClickedIntent.getParcelableArrayListExtra("boardItemArrayList");
        final int position = BoardClickedIntent.getIntExtra("position", 0);


        String image_url = boardItemArrayList.get(position).getImage();

        detail_title.setText(boardItemArrayList.get(position).getTitle());
        detail_text.setText(boardItemArrayList.get(position).getText());
        detail_nickname.setText(boardItemArrayList.get(position).getNickname());
        detail_code.setText(Integer.toString(boardItemArrayList.get(position).getBcode()));
        boardDateView.setText(date_text);
        detail_like.setText(Integer.toString(boardItemArrayList.get(position).getLike()));
        detail_views.setText(Integer.toString(boardItemArrayList.get(position).getViews()));

        final ImagePopup imagePopup = new ImagePopup(this);

        final ImageRequest imageRequest = new ImageRequest(URL_IMAGE + image_url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                detail_image.setImageBitmap(response);

//                imagePopup.setWindowHeight(800); // Optional
//                imagePopup.setWindowWidth(800); // Optional
                imagePopup.setBackgroundColor(Color.BLACK);  // Optional
                imagePopup.setFullScreen(true); // Optional
                imagePopup.setHideCloseIcon(true);  // Optional
//                imagePopup.setImageOnClickClose(true);  // Optional
                imagePopup.initiatePopup(detail_image.getDrawable());
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                detail_image.setVisibility(View.GONE);
//                Toast.makeText(BoardDetailActivity.this,"이미지가 없는 글", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        queue.add(imageRequest);


        detail_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.viewPopup();
            }
        });


        if (nickname_res.equals(boardItemArrayList.get(position).getNickname())) {
            boardUpdate.setVisibility(View.VISIBLE);
            boardDelete.setVisibility(View.VISIBLE);
        }


        final StringRequest stringViewAddRequest = new StringRequest(Request.Method.POST, URL_VIEW_ADD, new Response.Listener<String>() {
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
                params.put("bcode", Integer.toString(boardItemArrayList.get(position).getBcode()));
                return params;
            }
        };

        queue.add(stringViewAddRequest);

        final StringRequest stringImageDeleteRequest = new StringRequest(Request.Method.POST, URL_IMAGE_DELETE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(BoardDetailActivity.this, "이미지 삭제 : " + response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("img", boardItemArrayList.get(position).getImage());
                return params;
            }
        };


        final StringRequest stringBoardDeleteRequest = new StringRequest(Request.Method.POST, URL_BOARD_DELETE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(BoardDetailActivity.this, "글 삭제 : " + response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("bcode", Integer.toString(boardItemArrayList.get(position).getBcode()));
                return params;
            }
        };

        final DialogInterface.OnClickListener boardDeleteListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.show();
                if (!boardItemArrayList.get(position).getImage().equals("0")) {
                    queue.add(stringImageDeleteRequest);
                }

                Handler timer = new Handler();
                timer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        queue.add(stringBoardDeleteRequest);
                        finish();
                        overridePendingTransition(R.anim.in, R.anim.out);
                    }
                }, 500);
            }
        };


        boardDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BoardDetailActivity.this);
                dialog = builder.setMessage("글을 삭제하시겠습니까?")
                        .setPositiveButton("확인", boardDeleteListener)
                        .setNegativeButton("취소", null)
                        .create();
                dialog.show();
            }
        });

        boardUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent boardUpdateIntent = new Intent(BoardDetailActivity.this, BoardUpdateActivity.class);
                boardUpdateIntent.putExtra("id", id_res);
                boardUpdateIntent.putExtra("nickname", nickname_res);
                boardUpdateIntent.putExtra("bcode", Integer.toString(boardItemArrayList.get(position).getBcode()));
                boardUpdateIntent.putExtra("bimage", boardItemArrayList.get(position).getImage());
                boardUpdateIntent.putExtra("title", boardItemArrayList.get(position).getTitle());
                boardUpdateIntent.putExtra("text", boardItemArrayList.get(position).getText());
                startActivity(boardUpdateIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        final StringRequest stringLikeRequest = new StringRequest(Request.Method.POST, URL_BOARD_LIKE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("1")) {
                    Toast.makeText(getApplicationContext(), "좋아요를 눌렀습니다.", Toast.LENGTH_SHORT).show();
                    int like = Integer.parseInt(Integer.toString(boardItemArrayList.get(position).getLike())) + 1;
                    detail_like.setText(String.valueOf(like));
                } else if (response.equals("2")) {
                    Toast.makeText(getApplicationContext(), "이미 좋아요를 눌렀습니다.", Toast.LENGTH_SHORT).show();
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
                params.put("bcode", Integer.toString(boardItemArrayList.get(position).getBcode()));
                params.put("id", id_res);
                return params;
            }
        };

        Button likeButton = findViewById(R.id.likeButton);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue.add(stringLikeRequest);
            }
        });

        final StringRequest stringCommentAddRequest = new StringRequest(Request.Method.POST, URL_COMMENT_ADD, new Response.Listener<String>() {
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
                params.put("bcode", Integer.toString(boardItemArrayList.get(position).getBcode()));
                params.put("id", id_res);
                params.put("cname", nickname_res);
                params.put("ctext", comment_edit.getText().toString());
                params.put("cdate", date_text);
                params.put("cprivate", checkString);
                return params;
            }
        };


        final StringRequest stringCommentListRequest = new StringRequest(Request.Method.POST, URL_COMMENT_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response : 댓글 목록
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ccodeList.add(jsonObject.getInt("ccode"));
                        bcodeList.add(jsonObject.getInt("bcode"));
                        cprivateList.add(jsonObject.getInt("cprivate"));
                        idList.add(jsonObject.getString("id"));
                        cnameList.add(jsonObject.getString("cname"));
                        ctextList.add(jsonObject.getString("ctext"));
                        cdateList.add(jsonObject.getString("cdate"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i=0; i<ccodeList.size(); i++) {
                    CommentItem commentItemList = new CommentItem();

                    if (cprivateList.get(i).equals(1)) {
                        commentItemList.setCheck(1);
                        if (cnameList.get(i).equals(nickname_res) || boardItemArrayList.get(position).getNickname().equals(nickname_res)) {
                            commentItemList.setContent(ctextList.get(i));
                            commentItemList.setNickname(cnameList.get(i));
                            commentItemList.setDate(cdateList.get(i));
                        } else {
                            commentItemList.setContent("작성자만 확인 가능합니다.");
                            commentItemList.setNickname("비공개 댓글");
                            commentItemList.setDate("");
                        }
                    } else {
                        commentItemList.setCheck(0);
                        commentItemList.setContent(ctextList.get(i));
                        commentItemList.setNickname(cnameList.get(i));
                        commentItemList.setDate(cdateList.get(i));
                    }

                    commentItemArrayList.add(commentItemList);
                    resetAdapter();
                }

                commentSize.setText(String.valueOf(ccodeList.size()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("bcode", Integer.toString(boardItemArrayList.get(position).getBcode()));
                return params;
            }
        };

        queue.add(stringCommentListRequest);

        comment_list = findViewById(R.id.comment_list);
        footer = getLayoutInflater().inflate(R.layout.footer, null, false);
        comment_list.addFooterView(footer);

        setList();
        setFooter(nickname_res, stringCommentAddRequest);
    }

    private void setFooter(final String nickname_res, final StringRequest stringCommentAddRequest){
        comment_edit = footer.findViewById(R.id.jrv_comment_edit);
        Button commentinput_btn = footer.findViewById(R.id.jrv_commentinput_btn);
        final CheckBox checkBox = footer.findViewById(R.id.commentCheck);

        commentinput_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.jrv_commentinput_btn:
                        final String temp = comment_edit.getText().toString();
                        if(temp.equals("")){
                            Toast.makeText(getApplicationContext(), "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show();
                        } else {
                            //EditText의 빈칸이 없을 경우 등록!
                            CommentItem commentItem = new CommentItem();
                            if (checkBox.isChecked()) {
                                commentItem.setCheck(1);
                                checkString = "1";
                            } else {
                                commentItem.setCheck(0);
                                checkString = "0";
                            }
                            commentItem.setContent(temp);
                            commentItem.setNickname(nickname_res);
                            commentItem.setDate(date_text);
                            commentItemArrayList.add(commentItem);
                            resetAdapter();
                            queue.add(stringCommentAddRequest);
                            comment_edit.setText("");
                        }
                        break;
                }
            }
        });
    }
    private void setList(){
        ca = new CommentAdapter(getApplicationContext(), BoardDetailActivity.this, BoardDetailActivity.this, commentItemArrayList, nickname);
        comment_list.setAdapter(ca);
        comment_list.setSelection(commentItemArrayList.size()-1);
        comment_list.setDivider(null);
        comment_list.setSelectionFromTop(0,0);
    }

    public void resetAdapter(){
        ca.notifyDataSetChanged();
    }

    public void deleteArr(final int p){
        Intent BoardClickedIntent = getIntent();
        final ArrayList<BoardItem> boardItemArrayList = BoardClickedIntent.getParcelableArrayListExtra("boardItemArrayList");
        final int position = BoardClickedIntent.getIntExtra("position", 0);
        final StringRequest stringCommentDeleteRequest = new StringRequest(Request.Method.POST, URL_COMMENT_DELETE, new Response.Listener<String>() {
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
                params.put("ccode", Integer.toString(ccodeList.get(p)));
                params.put("bcode", Integer.toString(boardItemArrayList.get(position).getBcode()));
                return params;
            }
        };
        commentItemArrayList.remove(p);
        queue.add(stringCommentDeleteRequest);
        ca.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
    }
}
