package fq.campus;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

import fq.campus.base.baseActivity;
import fq.campus.common.recyclerview.CustomAdapter;
import fq.campus.common.recyclerview.FoodItem;
import fq.campus.common.recyclerview.FoodListCustomAdapter;


public class ViewStoreActivity extends baseActivity {
    private TabLayout fqTab;
    private RecyclerView mRecyclerView;
    private FoodListCustomAdapter mAdapter;
    private static final int itemCount = 20;
    private ArrayList<FoodItem> food_items= new ArrayList<>();
    private String storeName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storeName = getIntent().getStringExtra("storeName");

        setContentView(R.layout.activity_view_store);
        getSupportActionBar().setTitle(storeName);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fqTab = findViewById(R.id.fqTab);

        mRecyclerView = findViewById(R.id.recyclerView);
        getFoodData();
        mAdapter = new FoodListCustomAdapter(food_items);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ViewStoreActivity.this));
        mRecyclerView.setAdapter(mAdapter);



    }


    private void getFoodData() {
        for(int i = 0;i<itemCount;i++){
            food_items.add(new FoodItem("Food #" + i, "Pic #"+i));
        }
        Log.d("Food Item = " , food_items.toString());
    }

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


