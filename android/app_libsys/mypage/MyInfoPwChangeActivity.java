package com.example.app_libsys.mypage;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.example.app_libsys.R;
import com.example.app_libsys.pw.AES256Util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MyInfoPwChangeActivity extends AppCompatActivity {
    private AlertDialog dialog;
    private static final String TAG = "MAIN";
    private RequestQueue queue;

    String key = "aes256-test-key!!";
    AES256Util aes256 = new AES256Util(key);

    public MyInfoPwChangeActivity() throws UnsupportedEncodingException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo_pw_change);

        queue = Volley.newRequestQueue(this);
        String url_pwchange = "http://www.minback.com/users/pwchange";



        final Intent myInfoPwChangeIntent = getIntent();
        final String nickname_res = myInfoPwChangeIntent.getStringExtra("nickname");
        final String id_res = myInfoPwChangeIntent.getStringExtra("id");

        final EditText newPwText = findViewById(R.id.newPwText);
        final EditText newPwConfirmText = findViewById(R.id.newPwConfirmText);



        final StringRequest stringPwChangeRequest = new StringRequest(Request.Method.POST, url_pwchange, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                saveIdAndPW(newPwText.getText().toString());
                AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoPwChangeActivity.this);
                dialog = builder.setMessage("비밀번호가 변경되었습니다.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                overridePendingTransition(R.anim.in, R.anim.out);
                            }
                        })
                        .setCancelable(false)
                        .create();
                dialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                String text = newPwText.getText().toString();
                String encText = null;
                try {
                    encText = aes256.aesEncode(text);
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

                Log.d("암호화할 문자 : ", text);
                Log.d("암호화된 문자 : ", encText);
                Log.d("복호화된 문자 : ", decText);

                params.put("pw", encText);
                params.put("id", id_res);

                return params;
            }
        };

        stringPwChangeRequest.setTag(TAG);

        final Button pw_changeButton = findViewById(R.id.pw_changeButton);
        pw_changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw_editText = newPwText.getText().toString();
                String pwConfirm_editText = newPwConfirmText.getText().toString();

                if (pw_editText.length() < 8 || pw_editText.length() > 12) {
                    Snackbar.make(v, "비밀번호는 8~12 자리여야 합니다.", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else if (pw_editText.equals(pwConfirm_editText)) {
                    queue.add(stringPwChangeRequest);

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoPwChangeActivity.this);
                    dialog = builder.setMessage("비밀번호를 확인해주세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }

            }
        });
    }
    private void saveIdAndPW(String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("pw", password);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
    }
}
