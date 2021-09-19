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
import java.util.Arrays;
import java.util.List;


public class SaveGroup extends AsyncTask<Void, Void, Boolean> {

    public Context context;
    public Activity activity;
    public String name;
    public String id;
    public ArrayList<ArrayList<String>> triggers;
    public ArrayList<ArrayList<String>> actions;
    public SaveGroup(Context ctx, Activity act, String setname,String idshka,ArrayList<ArrayList<String>> trig,ArrayList<ArrayList<String>> acti){
        context = ctx;
        activity = act;
        name = setname;
        triggers = trig;
        actions = acti;
        id = idshka;
        Log.e("save",String.valueOf(trig));
        Log.e("save",String.valueOf(actions));
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        boolean isok = false;
        try {
            String urlString = "http://45.134.255.158:80/groups?user_id="+id;
            HttpURLConnection urlConnection = null;
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.addRequestProperty("Accept", "application/json");
            urlConnection.addRequestProperty("Content-Type", "application/json");
            urlConnection.connect();
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            JSONObject temp = new JSONObject();
            JSONArray trigs = new JSONArray();
            JSONArray action = new JSONArray();
            for (int i = 0;i<triggers.size();i++){
                JSONObject temptrig = new JSONObject();
                temptrig.put("local_id",String.valueOf(i));
                temptrig.put("type",triggers.get(i).get(1));
                if (Integer.parseInt(triggers.get(i).get(1)) == 0){
                    temptrig.put("link",triggers.get(i).get(2));
                }else if (Integer.parseInt(triggers.get(i).get(1)) == 1){
                    temptrig.put("left_time",triggers.get(i).get(2));
                }else if (Integer.parseInt(triggers.get(i).get(1)) == 2){
                    temptrig.put("key_word",triggers.get(i).get(2));
                }

                trigs.put(temptrig);
            }
            for (int i = 0;i<actions.size();i++){
                JSONObject tempact = new JSONObject();
                tempact.put("local_id",String.valueOf(i));
                tempact.put("type",actions.get(i).get(1));
                JSONArray tempok = new JSONArray();
                String ids = actions.get(i).get(2).replace("]","").replace("[","");
                List<String> idc = Arrays.asList(ids.split(",").clone());
                for (int i2 = 0;i2<idc.size();i2++){
                    tempok.put(idc.get(i2).replace(" ",""));
                }
                tempact.put("post_ids",tempok);
                action.put(tempact);
            }
            temp.put("user_id",id);
            temp.put("name",name);
            temp.put("triggers",trigs);
            temp.put("actions",action);
            Log.e("Saved",temp.toString());
            wr.write(temp.toString());
            wr.flush();

            url.openConnection();
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                isok = true;
            }
            Log.e("Code", String.valueOf(urlConnection.getResponseCode()));


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        return isok;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

    }
}
