package fq.campus;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import fq.campus.base.baseActivity;
import fq.campus.common.recyclerview.BasicItem;
import fq.campus.common.recyclerview.CustomAdapter;
import fq.campus.common.recyclerview.FoodItem;
import fq.campus.common.recyclerview.FoodListCustomAdapter;


public class ViewStoreActivity extends baseActivity implements AsyncResponse{
    private TabLayout fqTab;
    private RecyclerView mRecyclerView;
    private FoodListCustomAdapter mAdapter;
    private static final int itemCount = 20;
    private ArrayList<FoodItem> food_items= new ArrayList<>();
    private String storeName;
    private String WEBSERVICE_FOODLIST ="store/{store}/foodlist.php";
    private String server = SERVER + WEBSERVICE_FOODLIST;
    private int GET_DATA = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asyncActivity = this;

        storeName = getIntent().getStringExtra("storeName").toLowerCase();
        server = server.replace("{store}",storeName);
        Log.d("SERVER: ",server);

        setContentView(R.layout.activity_view_store);
        getSupportActionBar().setTitle(storeName);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fqTab = findViewById(R.id.fqTab);

        getJSON(server);
        mRecyclerView = findViewById(R.id.recyclerView);

        mAdapter = new FoodListCustomAdapter(food_items);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ViewStoreActivity.this));
        mRecyclerView.setAdapter(mAdapter);



    }

    @Override
    public void processFinish(int request, String result) {
        if(request == GET_DATA){
            if(result.length()>0){
                try {
                    getFood(result);
                    mAdapter = new FoodListCustomAdapter(food_items);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(ViewStoreActivity.this));
                    mRecyclerView.setAdapter(mAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(asyncActivity, R.string.no_content, Toast.LENGTH_SHORT).show();}
        }

    }

    private void getFood(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        food_items = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject storeObj = jsonArray.getJSONObject(i);
            FoodItem item = new FoodItem(storeObj.getString("food_name"),"image");
            food_items.add(item);
        }

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

//    private void getFoodData() {
//        for(int i = 0;i<itemCount;i++){
//            food_items.add(new FoodItem("Food #" + i, "Pic #"+i));
//        }
//        Log.d("Food Item = " , food_items.toString());
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


