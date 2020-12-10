package com.example.app_libsys.book;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
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
import com.example.app_libsys.book.review.ReviewAdapter;
import com.example.app_libsys.book.review.ReviewItem;

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

public class BookDetailActivity extends AppCompatActivity {

    TextView bookNameDetail, bookTextDetail, bookPublisherDetail, bookCategoryDetail,
            bookDateDetail, bookCountDetail, bookReviewDetail, BookRatingAvgDetail, reviewSize;
    ImageView bookImageView;
    ImageButton bookWishButton;
    ListView review_list;
    EditText review_edit;
    View footer;
    ReviewAdapter bookReviewAdapter;
    RatingBar ratingBar;
    Date currentTime = Calendar.getInstance().getTime();
    String date_text = new SimpleDateFormat("YYYY-MM-dd H:mm", Locale.getDefault()).format(currentTime);
    String ratingText, nickname;
    float rrating;

    private RequestQueue queue;
    private static final String URL_REVIEW_ADD = "http://www.minback.com/item/reviewadd";
    private static final String URL_REVIEW_LIST = "http://www.minback.com/item/reviewlist";
    private static final String URL_REVIEW_DELETE = "http://www.minback.com/item/reviewdel";
    private static final String URL_WISH_ADD = "http://www.minback.com/item/wishlistadd";
    private final String URL_IMAGE = "http://minback.com/upimg/";

    private AlertDialog dialog;

    private ArrayList<ReviewItem> reviewItemArrayList = new ArrayList<>();
    private ArrayList<Integer> rcodeList = new ArrayList<>();
    private ArrayList<Integer> icodeList = new ArrayList<>();
    private ArrayList<String> idList = new ArrayList<>();
    private ArrayList<String> rnameList = new ArrayList<>();
    private ArrayList<String> rtextList = new ArrayList<>();
    private ArrayList<Float> rratingList = new ArrayList<>();
    private ArrayList<String> rdateList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        bookImageView = findViewById(R.id.bookImage);
        bookNameDetail = findViewById(R.id.bookNameDetail);
        bookTextDetail = findViewById(R.id.bookTextDetail);
        bookPublisherDetail = findViewById(R.id.bookPublisherDetail);
        bookCategoryDetail = findViewById(R.id.bookCategoryDetail);
        bookDateDetail = findViewById(R.id.bookDateDetail);
        bookCountDetail = findViewById(R.id.bookCountDetail);
        bookReviewDetail = findViewById(R.id.bookReviewDetail);
        BookRatingAvgDetail = findViewById(R.id.bookRatingAvgDetail);
        bookWishButton = findViewById(R.id.bookWishButton);
        reviewSize = findViewById(R.id.reviewSize);

        Intent BookDetailIntent = getIntent();
        final String id_res = BookDetailIntent.getStringExtra("id");
        final String nickname_res = BookDetailIntent.getStringExtra("nickname");
        final String icon_res = BookDetailIntent.getStringExtra("icon");
        nickname = nickname_res;

        final ArrayList<BookItem> bookItemArrayList = BookDetailIntent.getParcelableArrayListExtra("bookItemArrayList");
        final int position = BookDetailIntent.getIntExtra("position", 0);

        queue = Volley.newRequestQueue(this);

        if (icon_res.equals("true")) {
            bookWishButton.setImageResource(R.drawable.ic_favorite);
        } else {
            bookWishButton.setImageResource(R.drawable.baseline_add_24);
        }

        final ImagePopup imagePopup = new ImagePopup(this);

