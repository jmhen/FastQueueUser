package fq.campus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import fq.campus.AsyncResponse;
import fq.campus.R;
import fq.campus.ViewStoreActivity;
import fq.campus.base.tabActivity;
import fq.campus.common.recyclerview.BasicItem;
import fq.campus.common.recyclerview.CustomAdapter;


public class FeedActivity extends tabActivity implements AsyncResponse {

    private String TAG =  FeedActivity.class.getName();
    private RecyclerView mRecyclerView;
    private ArrayList<BasicItem> storeList;
    private CustomAdapter sAdapter;
    private static final int storeCount = 10;
    private String WEBSERVICE_STORELIST ="store/general/usermainpage.php";
    private String server = SERVER + WEBSERVICE_STORELIST;
    private int GET_DATA = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asyncActivity = this;
        setContentView(R.layout.activity_feed);
        getSupportActionBar().setTitle(R.string.feed_title);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        fqTab = findViewById(R.id.fqTab);
        tabIni();

        getJSON(server);

        mRecyclerView = findViewById(R.id.recyclerView);


        fqTab.addOnTabSelectedListener(onFqTabSelectedListener);


    }
    @Override
    public void processFinish(int request, String result) {
        if(request == GET_DATA){
            if(result.length()>0){
                try {
                    getStores(result);
                    sAdapter = new CustomAdapter(storeList);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(FeedActivity.this));
                    mRecyclerView.setAdapter(sAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{Toast.makeText(asyncActivity, R.string.no_content, Toast.LENGTH_SHORT).show();}
        }

    }

    private void getStores(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        storeList = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject storeObj = jsonArray.getJSONObject(i);
            BasicItem store = new BasicItem(storeObj.getString("store_name"));
            storeList.add(store);
        }

        Log.d("getStores = ",storeList.toString());
    }

    protected void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog dialog;
            AsyncResponse delegate = null;

            @Override
            protected void onPreExecute() {
                dialog = getProgressDialog(getString(R.string.loading_store_list));
                dialog.show();
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Log.d("GETJSON", s);
                delegate.processFinish(GET_DATA, s);


            }

            @Override
            protected String doInBackground (Void...voids){
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection
                            ();
                    Log.d("URL: ", urlWebService);
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                        Log.d("URL: ", json);
                    }


                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }

        GetJSON getJSON = new GetJSON();
        getJSON.delegate = (AsyncResponse) asyncActivity;
        getJSON.execute();
    }

    public void favourateClick(View view) {
        Intent gotoFavourate  =  new Intent(this, ViewStoreActivity.class);
        gotoFavourate.putExtra("storeName", "SampleStore");
        startActivity(gotoFavourate);
    }



//    @Override
//    public void onBackPressed() {
//        Toast.makeText(getApplicationContext(), R.string.log_out, Toast.LENGTH_SHORT).show();
//    }
}