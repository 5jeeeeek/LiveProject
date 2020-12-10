package com.example.app_libsys.mypage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_libsys.R;

import java.util.HashMap;
import java.util.Map;

public class MyInfoQuestionChangeActivity extends AppCompatActivity {

    private static final String TAG = "MAIN";
    private ArrayAdapter adapter;
    private Spinner spinner;
    private RequestQueue queue;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo_question_change);

        final Intent myInfoQuestionChangeIntent = getIntent();
        final String nickname_res = myInfoQuestionChangeIntent.getStringExtra("nickname");
        final String id_res = myInfoQuestionChangeIntent.getStringExtra("id");

        spinner = (Spinner) findViewById(R.id.questionSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.question, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText questionText = findViewById(R.id.questionText);
        final EditText answerText = findViewById(R.id.answerText);
        queue = Volley.newRequestQueue(this);
        String url_qachange = "http://www.minback.com/users/qachange";

        final StringRequest stringQnARequest = new StringRequest(Request.Method.POST, url_qachange, new Response.Listener<String>() {
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
//                params.put("nickname", nickname_res);
                params.put("id", id_res);
                params.put("pwqa", answerText.getText().toString());
                if (spinner.getSelectedItemPosition() == 4) {
                    params.put("pwq", questionText.getText().toString());
                } else {
                    params.put("pwq", spinner.getSelectedItem().toString());
                }
                return params;
            }
        };

        stringQnARequest.setTag(TAG);

        Button qnaChangeButton = findViewById(R.id.qnaChangeButton);
        qnaChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = spinner.getSelectedItem().toString();
                String answer = answerText.getText().toString();

                if (question.equals("") || answer.equals("")) {
                    Snackbar.make(v, "빈 칸 없이 입력해주세요.", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
                    queue.add(stringQnARequest);

                    AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoQuestionChangeActivity.this);
                    dialog = builder.setMessage("질문과 답변이 변경되었습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MyPageActivity MP = (MyPageActivity)MyPageActivity._MyPageActivity;
                                    MP.finish();
                                    MyInfoActivity MI = (MyInfoActivity)MyInfoActivity._MyInfoActivity;
                                    MI.finish();
                                    finish();

                                    overridePendingTransition(R.anim.in, R.anim.out);
                                }
                            })
                            .setCancelable(false)
                            .create();
                    dialog.show();
                }
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner.getSelectedItemPosition() == 4) {
                    questionText.setVisibility(View.VISIBLE);
                } else {
                    questionText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
    }
}
