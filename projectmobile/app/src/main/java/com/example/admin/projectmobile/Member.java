package com.example.admin.projectmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Member extends AppCompatActivity {
    TextView usr, Name, Tel;
    ImageView proimg;
    Button insert;
    LinearLayout edit;
    private String id,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilemem);
        usr = findViewById(R.id.photoName);
        Name = findViewById(R.id.name2);
        Tel = findViewById(R.id.photoTel);
        proimg = findViewById(R.id.proimg);

        Bundle bd = getIntent().getExtras();
        if (bd != null) {
            usr.setText(bd.getString("username"));
            Name.setText(bd.getString("Name"));
            Tel.setText(bd.getString("tel"));
            id = bd.getString("id");
            pass = bd.getString("pass");
            Glide.with(this).load(bd.getString("img")).into(proimg);
        }

        insert = findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), changpab.class);
                startActivity(intent);

            }

        }
        );

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


    }
}