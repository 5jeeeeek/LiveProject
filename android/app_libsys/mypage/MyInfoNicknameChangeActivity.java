package com.example.app_libsys.mypage;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_libsys.LoginActivity;
import com.example.app_libsys.MainActivity;
import com.example.app_libsys.R;

import java.util.HashMap;
import java.util.Map;

public class MyInfoNicknameChangeActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private static final String TAG = "MAIN";
    private RequestQueue queue;
    private boolean nick_validate = false;
    public String nickCheckResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo_nickname_change);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("잠시만 기다려 주세요...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        queue = Volley.newRequestQueue(this);
        String url_nickchange = "http://www.minback.com/users/nickchange";
        String url_nick_check = "http://www.minback.com/users/nickcheck";


        final Intent myInfoNicknameChangeIntent = getIntent();
        final String nickname_res = myInfoNicknameChangeIntent.getStringExtra("nickname");
        final String id_res = myInfoNicknameChangeIntent.getStringExtra("id");

        final EditText nicknameChangeText = findViewById(R.id.nicknameChangeText);

        final StringRequest stringNICKcheckRequest = new StringRequest(Request.Method.POST, url_nick_check, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                nickCheckResponse = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nick", nicknameChangeText.getText().toString());
                return params;
            }
        };

        final Button nickvalidateButton = findViewById(R.id.nickvalidateButton);
        nickvalidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                queue.add(stringNICKcheckRequest);

                Handler timer = new Handler();
                timer.postDelayed(new Runnable() {
                    String newNickname = nicknameChangeText.getText().toString();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoNicknameChangeActivity.this);

                    @Override
                    public void run() {
                        progressDialog.dismiss();

                        if (newNickname.equals("")) {
                            setDialog("닉네임은 빈 칸 일 수 없습니다.");
                        } else if (nickCheckResponse.equals("1")) {

                            queue.add(stringNICKcheckRequest);

                            dialog = builder.setMessage("사용 가능한 닉네임입니다.")
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .create();
                            dialog.show();
                            nick_validate = true;

                        } else if (nickCheckResponse.equals("0")) {
                            queue.add(stringNICKcheckRequest);

                            dialog = builder.setMessage("이미 사용중인 닉네임입니다.")
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .create();
                            dialog.show();
                        }
                    }
                }, 500);

            }
        });



        final StringRequest stringNickChangeRequest = new StringRequest(Request.Method.POST, url_nickchange, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nickname", nicknameChangeText.getText().toString());
                params.put("id", id_res);

                return params;
            }
        };

        stringNickChangeRequest.setTag(TAG);


        final DialogInterface.OnClickListener idFindListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveIdAndPW();
                Intent loginIntent = new Intent(MyInfoNicknameChangeActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                MainActivity MA = (MainActivity) MainActivity._Main_Activity;
                MA.finish();
                MyInfoActivity MI = (MyInfoActivity)MyInfoActivity._MyInfoActivity;
                MyPageActivity MP = (MyPageActivity)MyPageActivity._MyPageActivity;
                MI.finish();
                MP.finish();
                finish();
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        };


        final Button nickname_changeButton = findViewById(R.id.nickname_changeButton);
        nickname_changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nick_validate) {
                    Snackbar.make(v, "닉네임 중복체크를 해주세요.", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
                    queue.add(stringNickChangeRequest);

                    AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoNicknameChangeActivity.this);
                    dialog = builder.setMessage("닉네임이 변경되었습니다.\n다시 로그인 해주세요!")
                            .setPositiveButton("확인", idFindListener)
                            .setCancelable(false)
                            .create();
                    dialog.show();
                }

            }
        });
    }

    private void saveIdAndPW() {
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", "");
        editor.putString("pw", "");
        editor.apply();
    }

    private void setDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoNicknameChangeActivity.this);
        dialog = builder.setMessage(text)
                .setNegativeButton("확인", null)
                .create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
    }
}
