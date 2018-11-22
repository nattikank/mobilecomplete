package com.example.admin.projectmobile;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

public class Register2 extends AppCompatActivity {
EditText use,pass,telna,Name,addressna,_idcard;
String member;
RadioGroup GG;
ImageView imgxxx;
    private StorageReference mStorageRef;
    private Button back;
    private Uri imgUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        bind();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int select = GG.getCheckedRadioButtonId();
                RadioButton ultra = findViewById(select);
                member = String.valueOf(ultra.getText().toString());
                Upload();

            }
        });
        imgxxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Select_photo();
            }
        });

    }
    private void bind(){
        use = findViewById(R.id.regusername);
        pass = findViewById(R.id.regpassword);
        telna = findViewById(R.id.regtel);
        Name = findViewById(R.id.regname);
        addressna = findViewById(R.id.regaddress);
        _idcard = findViewById(R.id.regidcard);
        GG = findViewById(R.id.group);
        back = findViewById(R.id.btnback);
        imgxxx = findViewById(R.id.regimage);
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
                imgxxx.setImageBitmap(bm);
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
                        Toast.makeText(Register2.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }



    public void Insert(final String img){
        String _url = "http://10.51.100.63/insert.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                _url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(member.equalsIgnoreCase("member")){
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                }else{
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
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
                param.put("username",use.getText().toString());
                param.put("password",pass.getText().toString());
                param.put("Name",Name.getText().toString());
                param.put("tel",telna.getText().toString());
                param.put("address",addressna.getText().toString());
                param.put("type_member",member);
                param.put("idcard",_idcard.getText().toString());
                param.put("img",img);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
