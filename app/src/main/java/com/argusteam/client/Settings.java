package com.argusteam.client;

import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Settings extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public Settings() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View main = inflater.inflate(R.layout.settings, container, false);
        Util_DB db = new Util_DB(getContext());
        TextView id = main.findViewById(R.id.myid);
        id.setText(db.getString("user_id"));
        return main;
    }
}