package com.islavdroid.jokejson.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.islavdroid.jokejson.Content;
import com.islavdroid.jokejson.MainActivity;
import com.islavdroid.jokejson.adapters.DBAdapter;
import com.islavdroid.jokejson.adapters.RecyclerViewAdapter;
import com.islavdroid.jokejson.database.DBHelper;

import java.util.ArrayList;
import java.util.List;


public class GetContentFromDB extends AsyncTask<Void, Void, Void> {
    ProgressDialog pDialog;
    private Context mContext;
    private List<Content> jokes =new ArrayList<>();
    private DBHelper dbHelper;
    private DBAdapter dbAdapter;
    private RecyclerView recyclerView;


    public GetContentFromDB (Context context,RecyclerView recyclerView){
        mContext = context;
        dbHelper =new DBHelper(context);
        this.recyclerView=recyclerView;

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
        dbAdapter = new DBAdapter(mContext,jokes);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(dbAdapter);
    }
}