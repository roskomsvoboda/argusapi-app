package com.argusteam.client;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.argusteam.client.utils.RegNewUser;
import com.argusteam.client.utils.SetVKApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Hello extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    public Dialog dialog;
    public Hello() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View main = inflater.inflate(R.layout.hello_screen, container, false);
        Util_DB db = new Util_DB(getContext());
        Button regnew = main.findViewById(R.id.reguser);
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setSubtitle("Регистрация");
        Button openvk = main.findViewById(R.id.requestvk);
        Animation bottomUp = AnimationUtils.loadAnimation(getContext(),
                R.anim.bottom);
        LinearLayout hiddenPanel = main.findViewById(R.id.animmain);
        hiddenPanel.startAnimation(bottomUp);
        hiddenPanel.setVisibility(View.VISIBLE);
        Animation fade = AnimationUtils.loadAnimation(getContext(),
                R.anim.fade_in);
        ImageView img = main.findViewById(R.id.logo);
        img.startAnimation(fade);
        img.setVisibility(View.VISIBLE);
        openvk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://oauth.vk.com/authorize?client_id=7954516&scope=notify,wall,offline&response_type=token")));
            }
        });
        TextInputEditText getname = main.findViewById(R.id.getname);
        TextInputEditText getkey = main.findViewById(R.id.getapi);
        regnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = getname.getText().toString();
                String api = getkey.getText().toString();
                RegNewUser reg = new RegNewUser(getContext(),getActivity(),name);

                try {
                    String id = reg.execute().get();
                    if (id.length()>0){
                        db.putString("user_id",id);
                        SetVKApi vkapi = new SetVKApi(getContext(),getActivity(),api,id);
                        boolean isok = vkapi.execute().get();
                        if (isok){toaster("Успешно");
                            Animation bottomUp = AnimationUtils.loadAnimation(getContext(),
                                    R.anim.down);
                            LinearLayout hiddenPanel = main.findViewById(R.id.animmain);
                            hiddenPanel.startAnimation(bottomUp);
                            hiddenPanel.setVisibility(View.VISIBLE);
                            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out,R.anim.fade_in,R.anim.fade_out).replace(R.id.main_container, new Main()).commit();
                        }
                        else{toaster("Не правильный токен");}
                    }else {toaster("Не удалось зарегестрироваться");}

                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        return main;
    }
    public void toaster(String msg){
        Toast toast = Toast.makeText(getContext(),
                msg, Toast.LENGTH_SHORT);
        toast.show();
    }

}