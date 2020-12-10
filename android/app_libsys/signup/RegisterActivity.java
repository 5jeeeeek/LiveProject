package com.example.app_libsys.signup;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_libsys.LoginActivity;
import com.example.app_libsys.R;
import com.example.app_libsys.pw.AES256Util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RegisterActivity extends AppCompatActivity {

    public String idCheckResponse;
    public String nickCheckResponse;

    private boolean success = false;
    private boolean id_validate = false;
    private boolean nick_validate = false;
    private boolean email_validate = false;

    private RequestQueue queue;

    private static final String URL_JOIN = "http://www.minback.com/users/join";
    private static final String URL_ID_CHECK = "http://www.minback.com/users/idcheck";
    private static final String URL_NICK_CHECK = "http://www.minback.com/users/nickcheck";
    private static final String URL_EMAIL_CHECK = "http://www.minback.com/email/emailcheck";

    private AlertDialog dialog;
    private Spinner spinner;
    private EditText idText;

    private String emailCode = "";

    String key = "aes256-test-key!!";
    AES256Util aes256 = new AES256Util(key);

    public RegisterActivity() throws UnsupportedEncodingException {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        queue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("잠시만 기다려 주세요...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        spinner = findViewById(R.id.questionSpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.question, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText passwordText = findViewById(R.id.passwordText);
        final EditText emailText = findViewById(R.id.emailText);
        final EditText emailCodeText = findViewById(R.id.emailCodeText);
        final EditText nameText = findViewById(R.id.nameText);
        final EditText nicknameText = findViewById(R.id.nicknameText);
        final EditText phoneText = findViewById(R.id.phoneText);
        final EditText questionText = findViewById(R.id.questionText);
        final EditText answerText = findViewById(R.id.answerText);

        final Button emailCertificationButton = findViewById(R.id.emailCertificationButton);
        final Button emailConfirmButton = findViewById(R.id.emailConfirmButton);

        idText = findViewById(R.id.idText);

        final StringRequest stringEmailCheckRequest = checkEmail(emailText);

        final StringRequest stringIDCheckRequest = checkId();

        final Button validateButton = findViewById(R.id.idvalidateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idText.getText().toString();
                boolean idCheck = Pattern.matches("^[a-zA-Z0-9]+$", id);

                if (id.equals("")) {
                    setDialog("아이디는 빈 칸 일 수 없습니다.");
                } else if (id.length() < 4 || id.length() > 10) {
                    setDialog("아이디는 4자 이상 10자 이하 입니다.");
                } else if (!idCheck) {
                    setDialog("아이디는 영문자와 숫자만 \n사용 가능합니다.");
                } else {
                    progressDialog.show();
                    queue.add(stringIDCheckRequest);

                    Handler timer = new Handler();
                    timer.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (idCheckResponse.equals("1")) {
                                queue.add(stringIDCheckRequest);
                                setDialog("사용 가능한 아이디입니다.");
                                id_validate = true;
                            } else if (idCheckResponse.equals("0")) {
                                queue.add(stringIDCheckRequest);
                                setDialog("이미 사용중인 아이디입니다.");
                                id_validate = false;
                            }
                        }
                    }, 500);
                }
            }
        });


        final StringRequest stringNickCheckRequest = checkNickname(nicknameText);

        final Button nickValidateButton = findViewById(R.id.nickvalidateButton);
        nickValidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nick = nicknameText.getText().toString();
                boolean nickCheck = Pattern.matches("^[a-zA-Z0-9가-힣]+$", nick);

                if (nick.equals("")) {
                    setDialog("닉네임은 빈 칸 일 수 없습니다.");
                } else if (nick.length() < 2 || nick.length() > 6) {
                    setDialog("닉네임은 2자 이상 6자 이하 입니다.");
                } else if (!nickCheck) {
                    setDialog("닉네임은 한글과 영문자,\n숫자만 사용 가능합니다.");
                } else {
                    progressDialog.show();
                    queue.add(stringNickCheckRequest);

                    Handler timer = new Handler();
                    timer.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (nickCheckResponse.equals("1")) {
                                queue.add(stringNickCheckRequest);
                                setDialog("사용 가능한 닉네임입니다.");
                                nick_validate = true;
                            } else if (nickCheckResponse.equals("0")) {
                                queue.add(stringNickCheckRequest);
                                setDialog("이미 사용중인 닉네임입니다.");
                                nick_validate = false;
                            }
                        }
                    }, 500);
                }
            }
        });

        final StringRequest stringJoinRequest = joinId(passwordText, emailText, nameText, nicknameText, phoneText, questionText, answerText);

        emailCertificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean email_check = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", emailText.getText().toString());
                if (!email_check) {
                    setDialog("잘못된 이메일 형식입니다.");
                } else {
                    progressDialog.show();
                    queue.add(stringEmailCheckRequest);

                    Handler timer = new Handler();
                    timer.postDelayed(new Runnable() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (emailCode.equals("0")) {
                                dialog = builder.setMessage("이미 사용중인 이메일입니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            } else {
                                dialog = builder.setMessage("인증 코드를 발송하였습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                    }, 500);
                }
            }
        });

        emailConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailCodeText.getText().toString().equals(emailCode)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("인증이 완료되었습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    email_validate = true;
                    emailConfirmButton.setText("인증완료");
                    emailConfirmButton.setTextColor(Color.BLACK);

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("인증코드가 틀렸습니다.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                }
            }
        });

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = idText.getText().toString();
                String pw = passwordText.getText().toString();
                String email = emailText.getText().toString();
                String userEmailCodeText = emailCodeText.getText().toString();
                String name = nameText.getText().toString();
                String nickname = nicknameText.getText().toString();
                String phone = phoneText.getText().toString();
                String question = spinner.getSelectedItem().toString();
                String answer = answerText.getText().toString();

                if (id.equals("") || pw.equals("") || email.equals("") ||
                        userEmailCodeText.equals("") || name.equals("") || nickname.equals("") ||
                        phone.equals("") || question.equals("") || answer.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                } else if (!id_validate) {
                    Toast.makeText(getApplicationContext(), "아이디 중복체크를 해주세요.", Toast.LENGTH_SHORT).show();
                } else if (!nick_validate) {
                    Toast.makeText(getApplicationContext(), "닉네임 중복체크를 해주세요.", Toast.LENGTH_SHORT).show();
                } else if (!email_validate) {
                    Toast.makeText(getApplicationContext(), "이메일 인증 코드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (pw.length() < 8 || pw.length() > 12) {
                    Toast.makeText(getApplicationContext(), "비밀번호는 8~12 자리여야 합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    success = true;
                }

                final DialogInterface.OnClickListener finishListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent finishIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        finish();
                        startActivity(finishIntent);
                        overridePendingTransition(R.anim.in, R.anim.out);
                    }
                };


                if (success) {
                    queue.add(stringJoinRequest);
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("회원가입이 완료되었습니다.")
                            .setPositiveButton("확인", finishListener)
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


    private StringRequest joinId(final EditText passwordText, final EditText emailText, final EditText nameText, final EditText nicknameText, final EditText phoneText, final EditText questionText, final EditText answerText) {
        return new StringRequest(Request.Method.POST, URL_JOIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                params.put("email", emailText.getText().toString());
                params.put("name", nameText.getText().toString());
                params.put("nickname", nicknameText.getText().toString());
                params.put("phone", phoneText.getText().toString());
                params.put("answer", answerText.getText().toString());

                if (spinner.getSelectedItemPosition() == 4) {
                    params.put("question", questionText.getText().toString());
                } else {
                    params.put("question", spinner.getSelectedItem().toString());
                }

                return params;
            }
        };
    }

    private StringRequest checkNickname(final EditText nicknameText) {
        return new StringRequest(Request.Method.POST, URL_NICK_CHECK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                nickCheckResponse = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nick", nicknameText.getText().toString());
                return params;
            }
        };
    }

    private StringRequest checkId() {
        return new StringRequest(Request.Method.POST, URL_ID_CHECK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                idCheckResponse = response;
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
                return params;
            }
        };
    }

    private StringRequest checkEmail(final EditText emailText) {
        return new StringRequest(Request.Method.POST, URL_EMAIL_CHECK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                emailCode = response;
                Log.d("이메일 인증코드", response);

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

    private void setDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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