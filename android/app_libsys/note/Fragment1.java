package com.example.app_libsys.note;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

import static android.app.Activity.RESULT_OK;

public class Fragment1 extends Fragment {
    private static final String URL_NOTE_ADD = "http://www.minback.com/note/noteadd";
    private EditText titleText, descText;
    private Button sendButton;
    private RequestQueue queue;
    private AlertDialog dialog;

    private static final String URL_IMAGE_UPLOAD = "http://www.minback.com/imgupload/boardimg";
    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST = 1;
    private BitmapImage bitmap = new NoneBitmapImage(null);
    private String filePath;
    private ImageView noteWriteImage;
    private String nimage = "0";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("잠시만 기다려 주세요...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);

        Bundle bundle = getArguments();
        final String id = bundle.getString("id");

        Date currentTime = Calendar.getInstance().getTime();
        final String date_text = new SimpleDateFormat("YYYY-MM-dd H:mm", Locale.getDefault()).format(currentTime);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        titleText = view.findViewById(R.id.adminMessageTitleText);
        descText = view.findViewById(R.id.adminMessageDescText);
        sendButton = view.findViewById(R.id.adminMessageSendButton);
        noteWriteImage = view.findViewById(R.id.noteWriteImage);

        noteWriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE))) {

                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                REQUEST_PERMISSIONS);
                    }
                } else {
                    Log.e("Else", "Else");
                    showFileChooser();
                }
            }
        });



        final StringRequest stringNoteAddRequest = new StringRequest(Request.Method.POST, URL_NOTE_ADD, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
//                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ntitle", titleText.getText().toString());
                params.put("ntext", descText.getText().toString());
                params.put("nsend", id);
                params.put("nreceive", "admin");
                params.put("ndate", date_text);
                params.put("nimage", nimage);

                return params;
            }
        };

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleText.getText().toString().equals("")) {
                    dialog = builder.setMessage("제목은 빈 칸 일 수 없습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                } else if (descText.getText().toString().equals("")) {
                    dialog = builder.setMessage("내용은 빈 칸 일 수 없습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                } else {
                    progressDialog.show();
                    queue.add(new VolleyMultipartNoteRequest(Request.Method.POST, URL_IMAGE_UPLOAD,
                                      new Response.Listener<NetworkResponse>() {
                                          @Override
                                          public void onResponse(NetworkResponse response) {
                                              try {
                                                  JSONObject obj = new JSONObject(new String(response.data));
                                                  String filename = obj.getString("filename");
                                                  nimage = filename;

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
                    Handler timer = new Handler();
                    timer.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            queue.add(stringNoteAddRequest);
                            dialog = builder.setMessage("관리자에게 쪽지를 보냈습니다.")
                                    .setNegativeButton("확인", null)
                                    .create();
                            dialog.show();
                            titleText.setText(null);
                            descText.setText(null);
                            noteWriteImage.setImageResource(R.drawable.ic_attachment_black_24dp);
                        }
                    }, 500);
                }
            }
        });

        return view;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri picUri = data.getData();
            filePath = getPath(picUri);
            if (filePath != null) {
                try {
                    Log.d("filePath", String.valueOf(filePath));
                    bitmap = new UploadBitmapImage(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), picUri));
                    noteWriteImage.setImageBitmap(bitmap.getBitmap());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(
                        getActivity(), "no image selected",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getActivity().getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }
}