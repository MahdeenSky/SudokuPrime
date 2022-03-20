package com.example.sudokuprime;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

class GetAPIData extends AsyncTask<String, Void, JSONObject> {

    @Override
    protected JSONObject doInBackground(String... urls) {

        Log.i("DiscoverAPI", " Async has started");

        String result ;
        URL url;
        HttpURLConnection connection ;

        try{

            url = new URL( urls[0]);

            connection = (HttpURLConnection) url.openConnection();



            connection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();

            result = sb.toString();

            Log.i("DiscoverAPI", "Success. \n The result is \n" + result);



            JSONObject json = new JSONObject(result);
            return json;

        }
        catch (Exception e){
            Log.i("DiscoverAPI", "FAILED \n" + e.getMessage());
            return null;
        }


    }

}
