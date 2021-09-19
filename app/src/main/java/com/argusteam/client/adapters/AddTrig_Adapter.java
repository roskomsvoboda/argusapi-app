package com.argusteam.client.adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.argusteam.client.R;
import com.argusteam.client.Util_DB;

import java.util.ArrayList;


public class AddTrig_Adapter extends RecyclerView.Adapter<AddTrig_Adapter.ViewHolder> {
    public ArrayList<ArrayList<String>> triggers = new ArrayList<>();
    public Context context;
    public Activity activity;
    public Util_DB db;
    public AddTrig_Adapter(Context context2, Activity mActivity, ArrayList<ArrayList<String>> triggers1) {
        context = context2;
        triggers = triggers1;
        activity = mActivity;
        db = new Util_DB(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView trigger_name;
        public TextView trigger_info;
        public ImageView trigger_pic;



        public ViewHolder(View v) {
            super(v);
            trigger_name = v.findViewById(R.id.trigger_name);
            trigger_info = v.findViewById(R.id.trigger_info);
            trigger_pic = v.findViewById(R.id.trigger_pic);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_trigger, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder adapter, @SuppressLint("RecyclerView") final int position) {
        adapter.trigger_name.setText(triggers.get(position).get(0));
        if (triggers.get(position).get(1).equals("0")){
            adapter.trigger_pic.setImageDrawable(context.getDrawable(R.drawable.link));
        }else if(triggers.get(position).get(1).equals("2")){
            adapter.trigger_pic.setImageDrawable(context.getDrawable(R.drawable.sms));
        }else if(triggers.get(position).get(1).equals("1")){
            adapter.trigger_pic.setImageDrawable(context.getDrawable(R.drawable.timer));

        }

    }

    @Override
    public int getItemCount() {

        return triggers.size();
    }

}
