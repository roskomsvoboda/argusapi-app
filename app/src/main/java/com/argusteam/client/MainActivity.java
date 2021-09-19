package com.argusteam.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.argusteam.client.utils.GetAllPosts;
import com.argusteam.client.utils.RegNewUser;
import com.argusteam.client.utils.SetVKApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Util_DB db = new Util_DB(this);
        String id = db.getString("user_id");
        if (id.length()>0){
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.main_container, Main.class, null)
                .commit();
            GetAllPosts gp = new GetAllPosts(this,MainActivity.this,id);
            gp.execute();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.main_container, Hello.class, null)
                    .commit();

        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings_button) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out,R.anim.fade_in,R.anim.fade_out).replace(R.id.main_container, new Settings()).addToBackStack(null).commit();
        }
        return super.onOptionsItemSelected(item);
    }
    public void toaster(String msg){
        Toast toast = Toast.makeText(this,
                msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}