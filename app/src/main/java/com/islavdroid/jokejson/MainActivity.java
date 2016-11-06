package com.islavdroid.jokejson;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;



import java.util.ArrayList;


public class MainActivity extends Activity {
    private ProgressDialog pDialog;
    private static String url = "http://rzhunemogu.ru/RandJSON.aspx?CType=1";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private String joke;
   private ArrayList<Content> jokes =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        new GetJokes().execute();
    }
    private class GetJokes extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Загрузка...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            for(int i=0;i<50;i++){

            HttpHandler jParser =new HttpHandler();
            String jsonStr = jParser.makeServiceCall(url);
            // Getting JSON from URL
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    joke=jsonObj.getString("content");
                    Content content =new Content(joke);
                    jokes.add(0,content);

                }catch (final JSONException e) {

                }}
            }
            return null;}

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            mAdapter =new RecyclerViewAdapter(jokes);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            recyclerView.setAdapter(mAdapter);




        }
    }
}


