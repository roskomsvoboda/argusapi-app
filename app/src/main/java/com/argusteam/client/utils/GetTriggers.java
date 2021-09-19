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


public class GetTriggers extends AsyncTask<Void, Void, ArrayList<ArrayList<String>>> {

    public Context context;
    public Activity activity;
    public String id;
    public GetTriggers(Context ctx, Activity act, String userid){
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
        ArrayList<ArrayList<String>> triggs = new ArrayList<>();
        try {
            String urlString = "http://45.134.255.158:80/triggers?group_id="+id;
            Log.e("id: ",id);
            HttpURLConnection urlConnection = null;
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.addRequestProperty("Accept", "application/json");
            urlConnection.addRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);
            url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            String jsonString = sb.toString();
            JSONObject answer = new JSONObject(jsonString);
            JSONArray triggers = answer.getJSONArray("triggers");
            for (int i = 0;i<triggers.length();i++){
                ArrayList<String> tempok = new ArrayList<>();
                    JSONObject tempjson = new JSONObject(triggers.get(i).toString());

                    if (tempjson.get("type").toString().equals("1")){
                        tempok.add(tempjson.get("left_time").toString());
                    }else if (tempjson.get("type").toString().equals("0")){
                        tempok.add(tempjson.get("link").toString());
                    }else if (tempjson.get("type").toString().equals("2")){
                        tempok.add(tempjson.get("key_word").toString());
                    }
                    tempok.add(tempjson.get("type").toString());
                    triggs.add(tempok);
            }
            Log.e("gtrb", String.valueOf(sb)+urlConnection.getResponseCode());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        return triggs;
    }

    @Override
    protected void onPostExecute(ArrayList<ArrayList<String>> id) {
        super.onPostExecute(id);

    }
}
