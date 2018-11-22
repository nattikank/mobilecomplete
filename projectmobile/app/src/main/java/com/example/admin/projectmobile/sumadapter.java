package com.example.admin.projectmobile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class sumadapter extends RecyclerView.Adapter<sumadapter.MyViewHolder> {
     private ImageView sumpro;
     private  TextView titlepro;
     private List<sumphoto> listsumphoto;
     View itemView;
    Context context;

    public sumadapter(List<sumphoto> listsumphto, Context applicationContext) {
        this.context = applicationContext;
        this.listsumphoto = listsumphto;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView sumpro;
        private  TextView titlepro;
        View itemView;
        private List<sumphoto> listphoto;
        public MyViewHolder(@NonNull View view) {
            super(view);
            sumpro = view.findViewById(R.id.imagePro);
            titlepro = view.findViewById(R.id.titlename);

        }
    }

    @NonNull
    @Override
    public sumadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grapher,parent,false);
        return new sumadapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final sumphoto sm = listsumphoto.get(position);
        Glide.with(itemView.getContext()).load(sm.getUrl()).into(holder.sumpro);
        holder.titlepro.setText(sm.getName());
    }


    @Override
    public int getItemCount() {
        return listsumphoto.size();
    }
}
