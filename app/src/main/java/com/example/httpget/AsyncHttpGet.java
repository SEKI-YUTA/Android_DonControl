package com.example.httpget;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class AsyncHttpGet extends AsyncTask<String, Void, String> {
    private Activity activity;
    private TextView tv_result;
    private OnFetchHtml listener;
    public AsyncHttpGet(Activity activity, OnFetchHtml listener) {
        this.activity = activity;
        this.listener = listener;
        this.tv_result = activity.findViewById(R.id.tv_result);
    }



    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();

            BufferedReader bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = "";
            while((line = bf.readLine()) != null) {
                sb.append(line);
            }
            is.close();
        } catch(UnknownHostException e) {
//            tv_result.setText("Please check URL...");
//            ((TextView)activity.findViewById(R.id.tv_result)).setText("Please check URL...")
            // ここからDOM操作出来れば都合がいいけどできない
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        ((TextView)activity.findViewById(R.id.tv_result)).setText(s);
        this.tv_result.setText(s);
        listener.onFetchHtml(s);
    }
}
