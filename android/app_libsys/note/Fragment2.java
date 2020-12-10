package com.example.app_libsys.note;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

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

public class Fragment2 extends Fragment {
    private RequestQueue queue;
    private static final String URL_NOTE_RECEIVE_LIST = "http://www.minback.com/note/notereceivelist";
    private static final String URL_NOTE_VIEW = "http://www.minback.com/note/noteview";
    private String ncode;

    private ArrayList<MessageItem> messageItemArrayList = new ArrayList<>();
    private ArrayList<String> ntitleList = new ArrayList<>();
    private ArrayList<String> ntextList = new ArrayList<>();
    private ArrayList<String> nsendList = new ArrayList<>();
    private ArrayList<String> nreceiveList = new ArrayList<>();
    private ArrayList<String> ndateList = new ArrayList<>();
    private ArrayList<String> nimageList = new ArrayList<>();
    private ArrayList<Integer> ncodeList = new ArrayList<>();
    private ArrayList<Integer> nstateList = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2,container,false);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("잠시만 기다려 주세요...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);


        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        final String id = sharedPreferences.getString("id", "");

        final ListView receiveListView = view.findViewById(R.id.receiveListView);
        final MessageListViewAdapter adapter = new MessageListViewAdapter(context, R.layout.message_receive_listview_item, messageItemArrayList);
        receiveListView.setAdapter(adapter);
        resetAdapter(adapter);

        LinearLayout helpView = view.findViewById(R.id.helpView);
        receiveListView.setEmptyView(helpView);

        startMessageClickedActivity(receiveListView, id);

        final StringRequest stringNoteReceiveListRequest = new StringRequest(Request.Method.POST, URL_NOTE_RECEIVE_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                setMessageItemList(response);
//                Toast.makeText(getActivity(), "받은 쪽지함의 response : " + response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                return params;
            }
        };
        progressDialog.show();
        queue.add(stringNoteReceiveListRequest);

        Handler timer = new Handler();
        timer.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                resetAdapter(adapter);
            }
        }, 200);

        return view;
    }

    private void setMessageItemList(String response_var) {
        try {
            JSONArray jsonArray = new JSONArray(response_var);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                ncodeList.add(jsonObject.getInt("ncode"));
                nstateList.add(jsonObject.getInt("nstate"));

                ntitleList.add(jsonObject.getString("ntitle"));
                ntextList.add(jsonObject.getString("ntext"));
                nsendList.add(jsonObject.getString("nsend"));
                nreceiveList.add(jsonObject.getString("nreceive"));
                ndateList.add(jsonObject.getString("ndate"));
                nimageList.add(jsonObject.getString("nimage"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < ntitleList.size(); i++) {
            messageItemArrayList.add(new MessageItem(ntitleList.get(i), ntextList.get(i), nsendList.get(i),
                    nreceiveList.get(i), ncodeList.get(i), nstateList.get(i), ndateList.get(i), nimageList.get(i)));
        }
    }

    private void startMessageClickedActivity(ListView listView, final String id_res) {
        final StringRequest stringNoteViewRequest = new StringRequest(Request.Method.POST, URL_NOTE_VIEW, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getActivity(), "stringNoteViewRequest의 response : " + response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("ncode", ncode);
                return params;
            }
        };


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ncode = ncodeList.get(position).toString();
                queue.add(stringNoteViewRequest);

                Intent MessageClickedIntent = new Intent(getContext(), ReceiveDetailActivity.class);

                MessageClickedIntent.putExtra("messageItemArrayList", messageItemArrayList);
                MessageClickedIntent.putExtra("position", position);
                MessageClickedIntent.putExtra("id", id_res);

                startActivity(MessageClickedIntent);
                getActivity().overridePendingTransition(R.anim.in, R.anim.out);
            }
        });
    }

    public void resetAdapter(final MessageListViewAdapter adapter){
        adapter.notifyDataSetChanged();
    }
}