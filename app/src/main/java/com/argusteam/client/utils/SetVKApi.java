package com.argusteam.client.utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;


public class SetVKApi extends AsyncTask<Void, Void, Boolean> {

    public Context context;
    public String api;
    public String userid;
    public Activity activity;
    public SetVKApi(Context ctx, Activity act,String vk,String id){
        context = ctx;
        activity = act;
        api = vk;
        userid = id;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        boolean id = false;
        try {
            String urlString = "http://45.134.255.158:80/vk_api";
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
            temp.put("user_id",userid);
            temp.put("vk_api_key",api);
            Log.e("api",userid+api);
            wr.write(temp.toString());
            wr.flush();
            if (urlConnection.getResponseCode() == 200){
                id = true;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

    }
}
