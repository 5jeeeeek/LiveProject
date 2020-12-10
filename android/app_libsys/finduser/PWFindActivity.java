package com.example.app_libsys.finduser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_libsys.R;

import java.util.HashMap;
import java.util.Map;

public class PWFindActivity extends AppCompatActivity {
    private static final String URL_PW_FIND = "http://www.minback.com/users/pwfind";
    private RequestQueue queue;
    private String response_q, response_a;
    private String pwFindResponse = "";
    EditText idText, nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_find);

        queue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("잠시만 기다려 주세요...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        final StringRequest stringPwFindRequest = getStringPwFindRequest();

        idText = findViewById(R.id.idText);
        nameText = findViewById(R.id.nameText);

        Button pw_findButton = findViewById(R.id.pw_findButton1);
        pw_findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idText.getText().toString().equals("")) {
                    Snackbar.make(v, "아이디를 입력해주세요.", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else if (nameText.getText().toString().equals("")) {
                    Snackbar.make(v, "이름을 입력해주세요.", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
                    progressDialog.show();
                    queue.add(stringPwFindRequest);

                    Handler timer = new Handler();
                    timer.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (pwFindResponse.equals("0")) {
                                setDialog("검색된 데이터가 없습니다.");
                            } else {
                                String[] splitText= pwFindResponse.split(",");
                                response_q = splitText[0].replace("\"", "").replace("[","");
                                response_a = splitText[1].replace("\"", "").replace("]","");
                                Log.d("질문", response_q);
                                Log.d("답변", response_a);

                                Intent pw_findIntent1 = new Intent(PWFindActivity.this, PWFindActivity2.class);
                                pw_findIntent1.putExtra("question", response_q);
                                pw_findIntent1.putExtra("answer", response_a);
                                pw_findIntent1.putExtra("id", idText.getText().toString());
                                startActivity(pw_findIntent1);
                                finish();
                                overridePendingTransition(R.anim.in, R.anim.out);
                            }
                        }
                    }, 500);
                }
            }
        });
    }

    private StringRequest getStringPwFindRequest() {
        return new StringRequest(Request.Method.POST, URL_PW_FIND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pwFindResponse = response;
            }
        }, new Response.ErrorListener() {
            @Override

            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", idText.getText().toString());
                params.put("name", nameText.getText().toString());
                return params;
            }
        };
    }

    public void setDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PWFindActivity.this);
        AlertDialog dialog = builder.setMessage(text)
                .setPositiveButton("확인", null)
                .create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
    }
}
