package com.argusteam.client.adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
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
import java.util.Arrays;
import java.util.List;


public class AddAction_Adapter extends RecyclerView.Adapter<AddAction_Adapter.ViewHolder> {
    public ArrayList<ArrayList<String>> actions = new ArrayList<>();
    public Context context;
    public Activity activity;
    public Util_DB db;
    public AddAction_Adapter(Context context2, Activity mActivity, ArrayList<ArrayList<String>> actions1) {
        context = context2;
        actions = actions1;
        activity = mActivity;
        db = new Util_DB(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView action_name;
        public TextView action_info;
        public ImageView action_pic;



        public ViewHolder(View v) {
            super(v);
            action_name = v.findViewById(R.id.action_name);
            action_info = v.findViewById(R.id.action_info);
            action_pic = v.findViewById(R.id.action_pic);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_action, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder adapter, @SuppressLint("RecyclerView") final int position) {
        adapter.action_name.setText(actions.get(position).get(0));
        if (actions.get(position).get(1).equals("0")){
            adapter.action_pic.setImageDrawable(context.getDrawable(R.drawable.del));
            adapter.action_info.setText("");
        }else if(actions.get(position).get(1).equals("1")){
            adapter.action_pic.setImageDrawable(context.getDrawable(R.drawable.minus));
            String[] temp = actions.get(position).get(2).split(",");
            List<String> temp2 = Arrays.asList(temp);
            Log.e("ids", String.valueOf(temp2));
            adapter.action_info.setText("");
        }else if(actions.get(position).get(1).equals("2")){
            adapter.action_pic.setImageDrawable(context.getDrawable(R.drawable.plus));
            String s = actions.get(position).get(2);
            if (s.length()>6){
            adapter.action_info.setText(s.substring(0,6));}else{
                adapter.action_info.setText(s);
            }
        }

    }

    @Override
    public int getItemCount() {

        return actions.size();
    }

}
