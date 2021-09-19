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
import android.widget.Adapter;
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
import com.argusteam.client.utils.SaveGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Add_Group extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mAdapter2;
    public Dialog dialog;
    public Dialog dialog2;
    public String ids;
    public String id;
    public ArrayList<ArrayList<String>> temptriggers = new ArrayList<>();
    public ArrayList<ArrayList<String>> tempaction = new ArrayList<>();
    public ArrayList<String> trigger =  new ArrayList<>();
    public ArrayList<String> action =  new ArrayList<>();
    public Add_Group() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View main = inflater.inflate(R.layout.add_group, container, false);
        Util_DB db = new Util_DB(getContext());
         id = db.getString("user_id");
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        LinearLayout add_trig = main.findViewById(R.id.adds_trig);
        LinearLayout add_action = main.findViewById(R.id.adds_action);
        ab.setSubtitle("Добавить группу");
        FloatingActionButton save = main.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveGroup sg = new SaveGroup(getContext(),getActivity(),"testgroup",id,temptriggers,tempaction);
                try {
                    boolean isok = sg.execute().get();
                    if (isok){
                        toaster("OK");
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        mRecyclerView = main.findViewById(R.id.triggers_list);
        mRecyclerView2 = main.findViewById(R.id.actions_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        add_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog();
            }
        });
        add_trig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog2();
            }
        });
        return main;
    }
    public void loadposts(String id){
        GetAllPosts gp = new GetAllPosts(getContext(),getActivity(),id);
        try {
            mAdapter = new SelectPosts_Adapter(getContext(),getActivity(), gp.execute().get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    public void showdialog(){
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.50);
        int height = (int)(getResources().getDisplayMetrics().heightPixels);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.selectdialog, null, false);
        LinearLayout delallvk = view.findViewById(R.id.delallposts);
        LinearLayout delsome = view.findViewById(R.id.delsomeposts);
        delallvk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetAllPosts gp = new GetAllPosts(getContext(),getActivity(),id);
                try {
                    ArrayList<ArrayList<String>> post = gp.execute().get();
                    ArrayList<String> ids = new ArrayList<>();
                    String allids = "";
                    for (int i = 0;i<post.size();i++){
                        ids.add(post.get(i).get(1));
                    }
                    allids = String.valueOf(ids);
                    Log.e("all",allids);
                    action = new ArrayList<>();
                    action.add("Удалить все посты");
                    action.add("0");
                    action.add(allids);
                    tempaction.add(action);
                    mAdapter2 = new AddAction_Adapter(getContext(),getActivity(),tempaction);
                    mRecyclerView2.setAdapter(mAdapter2);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        delsome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showposts();
                dialog.cancel();
            }
        });
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setLayout(width,height);dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }
    public void showsmskey(){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.editcodeword, null, false);
        Button ok = view.findViewById(R.id.savesms);
        EditText word = view.findViewById(R.id.getword);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String w = word.getText().toString();
                if (w.length() <3){
                    toaster("Пожалуйста, введите уникальное слово");
                }else{
                    trigger = new ArrayList<>();
                    trigger.add("По смс");
                    trigger.add("2");
                    trigger.add(w);
                    temptriggers.add(trigger);
                    mAdapter = new AddTrig_Adapter(getContext(),getActivity(),temptriggers);
                    mRecyclerView.setAdapter(mAdapter);

                    dialog.cancel();
                }
            }
        });
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
       dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }
    public void showposts(){
        Dialog dialog3 = new Dialog(getContext());
        dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE); LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.selectposts, null, false);
        RecyclerView recyclerView;
        RecyclerView.Adapter adapter;
        recyclerView = view.findViewById(R.id.select_posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        try {
            ArrayList<ArrayList<String>> posts = new GetAllPosts(getContext(),getActivity(),id).execute().get();
            adapter = new SelectPosts_Adapter(getContext(),getActivity(),posts);
            adapter.setHasStableIds(true);
            recyclerView.setAdapter(adapter);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Button ok = view.findViewById(R.id.addposts);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ids = ((SelectPosts_Adapter) Objects.requireNonNull(recyclerView.getAdapter())).getPostslist();
                if (ids.length() > 0){
                    action = new ArrayList<>();
                    action.add("Удалить выбранные посты");
                    action.add("0");
                    action.add(ids);
                    tempaction.add(action);
                    mAdapter2 = new AddAction_Adapter(getContext(),getActivity(),tempaction);
                    mRecyclerView2.setAdapter(mAdapter2);
                    dialog3.cancel();
                }else {
                    toaster("Пожалуйста выберете посты");
                }
            }
        });
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog3.setContentView(view);
        final Window window = dialog3.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog3.show();
    }
    public void showsetitme(){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.selecttime, null, false);
        Button ok = view.findViewById(R.id.savetime);
        EditText word = view.findViewById(R.id.gettime);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String w = word.getText().toString();
                if (w.length() <1 || !isInt(w)){
                    toaster("Пожалуйста, введите минуты");
                }else{
                    trigger = new ArrayList<>();
                    trigger.add("Таймер");
                    trigger.add("1");
                    trigger.add(String.valueOf(Integer.parseInt(w)*60));
                    temptriggers.add(trigger);
                    mAdapter = new AddTrig_Adapter(getContext(),getActivity(),temptriggers);
                    mRecyclerView.setAdapter(mAdapter);
                    dialog.cancel();
                }
            }
        });
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }
    public void showdialog2(){
        dialog2 = new Dialog(getContext());
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.50);
        int height = (int)(getResources().getDisplayMetrics().heightPixels);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.selecttrigger, null, false);
        LinearLayout link = view.findViewById(R.id.setlink);
        LinearLayout sms = view.findViewById(R.id.setsms);
        LinearLayout timer = view.findViewById(R.id.settimer);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trigger = new ArrayList<>();
                trigger.add("По ссылке");
                trigger.add("0");
                trigger.add("link");
                temptriggers.add(trigger);
                mAdapter = new AddTrig_Adapter(getContext(),getActivity(),temptriggers);
                mRecyclerView.setAdapter(mAdapter);
                dialog2.cancel();
            }
        });
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showsmskey();
                dialog2.cancel();
            }
        });
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showsetitme();
                dialog2.cancel();
            }
        });
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog2.getWindow().setLayout(width,height);dialog2.setContentView(view);
        final Window window = dialog2.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog2.show();
    }
    public void toaster(String msg){
        Toast toast = Toast.makeText(getContext(),
                msg, Toast.LENGTH_SHORT);
        toast.show();
    }
    public static boolean isInt(String str) {
        try {
            @SuppressWarnings("unused")
            int x = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}