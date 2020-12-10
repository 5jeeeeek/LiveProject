package com.example.app_libsys.book;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_libsys.R;

import java.util.ArrayList;
import java.util.List;

public class BookListViewAdapter extends BaseAdapter {
//    private ArrayList<BookItem> bookItemArrayList = new ArrayList<>() ;
    private LayoutInflater inflate;
    private List<String> list;
    private ArrayList<BookItem> data;
    private int layout;
    private Context context;
    private final String URL_IMAGE = "http://minback.com/upimg/";


    public BookListViewAdapter(Context context, int layout, ArrayList<BookItem> data) {
        this.inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.book_listview_item, parent, false);
        }


        final ImageView bookImageView =  convertView.findViewById(R.id.bookImageView) ;
        TextView bookNameView =  convertView.findViewById(R.id.bookNameView) ;
        TextView bookTextView =  convertView.findViewById(R.id.bookTextView) ;
        TextView bookPublisherView =  convertView.findViewById(R.id.bookPublisherView) ;
        TextView bookCategoryView =  convertView.findViewById(R.id.bookCategoryView) ;
        TextView bookDateView =  convertView.findViewById(R.id.bookDateView) ;
        TextView bookCount =  convertView.findViewById(R.id.bookCount) ;
        TextView bookReview =  convertView.findViewById(R.id.bookReview) ;
        TextView bookRatingAvg =  convertView.findViewById(R.id.bookRatingAvg) ;


        BookItem bookItem = data.get(position);


        final ImageRequest imageRequest = new ImageRequest(URL_IMAGE + bookItem.getImage(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                bookImageView.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                bookImageView.setVisibility(View.GONE);
//                Toast.makeText(BoardDetailActivity.this,"이미지가 없는 글", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(context).add(imageRequest);

        bookNameView.setText(bookItem.getName());
        bookTextView.setText(bookItem.getText());
        bookPublisherView.setText(bookItem.getPublisher());
        bookCategoryView.setText(bookItem.getCate());
        bookDateView.setText(bookItem.getDate());
        bookCount.setText(String.valueOf(bookItem.getCount()));
        bookReview.setText(String.valueOf(bookItem.getReviewavg()));
        bookRatingAvg.setText(String.valueOf(bookItem.getRating()));

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return data.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
//    public void addItem(Drawable icon, String name, String text, String publisher) {
//        BookListViewItem bookListViewItem = new BookListViewItem();
//
//        bookListViewItem.setBookImage(icon);
//        bookListViewItem.setBookName(name);
//        bookListViewItem.setBookText(text);
//        bookListViewItem.setBookPublisher(publisher);
//
//        listViewItemList.add(bookListViewItem);
//    }
}