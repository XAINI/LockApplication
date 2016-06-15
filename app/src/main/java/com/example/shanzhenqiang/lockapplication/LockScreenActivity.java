package com.example.shanzhenqiang.lockapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LockScreenActivity extends AppCompatActivity {
    private final String TAG = "LockScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        MyTask myTask = new MyTask();
        myTask.execute();
        setContentView(R.layout.activity_lock_screen);
    }

    public void close(View view){
        Log.i(TAG,"---------------You clicked unlock--------------");
        finish();
    }

    public class MyTask extends AsyncTask<String, Integer, String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String result;
            try {
                result = get_examination();
                Log.i(TAG,"<<<<<<<<<<<<<<<<<<<result0>>>>>>>>>>>"+result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Log.i(TAG,"<<<<<<<<<<<<<<<<<<<result>>>>>>>>>>>"+result);
            TextView textView = (TextView) findViewById(R.id.wholeRing);
            textView.setText(result);
        }

        String get_examination() throws Exception{
            String url = "http://192.168.56.3:3000/curriculums/fetch_curriculums";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            Log.i(TAG,"<<<<<<<<<<<<<<<<<<<response>>>>>>>>>>>"+response.body().string());
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Unexpected code " + response);
            }
        }
    }
}
