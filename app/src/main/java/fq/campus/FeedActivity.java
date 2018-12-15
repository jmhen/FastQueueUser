package fq.campus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;


import fq.campus.base.tabActivity;
import fq.campus.common.recyclerview.BasicItem;
import fq.campus.common.recyclerview.CustomAdapter;


public class FeedActivity extends tabActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<BasicItem> storeList = new ArrayList<>();
    private CustomAdapter sAdapter;
    private static final int storeCount = 10;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        getSupportActionBar().setTitle(R.string.feed_title);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        fqTab = findViewById(R.id.fqTab);
        tabIni();

        getStores();


        mRecyclerView = findViewById(R.id.recyclerView);
        sAdapter = new CustomAdapter(storeList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(FeedActivity.this));
        mRecyclerView.setAdapter(sAdapter);

        fqTab.addOnTabSelectedListener(onFqTabSelectedListener);

    }

    private void getStores() {

        for(int i = 0;i<storeCount;i++){
            BasicItem store = new BasicItem("store #"+i);
            storeList.add(store);
        }
        Log.d("getStores = ",storeList.toString());
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
