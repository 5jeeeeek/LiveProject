package com.example.app_libsys.finduser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.app_libsys.mypage.MyInfoActivity;
import com.example.app_libsys.R;
import com.example.app_libsys.pw.AES256Util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class PWConfirmActivity extends AppCompatActivity {
    private AlertDialog dialog;

    String key = "aes256-test-key!!";
    AES256Util aes256 = new AES256Util(key);

    public PWConfirmActivity() throws UnsupportedEncodingException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_confirm);

        final Intent pwConfirmIntent = getIntent();
        final String id_res = pwConfirmIntent.getStringExtra("id");
        final String pw_res = pwConfirmIntent.getStringExtra("pw");
        final String email_res = pwConfirmIntent.getStringExtra("email");
        final String name_res = pwConfirmIntent.getStringExtra("name");
        final String nickname_res = pwConfirmIntent.getStringExtra("nickname");
        final String phone_res = pwConfirmIntent.getStringExtra("phone");
        final String question_res = pwConfirmIntent.getStringExtra("question");
        final String answer_res = pwConfirmIntent.getStringExtra("answer");

        final EditText mypagePwConfirmText = findViewById(R.id.mypagePwConfirmText);



        Button mypagePwConfirmButton = findViewById(R.id.mypagePwConfirmButton);
        mypagePwConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw = mypagePwConfirmText.getText().toString();

                String encText = null;
                try {
                    encText = aes256.aesEncode(pw);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }

                String decText = null;
                try {
                    decText = aes256.aesDecode(encText);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }

                Log.d("암호화할 문자 : ", pw);
                Log.d("암호화된 문자 : ", encText);
                Log.d("복호화된 문자 : ", decText);

                if (encText.equals(pw_res)) {
                    Intent myInfoIntent = new Intent(PWConfirmActivity.this, MyInfoActivity.class);
                    myInfoIntent.putExtra("nickname", nickname_res);
                    myInfoIntent.putExtra("id", id_res);
                    myInfoIntent.putExtra("pw", pw_res);
                    myInfoIntent.putExtra("email", email_res);
                    myInfoIntent.putExtra("name", name_res);
                    myInfoIntent.putExtra("phone", phone_res);
                    myInfoIntent.putExtra("question", question_res);
                    myInfoIntent.putExtra("answer", answer_res);
                    PWConfirmActivity.this.startActivity(myInfoIntent);
                    finish();
                    overridePendingTransition(R.anim.in, R.anim.out);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PWConfirmActivity.this);
                    dialog = builder.setMessage("비밀번호를 확인해주세요.")
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
