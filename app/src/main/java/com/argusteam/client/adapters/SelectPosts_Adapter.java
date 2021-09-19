package com.argusteam.client.adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.argusteam.client.R;
import com.argusteam.client.Util_DB;

import java.util.ArrayList;


public class SelectPosts_Adapter extends RecyclerView.Adapter<SelectPosts_Adapter.ViewHolder> {
    public ArrayList<ArrayList<String>> postslist = new ArrayList<>();
    public ArrayList<ArrayList<String>> selected = new ArrayList<>();
    public Context context;
    public Activity activity;
    public Util_DB db;
    public int tag = 0;
    public SelectPosts_Adapter(Context context2, Activity mActivity, ArrayList<ArrayList<String>> getposts) {
        context = context2;
        postslist = getposts;
        activity = mActivity;
        db = new Util_DB(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView poststext;
        public TextView postdate;
        public CheckBox chk;
        public LinearLayout card;


        public ViewHolder(View v) {
            super(v);
            poststext = v.findViewById(R.id.posttext);
            postdate = v.findViewById(R.id.postdate);
            chk = v.findViewById(R.id.chk);
            card = v.findViewById(R.id.item);

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_posts, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder adapter, @SuppressLint("RecyclerView") final int position) {
        adapter.poststext.setText(postslist.get(position).get(2));
        adapter.postdate.setText(postslist.get(position).get(0));
        adapter.card.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                ArrayList<String> item = postslist.get(position);
                toaster(String.valueOf(item));
                if (adapter.chk.isChecked()){
                    adapter.chk.setChecked(false);
                    selected.remove(item);
                }else{
                    adapter.chk.setChecked(true);
                    selected.add(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return postslist.size();
    }
    public void toaster(String msg){
        Toast toast = Toast.makeText(context,
                msg, Toast.LENGTH_SHORT);
        toast.show();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    public String getPostslist(){
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0;i<selected.size();i++){
            temp.add(selected.get(i).get(1));
        }
        return String.valueOf(temp);
    }
}
