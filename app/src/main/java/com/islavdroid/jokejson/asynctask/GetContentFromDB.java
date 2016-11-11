package com.islavdroid.jokejson.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.islavdroid.jokejson.adapters.RecyclerViewAdapter;



public class GetContentFromDB /*extends AsyncTask<Void, Void, Void> */{
 /*   ProgressDialog pDialog;
    private Context mContext;

    public GetContentFromDB (Context context){
        mContext = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Загрузка...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        jokes=dbHelper.getContentFromDB();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (pDialog.isShowing())
            pDialog.dismiss();
        mAdapter =new RecyclerViewAdapter(getApplicationContext(),jokes);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }*/
}