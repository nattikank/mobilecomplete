package com.example.admin.projectmobile;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Prograp extends AppCompatActivity {
TextView usr,Name,Tel;
ImageView proimg;
LinearLayout photo,edit;
private String id,pass;
private Button btn,insert;
private ImageView up;
private Uri imgUri;
private Button btnupanddown;
private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pro_photographer);
        btn = findViewById(R.id.insert);
        usr = findViewById(R.id.photoName);
        Name = findViewById(R.id.name2);
        Tel = findViewById(R.id.photoTel);
        proimg = findViewById(R.id.proimg);
        edit = findViewById(R.id.c_edit);
        insert = findViewById(R.id.insert);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Bundle bd = getIntent().getExtras();
        Intent xd = getIntent();
        if(bd != null){
            Toast.makeText(getApplicationContext(),bd.getString("id"),Toast.LENGTH_LONG).show();
            usr.setText(bd.getString("username"));
            Name.setText(bd.getString("Name"));
            Tel.setText(bd.getString("tel"));
            id = bd.getString("id");
            pass = bd.getString("pass");
            Glide.with(this).load(bd.getString("img")).into(proimg);
            if(xd.hasExtra("lock")) {
                if (bd.getString("lock").equalsIgnoreCase("true")) {
                    edit.setEnabled(false);
                    btn.setVisibility(View.GONE);
                    edit.setBackground(getResources().getDrawable(R.color.smoke));
                    pass = bd.getString("pass");
                }
            }else{

            }

        }
        photo = findViewById(R.id.c_photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),postpic.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        edit = findViewById(R.id.c_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Editpro.class);
                intent.putExtra("pass",pass);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });


        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Prograp.this);
                LayoutInflater inflater = getLayoutInflater();

                View view = inflater.inflate(R.layout.uploadphoto,null);
                builder.setView(view);
                up = view.findViewById(R.id.imageView3);
                up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Select_photo();
                    }
                });
                btnupanddown = view.findViewById(R.id.btnupimgpost);
                btnupanddown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Upload();
                    }
                });
                builder.show();
            }
        });
    }

    private void Select_photo() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), 1234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri = data.getData();

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                up.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void Upload(){
        if (imgUri != null) {
            //Get the storage reference
            final StorageReference ref = mStorageRef.child("profile" + System.currentTimeMillis() + "." + getImageExt(imgUri));

            //Add file to reference
            ref.putFile(imgUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Toast.makeText(getApplicationContext(),"Upload Complete",Toast.LENGTH_LONG).show();
                        Insert(downloadUri.toString());
                    } else {
                        Toast.makeText(Prograp.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
    private void Insert(final String img){
        String _url = "http://10.51.100.63/insertphoto.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                _url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            if(response.equalsIgnoreCase("Success")){
                Intent i = new Intent(getApplicationContext(),postpic.class);
                i.putExtra("id",id);
                startActivity(i);
            }else{

            }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("url",img);
                param.put("user_id",id);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}