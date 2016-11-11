package com.islavdroid.jokejson.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.islavdroid.jokejson.Content;
import com.islavdroid.jokejson.HttpHandler;
import com.islavdroid.jokejson.R;
import com.islavdroid.jokejson.adapters.DBAdapter;
import com.islavdroid.jokejson.adapters.RecyclerViewAdapter;
import com.islavdroid.jokejson.database.DBHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment  extends Fragment {
    private ProgressDialog pDialog;
    private List<Content> jokes =new ArrayList<>();
    private DBAdapter dbAdapter;
    private RecyclerView recyclerView;
    private DBHelper dbHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,
                container, false);
        recyclerView =(RecyclerView) view.findViewById(R.id.recycler_view);
        dbHelper =new DBHelper(getContext());
        new GetContentFromDB(getActivity()).execute();
        return view;
    }


    public class GetContentFromDB extends AsyncTask<Void, Void, Void> {
      private Context mContext;


        public GetContentFromDB (Context context){
            mContext = context;
            dbHelper =new DBHelper(context);


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
}