package com.example.saurabhgoyal.cameraapplication.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saurabhgoyal.cameraapplication.Information;
import com.example.saurabhgoyal.cameraapplication.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by saurabh goyal on 9/19/2015.
 */
public class VivzAdapter2 extends RecyclerView.Adapter<VivzAdapter2.MyViewHolder> {
    private ClickListener clickListener;
    private LayoutInflater inflater;
    List<Information> data= Collections.emptyList();//take care of null pointer exception
    private Context context;

    public VivzAdapter2(Context context, List<Information> data){
        inflater= LayoutInflater.from(context);
        this.context=context;
        this.data=data;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        //called only once
    View view=inflater.inflate(R.layout.customrow2, viewGroup, false);

        Log.d("Vivz", "on create view holder is called");
        MyViewHolder holder=new MyViewHolder(view);



        return holder;
    }
    public void setClickListener(ClickListener clickListener){
        this.clickListener=clickListener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
Information current=data.get(i);
        Log.d("Vivz", "on bind view holder is called" + i);
        viewHolder.title.setText(current.title);
        viewHolder.icon.setImageResource(current.itemId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView icon;
        TextView title;
        public MyViewHolder(View itemView) {
            super(itemView);
title= (TextView) itemView.findViewById(R.id.textmessage);
            icon= (ImageView) itemView.findViewById(R.id.image_icon);
itemView.setOnClickListener(this);


        }
        public void delete(int position){

data.remove(position);
            notifyItemRemoved(position);



        }

        @Override
        public void onClick(View v) {
//delete(getPosition());

    //
            if(clickListener!=null){
                clickListener.itemclicked(v,getPosition());
            }

        }


    }
    public interface ClickListener{

        public void itemclicked(View view, int position);



    }
}
