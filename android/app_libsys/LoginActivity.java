package com.example.app_libsys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_libsys.finduser.IDFindActivity;
import com.example.app_libsys.finduser.PWFindActivity;
import com.example.app_libsys.pw.AES256Util;
import com.example.app_libsys.signup.RegisterActivity;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class LoginActivity extends AppCompatActivity {
    private static final String URL_LOGIN = "http://www.minback.com/users/login";
    private AlertDialog dialog;
    private RequestQueue queue;
    private static final String TAG = "MAIN";
    public String response_var = "";

    String key = "aes256-test-key!!";
    AES256Util aes256 = new AES256Util(key);

    public LoginActivity() throws UnsupportedEncodingException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        queue = Volley.newRequestQueue(this);

        final EditText idText = findViewById(R.id.idText);
        final EditText passwordText = findViewById(R.id.passwordText);
        final Button loginButton = findViewById(R.id.loginButton);

        TextView registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        TextView id_findButton = findViewById(R.id.id_search);
        id_findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent id_findIntent = new Intent(LoginActivity.this, IDFindActivity.class);
                LoginActivity.this.startActivity(id_findIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        TextView pw_findButton = findViewById(R.id.pw_search);
        pw_findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pw_findIntent = new Intent(LoginActivity.this, PWFindActivity.class);
                LoginActivity.this.startActivity(pw_findIntent);
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        final StringRequest stringLoginRequest = login(idText, passwordText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idText.getText().toString();

                if (id.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    dialog = builder.setMessage("아이디를 입력해주세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                } else {
                    queue.add(stringLoginRequest);
                }
            }
        });
    }

    private StringRequest login(final EditText idText, final EditText passwordText) {
        return new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(final String response) {
                    response_var = response;

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                    switch (response) {
                        case "2":
                            dialog = builder.setMessage("비밀번호가 틀렸습니다.")
                                    .setPositiveButton("확인", null)
                                    .create();
                            dialog.show();
                            break;
                        case "3":
                            dialog = builder.setMessage("아이디가 틀렸습니다.")
                                    .setPositiveButton("확인", null)
                                    .create();
                            dialog.show();
                            break;
                        case "4":
                            dialog = builder.setMessage("블랙리스트 회원입니다.\n ck2711@naver.com로 문의주세요.")
                                    .setPositiveButton("확인", null)
                                    .create();
                            dialog.show();
                            break;
                        default:
                            saveIDandPW(idText, passwordText);
                            startToMainActivity();
                            break;
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    String text = passwordText.getText().toString();
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

                    params.put("id", idText.getText().toString());
                    params.put("pw", encText);
                    return params;
                }
            };
    }

    private void startToMainActivity() {
        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
        loginIntent.putExtra("response_var", response_var);
        startActivity(loginIntent);
        finish();
        overridePendingTransition(R.anim.in, R.anim.out);
    }

    private void saveIDandPW(EditText idText, EditText passwordText) {
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", idText.getText().toString());
        editor.putString("pw", passwordText.getText().toString());
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(queue != null) {
            queue.cancelAll(TAG);
        }
    }
}