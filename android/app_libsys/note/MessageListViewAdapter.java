package com.example.app_libsys.note;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_libsys.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-10-13.
 */
public class MessageListViewAdapter extends BaseAdapter {
    private LayoutInflater inflate;
    private List<String> list;
    private ArrayList<MessageItem> data;
    private int layout;

    public MessageListViewAdapter(Context context, int layout, ArrayList<MessageItem> data) {
        this.inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }
    @Override
    public int getCount() { //리스트 안 Item의 개수를 센다.
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position).getNtitle();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflate.inflate(layout, parent, false);
        }
        MessageItem messageItem = data.get(position);

        final ImageView receiveImageView = convertView.findViewById(R.id.receiveImageView);

        if (messageItem.getNimage().equals("0")) {

        } else  {
            receiveImageView.setImageResource(R.drawable.ic_image);
        }


        TextView title = convertView.findViewById(R.id.rtitleView);
        title.setText(messageItem.getNtitle());

        TextView text = convertView.findViewById(R.id.rdescView);
        text.setText(messageItem.getNtext());

        TextView id = convertView.findViewById(R.id.ridView);
        id.setText(messageItem.getNsend());

        TextView state = convertView.findViewById(R.id.stateValue);
        String stateValue = String.valueOf(messageItem.getNstate());
        if (stateValue.equals("0")) {
            state.setText("읽지 않음");
            state.setTextColor(Color.parseColor("#c377e0"));
        } else if (stateValue.equals("1")) {
            state.setText("읽음");
            state.setTextColor(Color.BLACK);
        }

        return convertView;
    }
}