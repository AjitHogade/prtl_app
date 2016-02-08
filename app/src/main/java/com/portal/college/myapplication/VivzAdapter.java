package com.portal.college.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 08-Feb-16.
 */
public class VivzAdapter extends RecyclerView.Adapter<VivzAdapter.MyViewHolder>{
    private final LayoutInflater inflater;
    List<Information> data = Collections.emptyList();

    public VivzAdapter(Context context,List<Information> data){
    inflater=LayoutInflater.from(context);
        this.data=data;
}
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
Information current = data.get(position);
        holder.title.setText(current.title);
        holder.id.setImageResource(current.iconId);
    }

    @Override
        public int getItemCount() {

            return data.size();

    }
class MyViewHolder extends RecyclerView.ViewHolder{
    TextView title;
    ImageView id;

    public MyViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.lText);
        id = (ImageView) itemView.findViewById(R.id.lIcon);
    }
}
}
