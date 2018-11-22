package com.example.admin.projectmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Editpro extends AppCompatActivity {

    EditText oldpass, newpassword, confirmpass;
    Button save;
    private String pass;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_proflie_user);

        oldpass = findViewById(R.id.epass);
        newpassword = findViewById(R.id.newpass);
        confirmpass = findViewById(R.id.conpass);
        save = findViewById(R.id.save);

        Bundle bd = getIntent().getExtras();
        if (bd != null) {
            pass = bd.getString("pass");
            id = bd.getString("id");
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newpassword.getText().toString().equalsIgnoreCase(confirmpass.getText().toString()) && oldpass.getText().toString().equalsIgnoreCase(pass)) {
                    uppass(confirmpass.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "รหัสผิดนะจ้ะ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void uppass(final String pass) {
        String url = "http://10.51.100.63/update.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Update Success",Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("password",pass);
                param.put("id",id);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}