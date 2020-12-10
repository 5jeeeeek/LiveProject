package com.example.app_libsys.mypage;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class MyInfoActivity extends AppCompatActivity {
    public static Activity _MyInfoActivity;

    private AlertDialog dialog;
    String url_userout = "http://www.minback.com/users/userout";
    private RequestQueue queue;
    private static final String TAG = "MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);

        _MyInfoActivity = MyInfoActivity.this;


        queue = Volley.newRequestQueue(this);


        TextView myInfoName = findViewById(R.id.myInfoName);
        TextView myInfoEmail = findViewById(R.id.myInfoEmail);
        TextView myInfoID = findViewById(R.id.myInfoID);
        TextView myInfoPhone = findViewById(R.id.myInfoPhone);
        TextView myInfoNickname = findViewById(R.id.myInfoNickname);
        TextView myInfoQuestion = findViewById(R.id.myInfoQuestion);
        TextView myInfoAnswer = findViewById(R.id.myInfoAnswer);

        final Intent myInfoIntent = getIntent();
        final String nickname_res = myInfoIntent.getStringExtra("nickname");
        final String id_res = myInfoIntent.getStringExtra("id");
        final String email_res = myInfoIntent.getStringExtra("email");
        final String name_res = myInfoIntent.getStringExtra("name");
        final String phone_res = myInfoIntent.getStringExtra("phone");
        final String question_res = myInfoIntent.getStringExtra("question");
        final String answer_res = myInfoIntent.getStringExtra("answer");


        myInfoName.setText(name_res);
        myInfoEmail.setText(email_res);
        myInfoID.setText(id_res);
        myInfoPhone.setText(phone_res);
        myInfoNickname.setText(nickname_res);
        myInfoQuestion.setText(question_res);
        myInfoAnswer.setText(answer_res);



        TextView myInfoPwChange = findViewById(R.id.myInfoPwChange);
        myInfoPwChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myInfoPwChangeIntent = new Intent(MyInfoActivity.this, MyInfoPwChangeActivity.class);
                myInfoPwChangeIntent.putExtra("nickname", nickname_res);
                myInfoPwChangeIntent.putExtra("id", id_res);
                MyInfoActivity.this.startActivity(myInfoPwChangeIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        TextView myInfoNicknameChange = findViewById(R.id.myInfoNicknameChange);
        myInfoNicknameChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myInfoNicknameChangeIntent = new Intent(MyInfoActivity.this, MyInfoNicknameChangeActivity.class);
                myInfoNicknameChangeIntent.putExtra("nickname", nickname_res);
                myInfoNicknameChangeIntent.putExtra("id", id_res);
                MyInfoActivity.this.startActivity(myInfoNicknameChangeIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        TextView myInfoQuestionChange = findViewById(R.id.myInfoQuestionChange);
        myInfoQuestionChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myInfoQuestionChangeIntent = new Intent(MyInfoActivity.this, MyInfoQuestionChangeActivity.class);
                myInfoQuestionChangeIntent.putExtra("nickname", nickname_res);
                myInfoQuestionChangeIntent.putExtra("id", id_res);
                MyInfoActivity.this.startActivity(myInfoQuestionChangeIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });


        final StringRequest stringRemoveRequest = new StringRequest(Request.Method.POST, url_userout, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("1")) {
                    saveIdAndPW();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("nickname", nickname_res);
                params.put("id", id_res);
                return params;
            }
        };

        final DialogInterface.OnClickListener removeListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                queue.add(stringRemoveRequest);


                Intent loginIntent = new Intent(MyInfoActivity.this, LoginActivity.class);
                MyInfoActivity.this.startActivity(loginIntent);

                MyInfoActivity MI = (MyInfoActivity)MyInfoActivity._MyInfoActivity;
                MyPageActivity MP = (MyPageActivity)MyPageActivity._MyPageActivity;
                MainActivity MA = (MainActivity)MainActivity._Main_Activity;
                MI.finish();
                MP.finish();
                MA.finish();
            }
        };


        LinearLayout myInfoRemove = findViewById(R.id.myInfoRemove);
        myInfoRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoActivity.this);
                dialog = builder.setMessage("정말 회원탈퇴를 하시겠습니까?")
                        .setPositiveButton("확인", removeListener)
                        .setNegativeButton("취소", null)
                        .create();
                dialog.show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
    }
}
