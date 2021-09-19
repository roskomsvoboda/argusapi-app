package com.argusteam.client;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.argusteam.client.adapters.SelectPosts_Adapter;
import com.argusteam.client.adapters.Trig_Adapter;
import com.argusteam.client.utils.GetAllGroups;
import com.argusteam.client.utils.GetAllPosts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Main extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    public Dialog dialog;
    public Main() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View main = inflater.inflate(R.layout.main, container, false);
        Util_DB db = new Util_DB(getContext());
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setSubtitle("Все настроенные группы");
        FloatingActionButton add = main.findViewById(R.id.add);
        mRecyclerView = main.findViewById(R.id.triggerlist);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        try {ArrayList<ArrayList<String>> triggers= null;
            triggers = new GetAllGroups(getContext(),getActivity(),db.getString("user_id")).execute().get();
            mAdapter = new Trig_Adapter(getContext(),getActivity(),triggers);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        mRecyclerView.setAdapter(mAdapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out,R.anim.fade_in,R.anim.fade_out).replace(R.id.main_container, new Add_Group()).addToBackStack(null).commit();
                ab.setSubtitle("Добавить группы");

            }
        });


        return main;
    }




}