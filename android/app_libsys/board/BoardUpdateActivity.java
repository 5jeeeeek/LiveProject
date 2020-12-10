package com.example.app_libsys.board;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_libsys.R;
import com.example.app_libsys.board.bitmap.BitmapImage;
import com.example.app_libsys.board.bitmap.NoneBitmapImage;
import com.example.app_libsys.board.bitmap.UploadBitmapImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BoardUpdateActivity extends AppCompatActivity {
    private final int GET_GALLERY_IMAGE = 200;
    private RequestQueue queue;
    private AlertDialog dialog;
    private ImageView boardUpdateImage;
    private static final String URL_BOARD_UPDATE = "http://www.minback.com/board/boardchange";
    private static final String URL_IMAGE_UPLOAD = "http://www.minback.com/imgupload/boardimg";
    private static final String URL_IMAGE_DELETE = "http://www.minback.com/imgupload/boardimgdel";
    private String bimage = "0";
    private BitmapImage bitmap = new NoneBitmapImage(null);
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String filePath;
    private TextView boardImageUpdateText;
    private String category = "1";
    private boolean imageTouch = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_update);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("잠시만 기다려 주세요...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        final EditText boardUpdateTitleText = findViewById(R.id.boardUpdateTitleText);
        final EditText boardUpdateDescText = findViewById(R.id.boardUpdateDescText);
        final Button boardUpdateButton = findViewById(R.id.boardUpdateButton);
        final CheckBox imageCheck = findViewById(R.id.imageCheck);
        ImageButton boardUpdateExitButton = findViewById(R.id.boardUpdateExitButton);

        boardUpdateImage = findViewById(R.id.boardUpdateImage);
        boardImageUpdateText = findViewById(R.id.boardImageUpdateText);

        queue = Volley.newRequestQueue(this);


        Date currentTime = Calendar.getInstance().getTime();
        final String date_text = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(currentTime);

        final Intent boardUpdateIntent = getIntent();
        final String id_res = boardUpdateIntent.getStringExtra("id");
        final String nickname_var = boardUpdateIntent.getStringExtra("nickname");
        final String bcode_var = boardUpdateIntent.getStringExtra("bcode");
        final String bimage_var = boardUpdateIntent.getStringExtra("bimage");
        final String title_var = boardUpdateIntent.getStringExtra("title");
        final String text_var = boardUpdateIntent.getStringExtra("text");

        boardUpdateTitleText.setText(title_var);
        boardUpdateDescText.setText(text_var);

        final StringRequest stringBoardUpdateRequest = new StringRequest(Request.Method.POST, URL_BOARD_UPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("btitle", boardUpdateTitleText.getText().toString());
                params.put("btext", boardUpdateDescText.getText().toString());
                params.put("bcate", category);
                params.put("bcode", bcode_var);
                params.put("bimage", bimage);
                return params;
            }
        };

        boardUpdateExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        boardUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(BoardUpdateActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(BoardUpdateActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(BoardUpdateActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                } else {
                    Log.e("Else", "Else");
                    showFileChooser();
                }
            }
        });

        Switch categorySwitch = findViewById(R.id.categorySwitch);
        categorySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    category = "2";
                    Log.d("카테고리", category);
                } else {
                    category = "1";
                    Log.d("카테고리", category);
                }
            }
        });

        final DialogInterface.OnClickListener dialogFinish = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BoardDetailActivity BCA = (BoardDetailActivity) BoardDetailActivity._BoardClickedActivity;
                BCA.finish();
                finish();
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        };

        final StringRequest stringImageDeleteRequest = new StringRequest(Request.Method.POST, URL_IMAGE_DELETE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(BoardDetailActivity.this, "이미지 삭제 : " + response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("img", bimage_var);
                return params;
            }
        };

        boardUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = boardUpdateTitleText.getText().toString();
                String text = boardUpdateDescText.getText().toString();



                if (title.equals("")) {
                    Snackbar.make(v, "제목을 입력해주세요.", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else if (text.equals("")) {
                    Snackbar.make(v, "내용을 입력해주세요.", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
                    if (!bimage_var.equals("0")) {
                        if (imageTouch) {
                            queue.add(stringImageDeleteRequest);
                        } else {

                        }
                    }
                    progressDialog.show();

                    if (imageTouch) {
                        queue.add(new VolleyMultipartRequest(Request.Method.POST, URL_IMAGE_UPLOAD,
                                          new Response.Listener<NetworkResponse>() {
                                              @Override
                                              public void onResponse(NetworkResponse response) {
                                                  try {
                                                      JSONObject obj = new JSONObject(new String(response.data));
                                                      String filename = obj.getString("filename");
                                                      bimage = filename;

                                                      String resultResponse = new String(response.data);
                                                      Log.d("json 리스폰스 값 : ", resultResponse);
                                                      Log.d("filename 리스폰스 값 : ", filename);
                                                  } catch (JSONException e) {
                                                      e.printStackTrace();
                                                  }
                                              }
                                          },
                                          new Response.ErrorListener() {
                                              @Override
                                              public void onErrorResponse(VolleyError error) {
//                                              Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                                  Log.e("GotError", "" + error.getMessage());
                                              }
                                          }) {
                                      @Override
                                      protected Map<String, DataPart> getByteData() {
                                          Map<String, DataPart> params = new HashMap<>();
                                          String img = "img";
                                          params.put("img", new DataPart(img + ".png", bitmap.getFileDataFromDrawable()));
                                          return params;
                                      }
                                  }
                        );
                    } else {
                        if (imageCheck.isChecked()) {
                            bimage = "0";
                        } else {
                            bimage = bimage_var;
                        }
                    }


                    Handler timer = new Handler();
                    timer.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            queue.add(stringBoardUpdateRequest);

                            AlertDialog.Builder builder = new AlertDialog.Builder(BoardUpdateActivity.this);
                            dialog = builder.setMessage("글이 수정되었습니다.")
                                    .setPositiveButton("확인", dialogFinish)
                                    .create();
                            dialog.show();
                            dialog.setCancelable(false);
                            overridePendingTransition(R.anim.in, R.anim.out);
                        }
                    }, 1500);

                }
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        imageTouch = true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
            filePath = getPath(picUri);
            if (filePath != null) {
                try {
                    boardImageUpdateText.setText("File Selected");
                    Log.d("filePath", String.valueOf(filePath));
                    bitmap = new UploadBitmapImage(MediaStore.Images.Media.getBitmap(getContentResolver(), picUri));
                    boardUpdateImage.setImageBitmap(bitmap.getBitmap());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(
                        BoardUpdateActivity.this, "no image selected",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in, R.anim.out);
    }
}