        final ImageRequest imageRequest = new ImageRequest(URL_IMAGE + bookItemArrayList.get(position).getImage(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                bookImageView.setImageBitmap(response);
                imagePopup.setBackgroundColor(Color.BLACK);  // Optional
                imagePopup.setFullScreen(true); // Optional
                imagePopup.setHideCloseIcon(true);  // Optional
                imagePopup.initiatePopup(bookImageView.getDrawable());

            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                bookImageView.setVisibility(View.GONE);
                error.printStackTrace();
            }
        });

        queue.add(imageRequest);

        bookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.viewPopup();
            }
        });



        bookNameDetail.setText(bookItemArrayList.get(position).getName());
        bookTextDetail.setText(bookItemArrayList.get(position).getText());
        bookPublisherDetail.setText(bookItemArrayList.get(position).getPublisher());
        bookCategoryDetail.setText(bookItemArrayList.get(position).getCate());
        bookDateDetail.setText(bookItemArrayList.get(position).getDate());
        bookCountDetail.setText(String.valueOf(bookItemArrayList.get(position).getCount()));
        bookReviewDetail.setText(String.valueOf(bookItemArrayList.get(position).getReviewavg()));
        BookRatingAvgDetail.setText(String.valueOf(bookItemArrayList.get(position).getRating()));


        final StringRequest stringWishAddRequest = new StringRequest(Request.Method.POST, URL_WISH_ADD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("1")) {
                    Toast.makeText(getApplicationContext(), "관심도서로 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    bookWishButton.setImageResource(R.drawable.ic_favorite);
                } else if(response.equals("0")) {
                    Toast.makeText(getApplicationContext(), "관심도서가 해제되었습니다.", Toast.LENGTH_SHORT).show();
                    bookWishButton.setImageResource(R.drawable.ic_favorite_border);
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
                params.put("id", id_res);
                params.put("icode", Integer.toString(bookItemArrayList.get(position).getIcode()));
                return params;
            }
        };

        bookWishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queue.add(stringWishAddRequest);
            }
        });


        final StringRequest stringReviewAddRequest = new StringRequest(Request.Method.POST, URL_REVIEW_ADD, new Response.Listener<String>() {
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
                params.put("icode", Integer.toString(bookItemArrayList.get(position).getIcode()));
                params.put("id", id_res);
                params.put("rname", nickname_res);
                params.put("rtext", review_edit.getText().toString());
                params.put("rrating", ratingText);
                params.put("rdate", date_text);
                return params;
            }
        };

        final StringRequest stringReviewListRequest = new StringRequest(Request.Method.POST, URL_REVIEW_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response : 댓글 목록
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        rcodeList.add(jsonObject.getInt("rcode"));
                        icodeList.add(jsonObject.getInt("icode"));
                        rratingList.add((float) jsonObject.getDouble("rrating"));
                        idList.add(jsonObject.getString("id"));
                        rnameList.add(jsonObject.getString("rname"));
                        rtextList.add(jsonObject.getString("rtext"));
                        rdateList.add(jsonObject.getString("rdate"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i=0; i<rcodeList.size(); i++) {
                    ReviewItem reviewItemList = new ReviewItem();
                    reviewItemList.setText(rtextList.get(i));
                    reviewItemList.setNickname(rnameList.get(i));
                    reviewItemList.setRating(rratingList.get(i));
                    reviewItemList.setDate(rdateList.get(i));
                    reviewItemArrayList.add(reviewItemList);
                    resetAdapter();
                }

                reviewSize.setText(String.valueOf(rcodeList.size()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("icode", Integer.toString(bookItemArrayList.get(position).getIcode()));
                return params;
            }
        };

        queue.add(stringReviewListRequest);

        review_list = findViewById(R.id.review_list);
        footer = getLayoutInflater().inflate(R.layout.footer_book_review, null, false);
        review_list.addFooterView(footer);


        setList();
        setFooter(nickname_res, stringReviewAddRequest);
    }

    private void setFooter(final String nickname_res, final StringRequest stringReviewAddRequest){
        review_edit = footer.findViewById(R.id.review_edit);
        Button review_input_btn = footer.findViewById(R.id.review_input_btn);
        ratingBar = footer.findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingText = String.valueOf(rating);
                rrating = rating;
            }
        });

        review_input_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.review_input_btn:
                        final String temp = review_edit.getText().toString();
                        if(temp.equals("")){
                            Toast.makeText(getApplicationContext(), "리뷰를 입력해주세요!", Toast.LENGTH_SHORT).show();
                        } else if(ratingText == null) {
                            Toast.makeText(getApplicationContext(), "평점을 입력해주세요!", Toast.LENGTH_SHORT).show();
                        } else {
                            final ReviewItem reviewItem = new ReviewItem();

                            reviewItem.setText(temp);
                            reviewItem.setNickname(nickname_res);
                            reviewItem.setDate(date_text);
                            reviewItem.setRating(rrating);
                            reviewItemArrayList.add(reviewItem);
                            resetAdapter();
                            queue.add(stringReviewAddRequest);
                            review_edit.setText("");
                        }
                        break;
                }
            }
        });
    }

    private void setList(){
        bookReviewAdapter = new ReviewAdapter(getApplicationContext(), BookDetailActivity.this, BookDetailActivity.this, reviewItemArrayList, nickname);
        review_list.setAdapter(bookReviewAdapter);
        review_list.setSelection(reviewItemArrayList.size()-1);
        review_list.setDivider(null);
        review_list.setSelectionFromTop(0,0);
    }

    public void resetAdapter(){
        bookReviewAdapter.notifyDataSetChanged();
    }

    public void deleteArr(final int p){
        Intent bookDetailIntent = getIntent();
        final ArrayList<BookItem> bookItemArrayList = bookDetailIntent.getParcelableArrayListExtra("bookItemArrayList");
        final int position = bookDetailIntent.getIntExtra("position", 0);
        final StringRequest stringReviewDeleteRequest = new StringRequest(Request.Method.POST, URL_REVIEW_DELETE, new Response.Listener<String>() {
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
                params.put("rcode", Integer.toString(rcodeList.get(p)));
                params.put("icode", Integer.toString(bookItemArrayList.get(position).getIcode()));
                return params;
            }
        };
        reviewItemArrayList.remove(p);
        queue.add(stringReviewDeleteRequest);
        bookReviewAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
    }
}
