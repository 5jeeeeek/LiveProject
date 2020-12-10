package com.example.app_libsys.board.comment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_libsys.R;
import com.example.app_libsys.board.BoardDetailActivity;

import java.util.ArrayList;


public class CommentAdapter extends BaseAdapter implements OnClickListener{
    private Context mContext;
    private Activity mActivity;
    private ArrayList<CommentItem> arr;
    private BoardDetailActivity ma;
    private String nickname;


    public CommentAdapter(Context mContext, Activity mActivity, BoardDetailActivity mc, ArrayList<CommentItem> arr_item, String nickname) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.arr = arr_item;
        this.ma = mc;
        this.nickname = nickname;
    }
    @Override
    public int getCount() {
        return arr.size();
    }
    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            int res;
            res = com.example.app_libsys.R.layout.comment_item;
            LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(res, parent, false);
        }
        if(arr.size() != 0){
            TextView ci_nickname_text = convertView.findViewById(R.id.ci_nickname_text);
            ci_nickname_text.setText(arr.get(position).getNickname());
            if (arr.get(position).getNickname().equals("비공개 댓글")) {
                ci_nickname_text.setTextColor(Color.parseColor("#c377e0"));
            } else {
                ci_nickname_text.setTextColor(Color.parseColor("#000000"));
            }
            ImageView imageView = convertView.findViewById(R.id.imageView1);
            if (arr.get(position).getNickname().equals("비공개 댓글")) {
                imageView.setImageResource(R.drawable.ic_myinfo_password);
            } else {
                imageView.setImageResource(R.drawable.baseline_account_box_24);
            }
            TextView ci_content_text = convertView.findViewById(R.id.ci_content_text);
            ci_content_text.setText(arr.get(position).getContent());
            TextView ci_date_text = convertView.findViewById(R.id.ci_date_text);
            ci_date_text.setText(arr.get(position).getDate());
            TextView ci_private_text = convertView.findViewById(R.id.ci_private_text);
            if (arr.get(position).getCheck() == 1) {
                ci_private_text.setText("비공개 댓글");
                if (arr.get(position).getNickname().equals("비공개 댓글")) {
                    ci_private_text.setText("");
                }
            } else {
                ci_private_text.setText("");
            }
            Button ci_delete_btn = convertView.findViewById(R.id.ci_delete_btn);
            ci_delete_btn.setOnClickListener(this);
            ci_delete_btn.setTag(position +"");

            if (arr.get(position).getNickname().equals(nickname)) {
                ci_delete_btn.setVisibility(View.VISIBLE);
            } else {
                ci_delete_btn.setVisibility(View.INVISIBLE);
            }
        }
        return convertView;
    }
    public void onClick(View v){
        final int tag = Integer.parseInt(v.getTag().toString());
        switch(v.getId()){
            case R.id.ci_delete_btn:
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(mActivity);
                alertDlg.setPositiveButton("삭제", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        ma.deleteArr(tag);
                        Toast.makeText(mContext, "댓글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDlg.setNegativeButton("취소", null);
                alertDlg.setTitle("댓글 삭제");
                alertDlg.setMessage("정말 삭제 하시겠습니까?");
                alertDlg.show();
                break;
        }
    }
}