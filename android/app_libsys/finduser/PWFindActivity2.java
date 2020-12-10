package com.example.app_libsys.finduser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app_libsys.R;

public class PWFindActivity2 extends AppCompatActivity {
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_find2);

        Intent pw_findIntent1 = getIntent();
        String response_question = pw_findIntent1.getStringExtra("question");
        final String response_answer = pw_findIntent1.getStringExtra("answer");
        final String response_id = pw_findIntent1.getStringExtra("id");

        TextView questionView = findViewById(R.id.questionView);
        questionView.setText(response_question);

        final EditText answerText = findViewById(R.id.answerText);

        Button pw_findButton2 = findViewById(R.id.pw_findButton2);
        pw_findButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (answerText.getText().toString().equals(response_answer)) {
//                    Toast.makeText(getApplicationContext(), "정답", Toast.LENGTH_SHORT).show();

                    Intent pw_findIntent2 = new Intent(PWFindActivity2.this, PWFindActivity3.class);
                    pw_findIntent2.putExtra("id", response_id);
                    startActivity(pw_findIntent2);
                    finish();
                    overridePendingTransition(R.anim.in, R.anim.out);
                } else {
//                    Toast.makeText(getApplicationContext(), "오답", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(PWFindActivity2.this);
                    dialog = builder.setMessage("답변이 일치하지 않습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
    }
}
