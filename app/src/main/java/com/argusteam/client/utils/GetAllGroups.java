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


public class GetAllGroups extends AsyncTask<Void, Void, ArrayList<ArrayList<String>>> {

    public Context context;
    public Activity activity;
    public String id;
    public GetAllGroups(Context ctx, Activity act, String userid){
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
        ArrayList<ArrayList<String>> groups = new ArrayList<>();
        try {
            String urlString = "http://45.134.255.158:80/groups?user_id="+id;
            Log.e("id: ",id);
            HttpURLConnection urlConnection = null;
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.addRequestProperty("Accept", "application/json");
            urlConnection.addRequestProperty("Content-Type", "application/json");
            urlConnection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
            JSONObject temp1 = new JSONObject();
            temp1.put("user_id",id);
            wr.write(temp1.toString());
            wr.flush();
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
            JSONArray groupstemp = answer.getJSONArray("groups");
            for (int i = 0;i<groupstemp.length();i++){
                ArrayList<String> temp = new ArrayList<>();
                JSONObject nameonj = groupstemp.getJSONObject(i);
                String name = nameonj.getString("name");
                String id = nameonj.getString("group_id");
                temp.add(name);
                temp.add(id);
                groups.add(temp);
                Log.e("gr",groups.toString());
            }
            Log.e("gtrb", String.valueOf(sb)+urlConnection.getResponseCode());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        return groups;
    }

    @Override
    protected void onPostExecute(ArrayList<ArrayList<String>> id) {
        super.onPostExecute(id);

    }
}
