package com.example.admin.projectmobile;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

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

public class MainActivity extends AppCompatActivity {
EditText etUsername,etPassword;
Button btnlogin;
String usr,Name,_tel,_Status,proimg;
public String idac,password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        bind();
       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
       }

       Button buttonregister = (Button) findViewById(R.id.button_register);
       buttonregister.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register2.class);
                startActivity(intent);
            }
        });

       btnlogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               login();
           }
       });



    }
    public void bind(){
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.epass);
        btnlogin = findViewById(R.id.button_login);
    }

    private void login(){
        String url = "http://10.51.100.63/test.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for(int i = 0;i<array.length();i++){
                        JSONObject photo = array.getJSONObject(i);
                        idac = String.valueOf(photo.getInt("id"));
                        usr = photo.getString("username");
                        Name = photo.getString("Name");
                        _tel = photo.getString("tel");
                        _Status = photo.getString("type");
                        proimg = photo.getString("image");
                        password = photo.getString("pass");

                    }
                    if(_Status.equalsIgnoreCase("photographer")) {
                        Intent intent = new Intent(getApplicationContext(), Prograp.class);
                        intent.putExtra("username", usr);
                        intent.putExtra("Name", Name);
                        intent.putExtra("tel", _tel);
                        intent.putExtra("img",proimg);
                        intent.putExtra("id",idac);
                        intent.putExtra("pass",password);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getApplicationContext(), Member.class);
                        intent.putExtra("username", usr);
                        intent.putExtra("Name", Name);
                        intent.putExtra("tel", _tel);
                        intent.putExtra("img",proimg);
                        intent.putExtra("id",idac);
                        intent.putExtra("pass",password);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("username",etUsername.getText().toString());
                param.put("password",etPassword.getText().toString());
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
