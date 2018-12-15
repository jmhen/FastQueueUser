package fq.campus;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import fq.campus.base.tabActivity;
import fq.campus.common.recyclerview.BasicItem;
import fq.campus.common.recyclerview.CollectItem;
import fq.campus.common.recyclerview.CollectListCustomAdapter;
import fq.campus.common.recyclerview.CustomAdapter;
import fq.campus.common.recyclerview.OrderHistoryListCustomAdapter;

public class NotificationActivity extends tabActivity implements AsyncResponse {
    private ArrayList<CollectItem> ItemList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CollectListCustomAdapter cAdapter;
    private OrderHistoryListCustomAdapter hAdapter;
    private TabLayout notifTab;
    private String WEBSERVICE_NOTIFICATIONLIST ="user/13/user13{status}.php";
    private String server = SERVER + WEBSERVICE_NOTIFICATIONLIST;
    private int GET_COLLECTLIST = 0;
    private int GET_COLLECTEDLIST = 1;
    private String statusCollect = "orderready";
    private String statusCollected = "collected";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asyncActivity = this;
        setContentView(R.layout.activity_notification);
        getSupportActionBar().setTitle(R.string.notification_title);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        fqTab = findViewById(R.id.fqTab);
        notifTab = findViewById(R.id.notification_tab);
        tabIni();

        getJSON(server,statusCollect);
        mRecyclerView = findViewById(R.id.collect_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
        notifTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int idx = tab.getPosition();
                switch (idx){
                    case 0:
                        getJSON(server,statusCollect);
                        break;
                    case 1:
                        getJSON(server,statusCollected);
                        break;
                    default: break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        fqTab.addOnTabSelectedListener(onFqTabSelectedListener);

    }
    @Override
    public void processFinish(int request, String result) {
        if(request == GET_COLLECTLIST){
            if(result.length()>0){
                try {
                    getList(result);
                    showCollectList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(asyncActivity, R.string.no_content, Toast.LENGTH_SHORT).show();}
        }
        if(request == GET_COLLECTEDLIST){
            if(result.length()>0){
                try {
                    getList(result);
                    showHistoryList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(asyncActivity, R.string.no_content, Toast.LENGTH_SHORT).show();}
        }

    }

    private void getList(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        ItemList = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject storeObj = jsonArray.getJSONObject(i);
            CollectItem item = new CollectItem(storeObj.getString("order_id"),storeObj.getString("store_name"),storeObj.getString("order_time"));
            ItemList.add(item);
        }

    }

    protected void getJSON(final String urlWebService, final String status) {

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
                if(status.equals("orderready"))
                delegate.processFinish(GET_COLLECTLIST, s);
                else if(status.equals("collected"))
                    delegate.processFinish(GET_COLLECTEDLIST, s);


            }

            @Override
            protected String doInBackground (Void...voids){
                try {
                    String finalurl = urlWebService.replace("{status}",status);
                    URL url = new URL(finalurl);

                    HttpURLConnection con = (HttpURLConnection) url.openConnection
                            ();
                    Log.d("URL: ", finalurl);
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
    private void showCollectList(){
        mRecyclerView.removeAllViews();
        cAdapter = new CollectListCustomAdapter(ItemList);
        mRecyclerView.setAdapter(cAdapter);
        Log.d("adapter: " , "CollectListCustomAdapter" );

    }

    private void showHistoryList(){
        mRecyclerView.removeAllViews();
        hAdapter = new OrderHistoryListCustomAdapter(ItemList); //here we assume the history items equals the collect items
        mRecyclerView.setAdapter(hAdapter);
        Log.d("adapter: " , "OrderHistoryListCustomAdapter");
    }
    private void getList() {

        for(int i = 0;i<3;i++){
            CollectItem item = new CollectItem("food labels","store label", "order date");
            ItemList.add(item);
        }
    }

}
