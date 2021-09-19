package com.argusteam.client;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.argusteam.client.adapters.AddAction_Adapter;
import com.argusteam.client.adapters.AddTrig_Adapter;
import com.argusteam.client.adapters.SelectPosts_Adapter;
import com.argusteam.client.utils.GetAllPosts;
import com.argusteam.client.utils.GetTriggers;
import com.argusteam.client.utils.SaveGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ShowDetails extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapter2;
    public Dialog dialog;
    public Dialog dialog2;
    public String ids;
    public String id;
    public   ArrayList<ArrayList<String>> triggers = new ArrayList<>();
    public ArrayList<ArrayList<String>> actions = new ArrayList<>();
    public ShowDetails(ArrayList<ArrayList<String>> t ,ArrayList<ArrayList<String>> a) {
        triggers = t;
        actions = a;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View main = inflater.inflate(R.layout.details, container, false);
        Util_DB db = new Util_DB(getContext());
         id = db.getString("user_id");
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setSubtitle("Детали");
        mRecyclerView = main.findViewById(R.id.triggers_list);
        mRecyclerView2 = main.findViewById(R.id.actions_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));


        mAdapter = new AddTrig_Adapter(getContext(),getActivity(),triggers);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter2 = new AddAction_Adapter(getContext(),getActivity(),actions);
        mRecyclerView2.setAdapter(mAdapter2);
        return main;
    }

    public void toaster(String msg){
        Toast toast = Toast.makeText(getContext(),
                msg, Toast.LENGTH_SHORT);
        toast.show();
    }

}