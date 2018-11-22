package com.example.admin.projectmobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class photoAdapter extends RecyclerView.Adapter<photoAdapter.MyViewholder>{
    View itemView;
    List<p_photo> Listphoto;
    private Context context;
    class MyViewholder extends RecyclerView.ViewHolder {
        private ImageView prograp,picgrap;
        private TextView namegrap,numpic;
        public MyViewholder(@NonNull View view) {
            super(view);
            prograp = view.findViewById(R.id.pic1);
            picgrap = view.findViewById(R.id.proimg);
            namegrap = view.findViewById(R.id.name2);
            numpic = view.findViewById(R.id.numpic);

        }
    }

    @NonNull
    @Override
    public photoAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pic,parent,false);
        context = parent.getContext();
        return new photoAdapter.MyViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        final p_photo xy = Listphoto.get(position);
        Glide.with(itemView.getContext()).load(xy.getPrograp()).into(holder.prograp);
        Glide.with(itemView.getContext()).load(xy.getPicgrap()).into(holder.picgrap);
        holder.namegrap.setText(xy.getNamegrap());
            holder.numpic.setText(String.valueOf(position+1)+"/"+String.valueOf(xy.getNumpic()));
        holder.picgrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                LayoutInflater li =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View eiei = li.inflate(R.layout.show_photo,null);
                builder.setView(eiei);
                final ImageView img = eiei.findViewById(R.id.imageView2);
                builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                    }
                });
                Glide.with(itemView.getContext()).load(xy.getPicgrap()).into(img);
                builder.show();
            }

        });

    }


    @Override
    public int getItemCount() {
        return Listphoto.size();
    }

    public photoAdapter(List<p_photo> listphoto, Context context) {
        Listphoto = listphoto;
        this.context = context;
    }
}
