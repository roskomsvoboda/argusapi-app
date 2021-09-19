package com.argusteam.client.adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.argusteam.client.Main;
import com.argusteam.client.R;
import com.argusteam.client.ShowDetails;
import com.argusteam.client.Util_DB;
import com.argusteam.client.utils.GetActions;
import com.argusteam.client.utils.GetTriggers;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class Trig_Adapter extends RecyclerView.Adapter<Trig_Adapter.ViewHolder> {
    public ArrayList<ArrayList<String>> triggers = new ArrayList<>();
    public Context context;
    public Activity activity;
    public Util_DB db;
    public Trig_Adapter(Context context2, Activity mActivity, ArrayList<ArrayList<String>> triggers1) {
        context = context2;
        triggers = triggers1;
        activity = mActivity;
        db = new Util_DB(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView trigger_name;
        public TextView trigger_status;
        public TextView trigger_count;
        public ImageView trigger_pic;
        public LinearLayout full_item;


        public ViewHolder(View v) {
            super(v);
            trigger_name = v.findViewById(R.id.trigger_name);
            trigger_status = v.findViewById(R.id.trigger_status);

            trigger_pic = v.findViewById(R.id.trigger_pic);
            full_item = v.findViewById(R.id.item);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder adapter, @SuppressLint("RecyclerView") final int position) {
        adapter.trigger_name.setText(triggers.get(position).get(0));
        adapter.trigger_status.setText(triggers.get(position).get(1));
        adapter.full_item.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                GetTriggers get = new GetTriggers(context,activity,triggers.get(position).get(1));
                GetActions get1 = new GetActions(context,activity,triggers.get(position).get(1));
                try {
                    ArrayList<ArrayList<String>> t = get.execute().get();
                    ArrayList<ArrayList<String>> a = get1.execute().get();
                    manager.beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out,R.anim.fade_in,R.anim.fade_out).replace(R.id.main_container, new ShowDetails(t,a)).addToBackStack(null).commit();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                // manager.beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out,R.anim.fade_in,R.anim.fade_out).replace(R.id.main_container, new Trig_Details(triggers.get(position).get(0), triggers.get(position).get(2))).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {

        return triggers.size();
    }

}
