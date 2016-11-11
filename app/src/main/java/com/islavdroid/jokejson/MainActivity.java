package com.islavdroid.jokejson;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.islavdroid.jokejson.adapters.DBAdapter;
import com.islavdroid.jokejson.adapters.RecyclerViewAdapter;
import com.islavdroid.jokejson.database.DBHelper;
import com.islavdroid.jokejson.fragments.InfoFragment;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ProgressDialog pDialog;

    private static String url = "http://rzhunemogu.ru/RandJSON.aspx?CType=";
    private int type =1;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private String joke;
    private Drawer navigationDrawer;
    private List<Content> jokes =new ArrayList<>();
    private DBHelper dbHelper;
    private DBAdapter dbAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      dbHelper =new DBHelper(MainActivity.this);

        //---------------------------navigationDrawer--------------------------
        navigationDrawer = new DrawerBuilder().withActivity(this).withTranslucentStatusBar(false)
                .withToolbar(toolbar).
                        withDrawerGravity(Gravity.LEFT).withSavedInstance(savedInstanceState)
                .withSelectedItem(-1).build();

        navigationDrawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Анекдоты").withIcon(R.drawable.ic_joker).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
               changeContent(1);
                return true;
            }
        }));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Рассказы").withIcon(R.drawable.ic_story).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                changeContent(2);
                return true;
            }
        }));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Стишки").withIcon(R.drawable.poem).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                changeContent(3);
                return true;
            }
        }));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Афоризмы").withIcon(R.drawable.ic_a).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                changeContent(4);
                return true;
            }
        }));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Цитаты").withIcon(R.drawable.ic_quotation).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                changeContent(5);
                return true;
            }
        }));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Тосты").withIcon(R.drawable.ic_tost).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                changeContent(6);
                return true;
            }
        }));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Статусы").withIcon(R.drawable.ic_check_black_24px).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                changeContent(8);
                return true;
            }
        }));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Избранное").withIcon(R.drawable.star).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                jokes.clear();
                new GetContentFromDB().execute();
                navigationDrawer.closeDrawer();
                return true;
            }
        }));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Инфо").withIcon(R.drawable.ic_information_button).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                return true;
            }
        }));

        //---------------------------navigationDrawer--------------------------

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        new GetContent().execute();
    }


   public void changeContent(int i){
       type=i;
       jokes.clear();
       new GetContent().execute();
       navigationDrawer.closeDrawer();
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.refresh){
            jokes.clear();
            new GetContent().execute();
        }
        return false;
    }


    private class GetContentFromDB extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
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
            dbAdapter = new DBAdapter(getApplicationContext(),jokes);
           // mAdapter =new RecyclerViewAdapter(getApplicationContext(),jokes);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(dbAdapter);
        }
    }






    private class GetContent extends AsyncTask<Void, Void, Void> {

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
            mAdapter =new RecyclerViewAdapter(getApplicationContext(),jokes);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
    }
}


