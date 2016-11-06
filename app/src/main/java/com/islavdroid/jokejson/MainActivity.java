package com.islavdroid.jokejson;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
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


import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ProgressDialog pDialog;

    private static String url = "http://rzhunemogu.ru/RandJSON.aspx?CType=";
    private int type =1;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private String joke;
    private Drawer navigationDrawer;
   private ArrayList<Content> jokes =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //---------------------------navigationDrawer--------------------------
        navigationDrawer = new DrawerBuilder().withActivity(this).withTranslucentStatusBar(false)
                .withToolbar(toolbar).
                        withDrawerGravity(Gravity.LEFT).withSavedInstance(savedInstanceState)
                .withSelectedItem(-1).build();

        navigationDrawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Анекдоты").withIcon(R.drawable.ic_joker).withSelectable(false).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
               changeContent(1);
                return true;
            }
        }));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Рассказы").withIcon(R.drawable.ic_story).withSelectable(false).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                changeContent(2);
                return true;
            }
        }));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Стишки").withIcon(R.drawable.poem).withSelectable(false).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                changeContent(3);
                return true;
            }
        }));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Афоризмы").withIcon(R.drawable.ic_a).withSelectable(false).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                changeContent(4);
                return true;
            }
        }));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Цитаты").withIcon(R.drawable.ic_quotation).withSelectable(false).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                changeContent(5);
                return true;
            }
        }));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Тосты").withIcon(R.drawable.ic_tost).withSelectable(false).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                changeContent(6);
                return true;
            }
        }));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Статусы").withIcon(R.drawable.ic_check_black_24px).withSelectable(false).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                changeContent(8);
                return true;
            }
        }));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Избранное").withIcon(R.drawable.star).withSelectable(false));





        //---------------------------navigationDrawer--------------------------

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        new GetJokes().execute();
    }


   public void changeContent(int i){
       type=i;
       jokes.clear();
       new GetJokes().execute();
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
            new GetJokes().execute();
        }
        return false;
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
            String jsonStr = jParser.makeServiceCall(url+type);
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


