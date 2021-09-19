package com.argusteam.client.utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


    public class RegNewUser extends AsyncTask<Void, Void, String> {

        public Context context;
        public Activity activity;
        public String name;
        public RegNewUser(Context ctx, Activity act,String setname){
            context = ctx;
            activity = act;
            name = setname;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... voids) {
            String id = "";
            try {
                String urlString = "http://45.134.255.158:80/users";
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
                temp.put("name",name);
                wr.write(temp.toString());
                wr.flush();

                Log.e("gtrb", String.valueOf(urlConnection.getResponseCode()));url.openConnection();
                urlConnection.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                String jsonString = sb.toString();
                JSONObject answer = new JSONObject(jsonString);
                id = String.valueOf(answer.get("user_id"));
                Log.e("gtrb", String.valueOf(id));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            return id;
        }

        @Override
        protected void onPostExecute(String id) {
            super.onPostExecute(id);

        }
    }
