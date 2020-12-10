package com.example.app_libsys.finduser;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.example.app_libsys.LoginActivity;
import com.example.app_libsys.R;

import java.util.HashMap;
import java.util.Map;

public class IDFindActivity extends AppCompatActivity {
    private static final String URL_ID_FIND = "http://www.minback.com/email/idfind";
    private RequestQueue queue;
    private AlertDialog dialog;
    private EditText emailText, emailCodeText;
    private String response_id, response_code;
    private String idFindResponse = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_find);

        queue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("잠시만 기다려 주세요...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        emailText = findViewById(R.id.emailText);
        emailCodeText = findViewById(R.id.emailCodeText);
        Button emailCertificationButton = findViewById(R.id.emailCertificationButton);
        Button idFindButton = findViewById(R.id.id_findButton);

        final StringRequest stringIdFindRequest = getStringIdFindRequest();

        emailCertificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailText.getText().toString().equals("")) {
                    Snackbar.make(v, "이메일을 입력해주세요.", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
                    progressDialog.show();
                    queue.add(stringIdFindRequest);

                    Handler timer = new Handler();
                    timer.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();

                            if (idFindResponse.equals("0")) {
                                setDialog("등록되지 않은 이메일입니다.");
                            } else {
                                String[] splitText= idFindResponse.split(",");
                                response_id = splitText[0].replace("\"", "").replace("[","");
                                response_code = splitText[1].replace("\"", "").replace("]","");
                                Log.d("아이디", response_id);
                                Log.d("코드", response_code);

                                setDialog("인증 코드를 발송하였습니다.");
                            }
                        }
                    }, 1000);
                }
            }
        });

        final DialogInterface.OnClickListener idFindListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent idFindIntent = new Intent(IDFindActivity.this, LoginActivity.class);
                startActivity(idFindIntent);
                finish();
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        };

        idFindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailCodeText.getText().toString().equals(response_code)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(IDFindActivity.this);
                    dialog = builder.setMessage("찾으시는 아이디는 " + response_id + "입니다.")
                            .setPositiveButton("확인", idFindListener)
                            .setCancelable(false)
                            .create();
                    dialog.show();
                } else {
                    if (emailText.getText().toString().equals("")) {
                        Snackbar.make(v, "이메일을 입력해주세요.", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    } else {
                        setDialog("인증 코드가 틀렸습니다.");
                    }
                }
            }
        });
    }

    private StringRequest getStringIdFindRequest() {
        return new StringRequest(Request.Method.POST, URL_ID_FIND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                idFindResponse = response;
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
                params.put("email", emailText.getText().toString());
                return params;
            }
        };
    }

    public void setDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(IDFindActivity.this, R.style.MyDialogTheme);
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
