package com.example;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList r, g, b, a;

    CustomAdapter(Activity activity, Context context, ArrayList r, ArrayList g, ArrayList b,
                  ArrayList a){
        this.activity = activity;
        this.context = context;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.txt.setText("RGBA("+String.valueOf(r.get(position))+","+String.valueOf(g.get(position))+","+String.valueOf(b.get(position))+","+String.valueOf(a.get(position))+")");
        holder.tvColor.setBackgroundColor(Color.argb((int) a.get(position),(int) r.get(position),(int) g.get(position),(int) b.get(position)));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("r", String.valueOf(r.get(position)));
                intent.putExtra("g", String.valueOf(g.get(position)));
                intent.putExtra("b", String.valueOf(b.get(position)));
                intent.putExtra("a", String.valueOf(a.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return r.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt;
        View tvColor;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.rgbaText);
            tvColor = itemView.findViewById(R.id.tvColor);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }

}
