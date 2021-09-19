package com.argusteam.client.utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class GetAllPosts extends AsyncTask<Void, Void, ArrayList<ArrayList<String>>> {

    public Context context;
    public Activity activity;
    public String id;
    public GetAllPosts(Context ctx, Activity act, String userid){
        context = ctx;
        activity = act;
        id = userid;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected ArrayList<ArrayList<String>> doInBackground(Void... voids) {
        ArrayList<ArrayList<String>> dialogs = new ArrayList<>();
        try {
            String urlString = "http://45.134.255.158:80/vk_api/posts?user_id="+id;
            Log.e("id: ",id);
            HttpURLConnection urlConnection = null;
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            String jsonString = sb.toString();
            JSONObject answer = new JSONObject(jsonString);
            if(answer.length()>0){
                JSONArray list = answer.getJSONArray("posts");
                Log.e("array1",list.toString());
                for(int i = 0; i <list.length();i++){
                    ArrayList<String> temp = new ArrayList<>();
                    JSONObject tempjs = new JSONObject(String.valueOf(list.getJSONObject(i)));
                    temp.add(String.valueOf(tempjs.get("date")));
                    temp.add(String.valueOf(tempjs.get("id")));
                    String main = String.valueOf(tempjs.get("title"));
                    if(main.length()>40){
                        temp.add(main.substring(0,40)+"...");
                    }else{temp.add(main);}

                    dialogs.add(temp);

            }}
            Log.e("gtrb", String.valueOf(dialogs));
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        return dialogs;
    }

    @Override
    protected void onPostExecute(ArrayList<ArrayList<String>> id) {
        super.onPostExecute(id);

    }
}
