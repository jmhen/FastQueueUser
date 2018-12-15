package fq.campus;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import fq.campus.base.tabActivity;
import fq.campus.common.recyclerview.BasicItem;
import fq.campus.common.recyclerview.CollectItem;
import fq.campus.common.recyclerview.CollectListCustomAdapter;
import fq.campus.common.recyclerview.CustomAdapter;
import fq.campus.common.recyclerview.OrderHistoryListCustomAdapter;

public class NotificationActivity extends tabActivity {
    private ArrayList<CollectItem> collectItemList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CollectListCustomAdapter cAdapter;
    private OrderHistoryListCustomAdapter hAdapter;
    private TabLayout notifTab;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        getSupportActionBar().setTitle(R.string.notification_title);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        fqTab = findViewById(R.id.fqTab);
        notifTab = findViewById(R.id.notification_tab);
        tabIni();

        getCollectList();
        mRecyclerView = findViewById(R.id.collect_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
        showCollectList();
        notifTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int idx = tab.getPosition();
                switch (idx){
                    case 0:
                        showCollectList();
                        break;
                    case 1:
                        showHistoryList();
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
    private void showCollectList(){
        mRecyclerView.removeAllViews();
        cAdapter = new CollectListCustomAdapter(collectItemList);
        mRecyclerView.setAdapter(cAdapter);

    }

    private void showHistoryList(){
        mRecyclerView.removeAllViews();
        hAdapter = new OrderHistoryListCustomAdapter(collectItemList); //here we assume the history items equals the collect items
        mRecyclerView.setAdapter(hAdapter);
    }
    private void getCollectList() {

        for(int i = 0;i<3;i++){
            CollectItem item = new CollectItem("food labels","store label", "order date");
            collectItemList.add(item);
        }
    }
//    public void tabClick(View view) {
//        int id = view.getId();
//        Intent proceed = new Intent();
//        switch (id) {
//            case R.id.feed:
//                proceed.setClass(this, FeedActivity.class);
//                startActivity(proceed);
//                break;
//            case R.id.notification:
//                proceed.setClass(this, NotificationActivity.class);
//                startActivity(proceed);
//                break;
//            case R.id.profile:
//                proceed.setClass(this, ProfileActivity.class);
//                startActivity(proceed);
//                break;
//            default:
//                break;
//
//        }
//    }
}
