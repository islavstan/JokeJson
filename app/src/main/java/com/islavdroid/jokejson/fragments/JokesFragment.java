package com.islavdroid.jokejson.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.islavdroid.jokejson.Content;
import com.islavdroid.jokejson.HttpHandler;
import com.islavdroid.jokejson.MainActivity;
import com.islavdroid.jokejson.R;
import com.islavdroid.jokejson.adapters.RecyclerViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class JokesFragment extends Fragment {
    private ProgressDialog pDialog;
    private static String url = "http://rzhunemogu.ru/RandJSON.aspx?CType=";
    private int type;
    private boolean isLoading =false;
    Parcelable state;
    private String joke;
    private List<Content> jokes =new ArrayList<>();
    private RecyclerViewAdapter mAdapter;
    private RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    public JokesFragment(){}
   /* public JokesFragment (String url){
        this.url=url;
    }*/



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,
                container, false);
            recyclerView =(RecyclerView) view.findViewById(R.id.recycler_view);
        new GetContent(getActivity()).execute();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
             type = bundle.getInt("key");
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = mLayoutManager.getChildCount();//смотрим сколько элементов на экране
                int totalItemCount = mLayoutManager.getItemCount();//сколько всего элементов
                int firstVisibleItems = mLayoutManager.findFirstVisibleItemPosition();//какая позиция первого элемента
               if (!isLoading) {
                    if ( (visibleItemCount+firstVisibleItems) >= totalItemCount) {

                        isLoading = true;
                        new AddContent(getActivity()).execute();

                    }
                }


            }
        });


        return view;
    }



    private class GetContent extends AsyncTask<Void, Void, Void> {
Context context;
        public GetContent(Context context){
            this.context =context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Загрузка...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            for(int i=0;i<30;i++){
                HttpHandler jParser =new HttpHandler();
                String jsonStr = jParser.makeServiceCall(url+type);
                // Getting JSON from URL
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        joke=jsonObj.getString("content");
                        Content content =new Content(joke);

                        jokes.add(content);

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
            isLoading=false;
            mAdapter =new RecyclerViewAdapter(context,jokes);
           // mAdapter.notifyItemRangeInserted(0,jokes.size());
           // final int positionStart = jokes.size() + 1;
          //  mAdapter.notifyItemRangeChanged(0,mAdapter.getItemCount());

       mLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
    }


    private class AddContent extends AsyncTask<Void, Void, Void> {
        Context context;
        public AddContent(Context context){
            this.context =context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Showing progress dialog

            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Загрузка...");
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            for(int i=0;i<30;i++){
                HttpHandler jParser =new HttpHandler();
                String jsonStr = jParser.makeServiceCall(url+type);
                // Getting JSON from URL
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        joke=jsonObj.getString("content");
                        Content content =new Content(joke);

                        jokes.add(content);

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
            isLoading=false;
            mAdapter.notifyDataSetChanged();
        }
    }

}