package com.example.admin.projectmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class changpab extends AppCompatActivity {
private RecyclerView recyclerView;
private List<sumphoto> listsumphto;
LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changpab);
        listsumphto = new ArrayList<>();
        recyclerView = findViewById(R.id.Changpabna);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        Load();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                sumphoto sm = listsumphto.get(position);
                Toast.makeText(getApplicationContext(),String.valueOf(sm.getId()),Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),Prograp.class);
                i.putExtra("id",String.valueOf(sm.getId()));
                i.putExtra("Name",sm.getName());
                i.putExtra("img",sm.getUrl());
                i.putExtra("tel","9999999");
                i.putExtra("username",sm.getName());
                i.putExtra("lock","true");
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
    private void Load(){
        String url = "http://10.51.100.63/acphoto.php";
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray array = null;
                        try {
                            array = new JSONArray(response);
                            for(int i = 0;i<array.length();i++) {
                                JSONObject posts = array.getJSONObject(i);
                                listsumphto.add(new sumphoto(
                                        posts.getInt("id"),
                                        posts.getString("name"),
                                        posts.getString("url")
                                ));
                                sumadapter pa = new sumadapter(listsumphto,getApplicationContext());
                                recyclerView.setAdapter(pa);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
