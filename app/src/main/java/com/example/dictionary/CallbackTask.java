package com.example.dictionary;
//add dependencies to your class
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CallbackTask extends AsyncTask<String, Integer, String> {

    Context context;

    CallbackTask(Context context){
        this.context=context;
    }

    @Override
    protected String doInBackground(String... params) {

        final String app_id = "900ec9f9";
        final String app_key = "e3d86353894d38cdf9ef674d3abaa5eb";
        try {
            URL url = new URL(params[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        String def;
        try{
            JSONObject jsonObject = new JSONObject(result).
                    getJSONArray("results").getJSONObject(0).
                    getJSONArray("lexicalEntries").getJSONObject(0).
                    getJSONArray("entries").getJSONObject(0).
                    getJSONArray("senses").getJSONObject(0);
            JSONArray jsonArray=jsonObject.getJSONArray("definitions");
            def=jsonArray.getString(0);
            Toast.makeText(context,def,Toast.LENGTH_SHORT).show();

        }catch (JSONException e){
            e.printStackTrace();
        }
        Log.v("Result",result);
    }
}