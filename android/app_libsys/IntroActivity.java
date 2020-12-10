package com.example.app_libsys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

public class IntroActivity extends AppCompatActivity {
    String key = "aes256-test-key!!";
    AES256Util aes256 = new AES256Util(key);

    public IntroActivity() throws UnsupportedEncodingException {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        final String id = sharedPreferences.getString("id", "");
        final String pw = sharedPreferences.getString("pw", "");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int newUiOptions = getWindow().getDecorView().getSystemUiVisibility();

//            newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
//            newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        }

        if (id.equals("") && pw.equals("")) {
            startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            finish();
        } else {
            String url_login = "http://www.minback.com/users/login";
            Volley.newRequestQueue(this).add(new StringRequest(Request.Method.POST, url_login, new Response.Listener<String>() {
                @Override
                public void onResponse(final String response) {
                    login(response);
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
                    String text = pw;
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

                    params.put("id", id);
                    params.put("pw", encText);
                    return params;
                }
            });
        }
    }

    private void login(String response) {
        AlertDialog.Builder builder = new AlertDialog.Builder(IntroActivity.this);

        switch (response) {
            case "2":
                AlertDialog dialog = builder.setMessage("비밀번호가 틀렸습니다.")
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
                startToMainActivity(response);
                break;
        }
    }

    private void startToMainActivity(final String response_var) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(IntroActivity.this, MainActivity.class);
                loginIntent.putExtra("response_var", response_var);
                startActivity(loginIntent);
                finish();
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        }, 2000);
    }

    @Override
    public void onBackPressed() {

    }
}
