package com.example.app_libsys.finduser;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class PWFindActivity3 extends AppCompatActivity {
    private AlertDialog dialog;
    private static final String URL_PW_CHANGE = "http://www.minback.com/users/pwchange";
    private RequestQueue queue;
    EditText newPwText, newPwConfirmText;
    Button pw_findButton3;

    String key = "aes256-test-key!!";
    AES256Util aes256 = new AES256Util(key);

    public PWFindActivity3() throws UnsupportedEncodingException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_find3);

        queue = Volley.newRequestQueue(this);

        Intent pw_findIntent2 = getIntent();
        final String id = pw_findIntent2.getStringExtra("id");

        newPwText = findViewById(R.id.newPwText);
        newPwConfirmText = findViewById(R.id.newPwConfirmText);
        pw_findButton3 = findViewById(R.id.pw_changeButton);

        final StringRequest stringPwChangeRequest = new StringRequest(Request.Method.POST, URL_PW_CHANGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(PWFindActivity3.this, response, Toast.LENGTH_SHORT).show();
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

                params.put("id", id);
                params.put("pw", encText);
                return params;
            }
        };


        final DialogInterface.OnClickListener dialogFinish = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        };

        pw_findButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newPwText.getText().toString().equals(newPwConfirmText.getText().toString())) {
//                    Toast.makeText(getApplicationContext(), "비밀번호 일치.", Toast.LENGTH_SHORT).show();
                    if (newPwText.getText().toString().length() < 8 || newPwText.getText().toString().length() > 12) {
                        Toast.makeText(getApplicationContext(), "비밀번호는 8 - 12 자리여야 합니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        queue.add(stringPwChangeRequest);

                        AlertDialog.Builder builder = new AlertDialog.Builder(PWFindActivity3.this);
                        dialog = builder.setMessage("비밀번호 변경이 완료되었습니다.")
                                .setPositiveButton("확인", dialogFinish)
                                .create();
                        dialog.show();


//                        Intent pw_findIntent3 = new Intent(PWFindActivity3.this, LoginActivity.class);
//                        startActivity(pw_findIntent3);
//                        finish();
                    }

                } else {
//                    Toast.makeText(getApplicationContext(), "비밀번호 불일치.", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(PWFindActivity3.this);
                    dialog = builder.setMessage("비밀번호가 일치하지 않습니다.")
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
